package dev.xxdb.execution;

import dev.xxdb.execution.executor.Executor;
import dev.xxdb.execution.plan.*;

public class ExecutionEngine implements PhysicalPlanVisitor<Executor> {
  public void execute(PhysicalPlan plan) throws ExecutionException {
    throw new RuntimeException("unimplemented");
  }

  private Executor buildExecutorTree(PhysicalPlan plan) {
    return plan.accept(this);
  }

  @Override
  public Executor visitCreateTablePlan(CreateTablePlan plan) {
    throw new RuntimeException("unimplemented");
  }

  @Override
  public Executor visitFilterPlan(FilterPlan plan) {
    throw new RuntimeException("unimplemented");
  }

  @Override
  public Executor visitHashJoinPlan(HashJoinPlan plan) {
    throw new RuntimeException("unimplemented");
  }

  @Override
  public Executor visitInsertPlan(InsertPlan plan) {
    throw new RuntimeException("unimplemented");
  }

  @Override
  public Executor visitSequentialScanPlan(SequentialScanPlan plan) {
    throw new RuntimeException("unimplemented");
  }

  @Override
  public Executor visitValueScanPlan(ValueScanPlan plan) {
    throw new RuntimeException("unimplemented");
  }
}
