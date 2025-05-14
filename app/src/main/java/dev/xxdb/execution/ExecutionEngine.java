package dev.xxdb.execution;

import dev.xxdb.catalog.Schema;
import dev.xxdb.execution.executor.*;
import dev.xxdb.execution.plan.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ExecutionEngine implements PhysicalPlanVisitor<Executor> {
  private final ExecutionContext executionContext;
  private Schema outputSchema;

  public List<TupleResult> execute(PhysicalPlan plan) throws ExecutionException {
    Executor executor = buildExecutorTree(plan);
    outputSchema = executor.getOutputSchema();

    List<TupleResult> results = new ArrayList<>();

    executor.init();
    Optional<TupleResult> maybeResult = executor.next();
    while (maybeResult.isPresent()) {
      results.add(maybeResult.get());
      maybeResult = executor.next();
    }

    return results;
  }

  public Schema getOutputSchema() {
    return outputSchema;
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
