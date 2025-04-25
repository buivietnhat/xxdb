package dev.xxdb.optimizer;

import dev.xxdb.execution.plan.PhysicalPlan;
import dev.xxdb.parser.antlr.SqlLexer;
import dev.xxdb.parser.antlr.SqlParser;
import dev.xxdb.parser.ast.plan.CreateTablePlan;
import dev.xxdb.parser.ast.plan.InsertPlan;
import dev.xxdb.parser.ast.plan.LogicalPlan;
import dev.xxdb.parser.ast.plan.StatementToPlanVisitor;
import dev.xxdb.parser.ast.relationalgebra.Predicate;
import dev.xxdb.parser.ast.relationalgebra.PredicateVisitor;
import dev.xxdb.parser.ast.relationalgebra.Select;
import dev.xxdb.parser.ast.relationalgebra.ValuePredicate;
import dev.xxdb.parser.ast.statement.AntlrToStatementVisitor;
import dev.xxdb.parser.ast.statement.Statement;
import dev.xxdb.types.IntValue;
import dev.xxdb.types.Ops;
import dev.xxdb.types.PredicateType;
import dev.xxdb.types.StringValue;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class OptimizerTest {

  @Nested
  class PredicateBuilderTest {
    // Testing strategy
    // + partition on selects len: 1, 2, >2
    // + partition on types len: 0, 1, >1

    private final Optimizer.PredicateBuidler builder = new Optimizer.PredicateBuidler();

    // cover selects len = 1, types len = 0
    @Test
    void simplePredicate() {
      Predicate pred = new ValuePredicate(Ops.EQUALS, "col1", new IntValue(20));
      List<Select> selects = List.of(new Select(pred, "FOO"));
      dev.xxdb.types.Predicate build = builder.build(selects, Collections.emptyList());
      assertEquals("SimplePredicate{column='col1', value=IntValue[value=20], op=EQUALS}", build.toString());
    }

    // cover selects len = 2, types len = 1
    @Test
    void andPredicate() {
      Predicate pred1 = new ValuePredicate(Ops.EQUALS, "col1", new IntValue(20));
      Predicate pred2 = new ValuePredicate(Ops.GREATER_THAN, "col2", new StringValue("20"));
      List<Select> selects = List.of(new Select(pred1, "FOO"), new Select(pred2, "BAR"));
      List<PredicateType> types = List.of(PredicateType.AND);
      dev.xxdb.types.Predicate build = builder.build(selects, types);
      assertEquals("AndPredicate{left=SimplePredicate{column='col1', value=IntValue[value=20], op=EQUALS}, " +
                                          "right=SimplePredicate{column='col2', value=StringValue[value=20], op=GREATER_THAN}}", build.toString());
    }
  }

  @Nested
  class RunTest {
    // Testing strategy
    // + partition on logical plan: insert, create, select
    // + partition on select:
    //    + has join: yes, no
    //    + has predicate: yes, no
    //    + has projection: all columns, subset of columns
    //    + has order by clause: yes, no
    //    + has limit: yes, no

    // cover logical plan is insert
    @Test
    void insertTest() {
      InsertPlan insertPlan = new InsertPlan("Table", List.of("col1", "col2", "col3"),
          List.of(new IntValue(1), new StringValue("2"), new IntValue(3)));

      Optimizer optimizer = new Optimizer();
      PhysicalPlan physicalPlan = optimizer.run(insertPlan);
      assertEquals("InsertPlan{tableName='Table', " +
          "leftChild: ValueScanPlan{tableName='Table', " +
          "columns=[col1, col2, col3], values=[IntValue[value=1], StringValue[value=2], IntValue[value=3]]}}", physicalPlan.toString());

    }

    // cover logical plan is create table
    @Test
    void createTableTest() {
      CreateTablePlan plan = new CreateTablePlan("Foo", List.of("col1", "col2"), List.of("INT", "VARCHAR"));
      Optimizer optimizer = new Optimizer();
      PhysicalPlan physicalPlan = optimizer.run(plan);
      assertEquals("CreateTablePlan{tableName='Foo', columns=[col1, col2], types=[INT, VARCHAR]}", physicalPlan.toString());
    }

    LogicalPlan queryToLogicalPlan(String query) {
      AntlrToStatementVisitor planVisitor = new AntlrToStatementVisitor();
      SqlLexer lexer = new SqlLexer(CharStreams.fromString(query));
      SqlParser.SqlContext sql = new SqlParser(new CommonTokenStream(lexer)).sql();
      Statement select = planVisitor.visit(sql);
      StatementToPlanVisitor visitor = new StatementToPlanVisitor();
      select.accept(visitor);
      return visitor.getPlan();
    }

    // cover logical plan is select, only projection
    @Test
    void selectTestOnlyProjection() {
      String query = "SELECT col1, col2, col3 FROM FOO;";
      LogicalPlan logicalPlan = queryToLogicalPlan(query);
      Optimizer optimizer = new Optimizer();
      PhysicalPlan physicalPlan = optimizer.run(logicalPlan);
      assertEquals("ProjectionPlan{columns=[col1, col2, col3], child=SequentialScanPlan{table='FOO'}}", physicalPlan.toString());
    }


    // cover logical plan is select, has projection and predicate
    @Test
    void selectTestProjectionAndPredicate() {
      String query = "SELECT col1, col2, col3 FROM FOO WHERE col1 > 2 AND col2 = 'Bar';";
      LogicalPlan logicalPlan = queryToLogicalPlan(query);
      Optimizer optimizer = new Optimizer();
      PhysicalPlan physicalPlan = optimizer.run(logicalPlan);
      assertEquals("ProjectionPlan{columns=[col1, col2, col3], " +
                                "child=FilterPlan{predicate=AndPredicate{left=SimplePredicate{column='col1', value=IntValue[value=2], op=GREATER_THAN}, right=SimplePredicate{column='col2', value=StringValue[value=Bar], op=EQUALS}}, " +
                                                  "child=SequentialScanPlan{table='FOO'}}}", physicalPlan.toString());

    }


    // cover logical plan is select, has projection, predicate, join, limit
    @Test
    void selectTestProjectionAndPredicateAndJoinAndLimit() {
      String query = "SELECT FOO.col1, FOO.col2, BAR.col1, BAR.col2 FROM FOO JOIN BAR ON FOO.id = BAR.id WHERE FOO.col1 = 20 AND BAR.col2 = 'Bar' LIMIT 10;";
      LogicalPlan logicalPlan = queryToLogicalPlan(query);
      Optimizer optimizer = new Optimizer();
      PhysicalPlan physicalPlan = optimizer.run(logicalPlan);
      assertEquals("LimitPlan{number=10, " +
                                      "child=ProjectionPlan{columns=[FOO.col1, FOO.col2, BAR.col1, BAR.col2], " +
                                             "child=FilterPlan{predicate=AndPredicate{left=SimplePredicate{column='FOO.col1', value=IntValue[value=20], op=EQUALS}, right=SimplePredicate{column='BAR.col2', value=StringValue[value=Bar], op=EQUALS}}, " +
                                                    "child=HashJoinPlan{leftJoinKey='FOO.id', rightJoinKey='BAR.id', " +
                                                           "leftChild='SequentialScanPlan{table='FOO'}', " +
                                                           "rightChild='SequentialScanPlan{table='BAR'}'}}}}", physicalPlan.toString());
    }
  }
}