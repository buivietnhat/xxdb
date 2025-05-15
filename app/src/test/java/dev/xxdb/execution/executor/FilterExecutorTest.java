package dev.xxdb.execution.executor;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import dev.xxdb.catalog.Catalog;
import dev.xxdb.catalog.Schema;
import dev.xxdb.execution.ExecutionException;
import dev.xxdb.execution.plan.FilterPlan;
import dev.xxdb.execution.plan.PhysicalPlan;
import dev.xxdb.optimizer.Optimizer;
import dev.xxdb.parser.antlr.SqlLexer;
import dev.xxdb.parser.antlr.SqlParser;
import dev.xxdb.parser.ast.plan.LogicalPlan;
import dev.xxdb.parser.ast.plan.StatementToPlanVisitor;
import dev.xxdb.parser.ast.statement.AntlrToStatementVisitor;
import dev.xxdb.parser.ast.statement.Statement;
import dev.xxdb.storage.tuple.RID;
import dev.xxdb.storage.tuple.Tuple;
import dev.xxdb.types.*;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.junit.jupiter.api.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class FilterExecutorTest {

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
    Optimizer optimizer = new Optimizer(mock(Catalog.class));
    return optimizer.run(logicalPlan);
  }


  @Test
  public void testAssertionsEnabled() {
    assertThrows(
        AssertionError.class,
        () -> {
          assert false; // make sure assertions are enabled with VM argument: -ea
        });
  }

  @Nested
  class NextTest {
    // Testing strategy
    // + partition on Predicate type of Filter plan: simple predicate, and predicate, or predicate
    // + partition on Ops type of simple predicate: equal, greater than, less than

    // cover Predicate is a simple predicate, op is equal
    @Test
    void simplePredicateEqualOp() throws ExecutionException {
      ExecutionContext mockCtx = mock(ExecutionContext.class);
      Catalog mockCatalog = mock(Catalog.class);

      Schema.Builder schemaBuilder = new Schema.Builder();
      schemaBuilder.addIntColumn("FOO.col1");
      Schema schema = schemaBuilder.build();

      when(mockCatalog.getTableSchema(eq("FOO"))).thenReturn(Optional.of(schema));

      Predicate equalPred = new SimplePredicate("FOO", "col1", new IntValue(10), Ops.EQUALS, mockCatalog);
      FilterPlan plan = new FilterPlan(equalPred);
      Executor mockChild = mock(Executor.class);

      FilterExecutor filterExecutor = new FilterExecutor(mockCtx, plan, mockChild);
      filterExecutor.init();

      IntValue ten = new IntValue(10);
      IntValue eleven = new IntValue(11);
      IntValue twelve = new IntValue(12);

      List<TupleResult> mockTupleResult = List.of(
          new TupleResult(new Tuple(ten.getData()), RID.INVALID_RID),
          new TupleResult(new Tuple(eleven.getData()), RID.INVALID_RID),
          new TupleResult(new Tuple(twelve.getData()), RID.INVALID_RID)
      );

      when(mockChild.next())
          .thenReturn(Optional.of(mockTupleResult.get(0)))
          .thenReturn(Optional.of(mockTupleResult.get(1)))
          .thenReturn(Optional.of(mockTupleResult.get(2)))
          .thenReturn(Optional.empty());

      Optional<TupleResult> tupleResult = Optional.empty();
      List<Tuple> producedTuples = new ArrayList<>();
      do {
        tupleResult = filterExecutor.next();
        tupleResult.ifPresent(result -> producedTuples.add(result.tuple()));
      } while(tupleResult.isPresent());

      assertEquals(1, producedTuples.size());
      assertEquals(mockTupleResult.get(0).tuple(), producedTuples.get(0));
    }

    // cover Predicate is a and predicate, op is equal and greater than
    @Test
    void andPredicateEqualAndGreaterThanOp() throws ExecutionException {
      ExecutionContext mockCtx = mock(ExecutionContext.class);
      Catalog mockCatalog = mock(Catalog.class);

      Schema.Builder schemaBuilder = new Schema.Builder();
      schemaBuilder.addIntColumn("FOO.col1");
      schemaBuilder.addVarcharColumn("FOO.col2");
      Schema schema = schemaBuilder.build();

      when(mockCatalog.getTableSchema(eq("FOO"))).thenReturn(Optional.of(schema));

      Predicate greaterThanPred = new SimplePredicate("FOO", "col1", new IntValue(10), Ops.GREATER_THAN, mockCatalog);
      Predicate equalPred = new SimplePredicate("FOO", "col2", new StringValue("hello"), Ops.EQUALS, mockCatalog);
      AndPredicate andPredicate = new AndPredicate(greaterThanPred, equalPred);
      FilterPlan plan = new FilterPlan(andPredicate);

      Executor mockChild = mock(Executor.class);

      FilterExecutor filterExecutor = new FilterExecutor(mockCtx, plan, mockChild);
      filterExecutor.init();

      Tuple.Builder tupleBuilder = new Tuple.Builder();
      Tuple tuple1 = tupleBuilder.addIntegerColumn(new IntValue(10).getData()).addVarcharColumn(new StringValue("goodbye").getData()).build();
      Tuple tuple2 = tupleBuilder.addIntegerColumn(new IntValue(11).getData()).addVarcharColumn(new StringValue("hello").getData()).build();
      Tuple tuple3 = tupleBuilder.addIntegerColumn(new IntValue(12).getData()).addVarcharColumn(new StringValue("hello").getData()).build();

      List<TupleResult> mockTupleResult = List.of(
          new TupleResult(tuple1, RID.INVALID_RID),
          new TupleResult(tuple2, RID.INVALID_RID),
          new TupleResult(tuple3, RID.INVALID_RID)
      );

      when(mockChild.next())
          .thenReturn(Optional.of(mockTupleResult.get(0)))
          .thenReturn(Optional.of(mockTupleResult.get(1)))
          .thenReturn(Optional.of(mockTupleResult.get(2)))
          .thenReturn(Optional.empty());

      Optional<TupleResult> tupleResult = Optional.empty();
      List<Tuple> producedTuples = new ArrayList<>();
      do {
        tupleResult = filterExecutor.next();
        tupleResult.ifPresent(result -> producedTuples.add(result.tuple()));
      } while(tupleResult.isPresent());

      assertEquals(2, producedTuples.size());

      assertTrue(producedTuples.stream()
          .anyMatch(t -> t.equals(tuple2)));

      assertTrue(producedTuples.stream()
          .anyMatch(t -> t.equals(tuple3)));
    }
  }
}
