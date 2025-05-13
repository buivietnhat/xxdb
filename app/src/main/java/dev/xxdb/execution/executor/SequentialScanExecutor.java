package dev.xxdb.execution.executor;

import dev.xxdb.catalog.Schema;
import dev.xxdb.execution.ExecutionException;
import dev.xxdb.execution.plan.SequentialScanPlan;
import dev.xxdb.storage.file.TableHeap;

import java.util.Iterator;
import java.util.Optional;

public class SequentialScanExecutor extends Executor {
  private final SequentialScanPlan plan;
  private Iterator<TupleResult> tupleIterator;

  public SequentialScanExecutor(ExecutionContext ctx, SequentialScanPlan plan) {
    super(ctx);
    this.plan = plan;
  }

  @Override
  public void init() throws ExecutionException {
    Optional<TableHeap> maybeTableHeap = ctx.catalog().getTable(plan.getTable());
    if (maybeTableHeap.isEmpty()) {
      throw new ExecutionException("table " + plan.getTable() + " does not exist");
    }

    tupleIterator = maybeTableHeap.get().getIterator();
  }

  @Override
  public Optional<TupleResult> next() throws ExecutionException {
    if (tupleIterator.hasNext()) {
      return Optional.of(tupleIterator.next());
    }

    return Optional.empty();
  }

  @Override
  public Schema getOutputSchema() {
    return plan.getOutputSchema();
  }

  @Override
  public String toString() {
    return "SequentialScanExecutor{}";
  }
}
