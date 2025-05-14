package dev.xxdb.execution;

import dev.xxdb.catalog.Catalog;
import dev.xxdb.catalog.Schema;
import dev.xxdb.execution.executor.ExecutionContext;
import dev.xxdb.execution.executor.Executor;
import dev.xxdb.execution.executor.TupleResult;
import dev.xxdb.execution.plan.PhysicalPlan;
import dev.xxdb.optimizer.Optimizer;
import dev.xxdb.parser.antlr.SqlLexer;
import dev.xxdb.parser.antlr.SqlParser;
import dev.xxdb.parser.ast.plan.LogicalPlan;
import dev.xxdb.parser.ast.plan.StatementToPlanVisitor;
import dev.xxdb.parser.ast.statement.AntlrToStatementVisitor;
import dev.xxdb.parser.ast.statement.Statement;
import dev.xxdb.storage.disk.DiskManager;
import dev.xxdb.storage.file.HeapFile;
import dev.xxdb.storage.file.TableHeap;
import dev.xxdb.storage.page.SlottedPageRepository;
import dev.xxdb.storage.tuple.RID;
import dev.xxdb.storage.tuple.Tuple;
import dev.xxdb.types.IntValue;
import dev.xxdb.types.StringValue;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.checkerframework.checker.units.qual.C;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
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
      assertEquals("LimitExecutor{child=ProjectionExecutor{child=FilterExecutor{child=HashJoinExecutor{leftChild=SequentialScanExecutor{}, " +
          "rightChild=SequentialScanExecutor{}}}}}", tree.toString());
    }
  }

  @Nested
  class ExecuteTest {
    private static final String FILE_PATH = "dump.db";
    private DiskManager diskManager;
    private Optimizer optimizer;
    private ExecutionEngine executionEngine;

    private List<TupleResult> execute(String query) throws ExecutionException {
      LogicalPlan logicalPlan = queryToLogicalPlan(query);
      PhysicalPlan plan = optimizer.run(logicalPlan);
      return executionEngine.execute(plan);
    }

    @BeforeEach
    void setUp() throws IOException {
      diskManager = new DiskManager(FILE_PATH);
      HeapFile heapFile = new HeapFile(diskManager);
      Catalog catalog = new Catalog(heapFile);
      optimizer = new Optimizer(catalog);
      ExecutionContext executionContext = new ExecutionContext(catalog);
      executionEngine = new ExecutionEngine(executionContext);
    }

    @AfterEach
    void cleanUp() throws IOException {
      diskManager.close();
      Files.deleteIfExists(Paths.get(FILE_PATH));
    }

    @Test
    void createThenInsertThenSelect() throws ExecutionException {
      String createTable = "CREATE TABLE Persons (\n" +
          "    PersonId INT,\n" +
          "    LastName VARCHAR,\n" +
          "    FirstName VARCHAR\n" +
          ");";
      assertTrue(execute(createTable).isEmpty());

      String insert = "INSERT INTO Persons (PersonId, LastName, FirstName)\n" +
          "VALUES\n" +
          "(1, 'Tom B. Erichsen', 'Skagen 21'),\n" +
          "(2, 'Greasy Burger', 'Per Olsen'),\n" +
          "(3, 'Tasty Tee', 'Finn Egan');";
      assertTrue(execute(insert).isEmpty());

      String select = "SELECT PersonId, LastName, FirstName " +
          "FROM Persons;";
      List<TupleResult> tupleResults = execute(select);
      assertEquals(3, tupleResults.size());

      Tuple.Builder tupleBuilder = new Tuple.Builder();
      List<Tuple> expectedTuple = List.of(
          tupleBuilder
              .addIntegerColumn(new IntValue(1).getData())
              .addVarcharColumn(new StringValue("Tom B. Erichsen").getData())
              .addVarcharColumn(new StringValue("Skagen 21").getData())
              .build(),
          tupleBuilder
              .addIntegerColumn(new IntValue(2).getData())
              .addVarcharColumn(new StringValue("Greasy Burger").getData())
              .addVarcharColumn(new StringValue("Per Olsen").getData())
              .build(),
          tupleBuilder
              .addIntegerColumn(new IntValue(3).getData())
              .addVarcharColumn(new StringValue("Tasty Tee").getData())
              .addVarcharColumn(new StringValue("Finn Egan").getData())
              .build()
      );
      for (TupleResult result : tupleResults) {
        assertNotEquals(RID.INVALID_RID, result.rid());
        assertTrue(expectedTuple.contains(result.tuple()));
      }
    }
  }

}