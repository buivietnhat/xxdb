package dev.xxdb.execution.executor;

import dev.xxdb.catalog.Schema;
import dev.xxdb.execution.ExecutionException;
import dev.xxdb.execution.plan.LimitPlan;
import java.util.Optional;

public class LimitExecutor extends Executor {
  private final LimitPlan plan;
  private final Executor child;
  private int numTupleProduced = 0;
  private final int numLimitTuples;

  public LimitExecutor(ExecutionContext ctx, LimitPlan plan, Executor child) {
    super(ctx);
    this.plan = plan;
    this.child = child;
    this.numLimitTuples = plan.getNumber();
  }

  @Override
  public void init() throws ExecutionException {
    child.init();
  }

  @Override
  public Optional<TupleResult> next() throws ExecutionException {
    Optional<TupleResult> result = child.next();
    if (numTupleProduced >= numLimitTuples || result.isEmpty()) {
      return Optional.empty();
    }

    numTupleProduced += 1;
    return result;
  }

  @Override
  public Schema getOutputSchema() {
    return plan.getOutputSchema();
  }

  @Override
  public String toString() {
    return "LimitExecutor{" + "child=" + child + '}';
  }
}
