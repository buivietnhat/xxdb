package dev.xxdb.execution;

import dev.xxdb.catalog.Catalog;
import dev.xxdb.catalog.Schema;
import dev.xxdb.execution.executor.ExecutionContext;
import dev.xxdb.execution.executor.Executor;
import dev.xxdb.execution.plan.PhysicalPlan;
import dev.xxdb.optimizer.Optimizer;
import dev.xxdb.parser.antlr.SqlLexer;
import dev.xxdb.parser.antlr.SqlParser;
import dev.xxdb.parser.ast.plan.LogicalPlan;
import dev.xxdb.parser.ast.plan.StatementToPlanVisitor;
import dev.xxdb.parser.ast.statement.AntlrToStatementVisitor;
import dev.xxdb.parser.ast.statement.Statement;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class ExecutionEngineTest {
  private static LogicalPlan queryToLogicalPlan(String query) {
    AntlrToStatementVisitor planVisitor = new AntlrToStatementVisitor();
    SqlLexer lexer = new SqlLexer(CharStreams.fromString(query));
    SqlParser.SqlContext sql = new SqlParser(new CommonTokenStream(lexer)).sql();
    Statement select = planVisitor.visit(sql);
    StatementToPlanVisitor visitor = new StatementToPlanVisitor();
    select.accept(visitor);
    return visitor.getPlan();
  }

  private static PhysicalPlan queryToPhysicalPlan(String query) {
    LogicalPlan logicalPlan = queryToLogicalPlan(query);
    Catalog mockCatalog = mock(Catalog.class);
    Optimizer optimizer = new Optimizer(mockCatalog);
    Schema mockSchema = mock(Schema.class);
    when(mockCatalog.getTableSchema(any())).thenReturn(Optional.of(mockSchema));
    when(mockSchema.filter(any())).thenReturn(mock(Schema.class));
    when(mockSchema.join(any(), any(), any())).thenReturn(mock(Schema.class));
    return optimizer.run(logicalPlan);
  }

  @Nested
  class CreateExecutorTreeTest {
    private final ExecutionContext mockCtx = mock(ExecutionContext.class);
    private final ExecutionEngine executionEngine = new ExecutionEngine(mockCtx);
    // Testing strategy:
    //  + partition on Physical Plan: insert plan, create table plan, select plan
    //  + partition on Select plan:
    //    + partition on Join: exist, not exist
    //    + partition on Predicate: exist, not exist
    //    + partition on Limit: exist, not exist

    // cover Physical Plan is insert plan
    @Test
    void insertPlan() {
      String query = "INSERT INTO table_name (column1, column2, column3)\n" +
          "VALUES (1, 'value2', 3);";

      PhysicalPlan insertPlan = queryToPhysicalPlan(query);
      Executor tree = insertPlan.accept(executionEngine);
      assertEquals("InsertExecutor{child=ValueScanExecutor{}}", tree.toString());
    }

    // cover Physical Plan is create table plan
    @Test
    void createTable() {
      String query = "CREATE TABLE Persons (\n" +
          "    PersonID INT,\n" +
          "    LastName VARCHAR,\n" +
          "    FirstName VARCHAR,\n" +
          "    Address VARCHAR,\n" +
          "    City VARCHAR\n" +
          ");";

      PhysicalPlan insertPlan = queryToPhysicalPlan(query);
      Executor tree = insertPlan.accept(executionEngine);
      assertEquals("CreateTableExecutor{}", tree.toString());
    }

    // cover Physical Plan is select query, only projection and predicate
    @Test
    void selectWithPredicateAndProjection() {
      String query = "SELECT PersonId, FirstName, LastName from People WHERE FirstName = 'Nhat';";
      PhysicalPlan insertPlan = queryToPhysicalPlan(query);
      Executor tree = insertPlan.accept(executionEngine);
      assertEquals("ProjectionExecutor{child=SequentialScanExecutor{}}", tree.toString());
    }


    // cover Physical Plan is select query, has projection, join, predicate, limit
    @Test
    void selectWithPredicateProjectionJoinLimit() {
      String query = "SELECT People.PersonId, People.FirstName " +
          "FROM People JOIN Address ON People.PersonId = Address.PersonId " +
          "WHERE People.FirstName = 'Nhat'" +
          "LIMIT 10;";
      PhysicalPlan insertPlan = queryToPhysicalPlan(query);
      Executor tree = insertPlan.accept(executionEngine);
      assertEquals("LimitExecutor{child=ProjectionExecutor{child=FilterExecutor{child=HashJoinExecutor{leftChild=SequentialScanExecutor{}, rightChild=SequentialScanExecutor{}}}}}", tree.toString());
    }
  }

}