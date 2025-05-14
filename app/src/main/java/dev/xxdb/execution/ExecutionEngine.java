package dev.xxdb.execution;

import dev.xxdb.execution.executor.*;
import dev.xxdb.execution.plan.*;

public class ExecutionEngine implements PhysicalPlanVisitor<Executor> {
  private final ExecutionContext executionContext;

  public void execute(PhysicalPlan plan) throws ExecutionException {
    throw new RuntimeException("unimplemented");
  }

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
    Executor child = plan.getLeftChild().accept(this);
    return new FilterExecutor(executionContext, plan, child);
  }

  @Override
  public Executor visitHashJoinPlan(HashJoinPlan plan) {
    Executor leftChild = plan.getLeftChild().accept(this);
    Executor rightChild = plan.getRightChild().accept(this);
    return new HashJoinExecutor(executionContext, plan, leftChild, rightChild);
  }

  @Override
  public Executor visitInsertPlan(InsertPlan plan) {
    Executor child = plan.getLeftChild().accept(this);
    return new InsertExecutor(executionContext, plan, child);
  }

  @Override
  public Executor visitSequentialScanPlan(SequentialScanPlan plan) {
    return new SequentialScanExecutor(executionContext, plan);
  }

  @Override
  public Executor visitValueScanPlan(ValueScanPlan plan) {
    return new ValueScanExecutor(executionContext, plan);
  }

  @Override
  public Executor visitProjectionPlan(ProjectionPlan plan) {
    Executor child = plan.getLeftChild().accept(this);
    return new ProjectionExecutor(executionContext, plan, child);
  }

  @Override
  public Executor visitLimitPlan(LimitPlan plan) {
    Executor child = plan.getLeftChild().accept(this);
    return new LimitExecutor(executionContext, plan, child);
  }
}
