package dev.xxdb.execution;

import dev.xxdb.execution.executor.CreateTableExecutor;
import dev.xxdb.execution.executor.ExecutionContext;
import dev.xxdb.execution.executor.Executor;
import dev.xxdb.execution.executor.InsertExecutor;
import dev.xxdb.execution.plan.*;

public class ExecutionEngine implements PhysicalPlanVisitor<Executor> {
  public void execute(PhysicalPlan plan) throws ExecutionException {
    throw new RuntimeException("unimplemented");
  }

  private final ExecutionContext executionContext;

  public ExecutionEngine(ExecutionContext executionContext) {
    this.executionContext = executionContext;
  }

  private Executor buildExecutorTree(PhysicalPlan plan) {
    return plan.accept(this);
  }

  @Override
  public Executor visitCreateTablePlan(CreateTablePlan plan) {
    return new CreateTableExecutor(executionContext, plan);
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
    Executor child = plan.getLeftChild().accept(this);
    return new InsertExecutor(executionContext, plan, child);
  }

  @Override
  public Executor visitSequentialScanPlan(SequentialScanPlan plan) {
    throw new RuntimeException("unimplemented");
  }

  @Override
  public Executor visitValueScanPlan(ValueScanPlan plan) {
    throw new RuntimeException("unimplemented");
  }

  @Override
  public Executor visitProjectionPlan(ProjectionPlan plan) {
    throw new RuntimeException("unimplemented");
  }

  @Override
  public Executor visitLimitPlan(LimitPlan plan) {
    throw new RuntimeException("unimplemented");
  }
}
