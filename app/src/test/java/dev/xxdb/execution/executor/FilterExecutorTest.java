package dev.xxdb.execution.executor;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

import dev.xxdb.catalog.Schema;
import dev.xxdb.execution.plan.FilterPlan;
import dev.xxdb.execution.plan.PhysicalPlan;
import dev.xxdb.optimizer.Optimizer;
import dev.xxdb.parser.antlr.SqlLexer;
import dev.xxdb.parser.antlr.SqlParser;
import dev.xxdb.parser.ast.plan.LogicalPlan;
import dev.xxdb.parser.ast.plan.StatementToPlanVisitor;
import dev.xxdb.parser.ast.statement.AntlrToStatementVisitor;
import dev.xxdb.parser.ast.statement.Statement;
import dev.xxdb.storage.tuple.Tuple;
import dev.xxdb.types.IntValue;
import dev.xxdb.types.Ops;
import dev.xxdb.types.Predicate;
import dev.xxdb.types.SimplePredicate;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.junit.jupiter.api.*;

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
    Optimizer optimizer = new Optimizer();
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
    void simplePredicateEqualOp() {
      ExecutionContext mockCtx = mock(ExecutionContext.class);
      Predicate equalPred = new SimplePredicate("col1", new IntValue(10), Ops.EQUALS);
      FilterPlan plan = new FilterPlan(equalPred);
      Executor mockChild = mock(Executor.class);

      FilterExecutor filterExecutor = new FilterExecutor(mockCtx, plan, mockChild);


    }
  }
}
