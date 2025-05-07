package dev.xxdb.parser.ast.plan;

import dev.xxdb.parser.antlr.SqlLexer;
import dev.xxdb.parser.antlr.SqlParser;
import dev.xxdb.parser.ast.statement.*;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class StatementToPlanVisitorTest {
  private StatementToPlanVisitor visitor = new StatementToPlanVisitor();

  @Nested
  class VisitCreateTableNodeTest {
    // Testing strategy
    // + partition on the expression: valid CreateTableExpression

    // cover the expression is a valid CreateTableExpression
    @Test
    void validCreateTableExpression() {
      String tableName = "FOO";
      List<Statement> columnLists = List.of(new ColumnDefinition("col1", "INT"), new ColumnDefinition("col2", "VARCHAR"));
      Statement columnDefList = new ColumnDefinitionList(columnLists);
      Statement createTable = new CreateTable(tableName, columnDefList);
      createTable.accept(visitor);
      LogicalPlan plan = visitor.getPlan();
      assertEquals("CreateTable FOO ( col1 INT,col2 VARCHAR )", plan.toString());
    }
  }

  @Nested
  class VisitInsertNodeTest {
    // Testing strategy
    // + partition on the expression: valid Insert expression
    // + partition on the value set: single value set, multiple values set

    // cover the expression is a valid Insert, single value set
    @Test
    void validInsertExpression() {
      String tableName = "FOO";
      Statement columns = new ColumnList(List.of("col1", "col2", "col3", "col4"));
      ValueList valueList = new ValueList(List.of(new IntValue(100), new StringValue("'hello'"), new IntValue(200), new StringValue("'world'")));
      Statement values = new ValueSetList(List.of(valueList));
      Insert insert = new Insert(tableName, columns, values);
      insert.accept(visitor);
      LogicalPlan plan = visitor.getPlan();
      assertEquals("InsertPlan{tableName='FOO', columns=[col1, col2, col3, col4], valueSets=[[IntValue[value=100], " +
          "StringValue[value=hello], IntValue[value=200], StringValue[value=world]]]}", plan.toString());
    }

    // cover multiple value sets
    @Test
    void validInsertExpressionWithMultipleValueSets() {
      String tableName = "FOO";
      Statement columns = new ColumnList(List.of("col1", "col2"));
      List<ValueList> valueSets = List.of(
          new ValueList(List.of(new IntValue(1), new StringValue("'a'"))),
          new ValueList(List.of(new IntValue(2), new StringValue("'b'"))),
          new ValueList(List.of(new IntValue(3), new StringValue("'c'")))
      );
      Statement valueSetList = new ValueSetList(valueSets);
      Insert insert = new Insert(tableName, columns, valueSetList);
      insert.accept(visitor);
      LogicalPlan plan = visitor.getPlan();
      assertEquals("InsertPlan{tableName='FOO', columns=[col1, col2], valueSets=[[IntValue[value=1], StringValue[value=a]], " +
          "[IntValue[value=2], StringValue[value=b]], [IntValue[value=3], StringValue[value=c]]]}", plan.toString());
    }

    @Nested
    class VisitSelectNodeTest {
      // Testing strategy
      // + partition on the expression: valid Select expression
      // + partition on appearance of join clause: yes, no
      // + partition on appearance of where clause: yes, no
      // + partition on appearance of limit clause: yes, no
      // + partition on type of where condition: simple, and, or

      // cover the expression is a valid Select, no join, no where, no limit
      @Test
      void noJoinNoWhereNoLimit() {
        String tableName = "FOO";
        Statement columns = new ColumnList(List.of("col1", "col2", "col3", "col4"));
        Select select = new Select();
        select.setTableName(tableName);
        select.setColumnList(columns);
        select.accept(visitor);
        LogicalPlan plan = visitor.getPlan();
        assertEquals("SelectPlan{table: FOO, projection=Projection{columns=[col1, col2, col3, col4]}}", plan.toString());
      }

      // cover this has join
      @Test
      void hasJoinNoWhereNoLimit() {
        String tableName = "FOO";
        Statement columns = new ColumnList(List.of("col1", "col2", "col3", "col4"));
        Statement joinCondition = new SimpleColumnCondition("FOO.id", "BAR.id", new Operator("="));
        Statement join = new Join("BAR", joinCondition);
        Select select = new Select();
        select.setTableName(tableName);
        select.setColumnList(columns);
        select.setJoinClause(join);

        select.accept(visitor);
        LogicalPlan plan = visitor.getPlan();
        assertEquals("SelectPlan{table: FOO, projection=Projection{columns=[col1, col2, col3, col4]}, " +
                "join=Join{leftTable='FOO', rightTable='BAR', " +
                "predicate=JoinPredicate{ops=EQUALS, leftColumn='FOO.id', rightColumn='BAR.id'}}}",
            plan.toString());
      }


      // cover this has simple value condition of where
      @Test
      void noJoinHasSimpleWhereNoLimit() {
        String tableName = "FOO";
        Statement columns = new ColumnList(List.of("col1", "col2", "col3", "col4"));
        Statement condition = new SimpleValueCondition("col1", new Operator(">"), new IntValue(20));
        Statement where = new Where(condition);
        Select select = new Select();
        select.setTableName(tableName);
        select.setColumnList(columns);
        select.setWhereClause(where);

        select.accept(visitor);
        LogicalPlan plan = visitor.getPlan();
        assertEquals("SelectPlan{table: FOO, projection=Projection{columns=[col1, col2, col3, col4]}, " +
                "predicates=[Select{tableName='FOO', predicate=ValuePredicate{op=GREATER_THAN, column='col1', value=IntValue[value=20]}}]}",
            plan.toString());
      }

      // cover this has and condition of where
      @Test
      void noJoinHasAndWhereNoLimit() {
        String tableName = "FOO";
        Statement columns = new ColumnList(List.of("col1", "col2", "col3", "col4"));
        Statement left = new SimpleValueCondition("col1", new Operator("="), new IntValue(20));
        Statement right = new SimpleValueCondition("col2", new Operator("<"), new IntValue(30));
        Statement condition = new AndCondition(left, right);
        Statement where = new Where(condition);
        Select select = new Select();
        select.setTableName(tableName);
        select.setColumnList(columns);
        select.setWhereClause(where);

        select.accept(visitor);
        LogicalPlan plan = visitor.getPlan();
        assertEquals("SelectPlan{table: FOO, " +
            "projection=Projection{columns=[col1, col2, col3, col4]}, " +
            "predicates=[Select{tableName='FOO', predicate=ValuePredicate{op=EQUALS, column='col1', value=IntValue[value=20]}} " +
            "AND " +
            "Select{tableName='FOO', predicate=ValuePredicate{op=LESS_THAN, column='col2', value=IntValue[value=30]}}]}", plan.toString());
      }

      // cover this has limit
      @Test
      void noJoinNoWhereHasLimit() {
        String tableName = "FOO";
        Statement columns = new ColumnList(List.of("col1", "col2", "col3", "col4"));
        Statement limit = new Limit(10);
        Select select = new Select();
        select.setTableName(tableName);
        select.setColumnList(columns);
        select.setLimitClause(limit);

        select.accept(visitor);
        LogicalPlan plan = visitor.getPlan();
        assertEquals("SelectPlan{table: FOO, projection=Projection{limit=10, columns=[col1, col2, col3, col4]}}", plan.toString());
      }

      // cover this has it all
      @Test
      void hasJoinHasWhereHasLimit() {
        AntlrToStatementVisitor planVisitor = new AntlrToStatementVisitor();
        String query = "SELECT Orders.OrderID, Customers.CustomerName, Orders.OrderDate\n" +
            "FROM Orders\n" +
            "JOIN Customers ON Orders.CustomerID=Customers.CustomerID\n" +
            "WHERE Orders.CustomerID = 1000 \n" +
            "LIMIT 100;";
        SqlLexer lexer = new SqlLexer(CharStreams.fromString(query));
        SqlParser.SqlContext sql = new SqlParser(new CommonTokenStream(lexer)).sql();
        Statement select = planVisitor.visit(sql);
        select.accept(visitor);
        LogicalPlan plan = visitor.getPlan();
        assertEquals(
            "SelectPlan{table: Orders, " +
                "projection=Projection{limit=100, columns=[Orders.OrderID, Customers.CustomerName, Orders.OrderDate]}, " +
                "predicates=[Select{tableName='Orders', predicate=ValuePredicate{op=EQUALS, column='CustomerID', value=IntValue[value=1000]}}], " +
                "join=Join{leftTable='Orders', rightTable='Customers', " +
                "predicate=JoinPredicate{ops=EQUALS, leftColumn='Orders.CustomerID', rightColumn='Customers.CustomerID'}}}", plan.toString());
      }
    }
  }
}