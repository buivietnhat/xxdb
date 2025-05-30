package dev.xxdb.execution.executor;

import dev.xxdb.catalog.Schema;
import dev.xxdb.execution.ExecutionException;
import dev.xxdb.execution.plan.InsertPlan;
import dev.xxdb.storage.file.TableHeap;
import dev.xxdb.storage.tuple.RID;
import dev.xxdb.storage.tuple.Tuple;
import dev.xxdb.storage.tuple.exception.TupleException;
import java.util.Optional;

public class InsertExecutor extends Executor {
  private final InsertPlan plan;
  private final Executor child;
  private TableHeap tableHeap;

  public InsertExecutor(ExecutionContext ctx, InsertPlan plan, Executor child) {
    super(ctx);
    this.plan = plan;
    this.child = child;
  }

  @Override
  public void init() throws ExecutionException {
    child.init();

    Optional<TableHeap> table = ctx.catalog().getTable(plan.getTableName());
    if (table.isEmpty()) {
      throw new ExecutionException("Trying to insert into non-existing table");
    }

    tableHeap = table.get();
  }

  @Override
  public Optional<TupleResult> next() throws ExecutionException {
    Optional<TupleResult> tupleResult = child.next();
    while (tupleResult.isPresent()) {
      Tuple tuple = tupleResult.get().tuple();
      RID rid = null;
      try {
        rid = tableHeap.addTuple(tuple);
      } catch (TupleException e) {
        throw new ExecutionException(e);
      }
      tupleResult = child.next();
    }
    return Optional.empty();
  }

  @Override
  public Schema getOutputSchema() {
    return plan.getOutputSchema();
  }

  @Override
  public String toString() {
    return "InsertExecutor{" + "child=" + child + '}';
  }
}
