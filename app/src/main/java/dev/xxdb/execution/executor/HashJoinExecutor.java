package dev.xxdb.execution.executor;

import dev.xxdb.catalog.Column;
import dev.xxdb.catalog.Schema;
import dev.xxdb.execution.ExecutionException;
import dev.xxdb.execution.plan.HashJoinPlan;
import dev.xxdb.storage.tuple.RID;
import dev.xxdb.storage.tuple.Tuple;
import dev.xxdb.types.Value;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

public class HashJoinExecutor extends Executor {
  private final HashJoinPlan plan;
  private final Executor leftChild;
  private final Executor rightChild;

  private final HashMap<Value, List<Tuple>> hashTable = new HashMap<>();
  private List<Tuple> cache = new ArrayList<>();

  public HashJoinExecutor(
      ExecutionContext ctx, HashJoinPlan plan, Executor leftChild, Executor rightChild) {
    super(ctx);
    this.plan = plan;
    this.leftChild = leftChild;
    this.rightChild = rightChild;
  }

  private void build() throws ExecutionException {
    Optional<TupleResult> maybeResult = leftChild.next();
    Schema leftSchema = leftChild.getOutputSchema();
    String leftJoinKey = plan.getLeftJoinKey();

    while (maybeResult.isPresent()) {
      Tuple tuple = maybeResult.get().tuple();
      Value key = tuple.getValue(leftSchema, leftJoinKey);
      hashTable.computeIfAbsent(key, k -> new ArrayList<>()).add(tuple);
      maybeResult = leftChild.next();
    }
  }

  private Tuple constructTuple(final Tuple left, final Tuple right) {
    Tuple.Builder builder = new Tuple.Builder();
    Schema outputSchema = getOutputSchema();
    Schema leftSchema = leftChild.getOutputSchema();
    Schema rightSchema = rightChild.getOutputSchema();

    for (Column col : outputSchema.getColumns()) {
      if (leftSchema.containsColumn(col.name())) {
        builder.addColumn(col.typeId(), left.getValue(leftSchema, col.name()).getData());
      } else if (rightSchema.containsColumn(col.name())) {
        builder.addColumn(col.typeId(), right.getValue(rightSchema, col.name()).getData());
      } else {
        throw new RuntimeException("cannot find the column to construct tuple");
      }
    }

    return builder.build();
  }

  private List<Tuple> probe(final Tuple tuple, final Schema schema, String joinKey) {
    List<Tuple> result = new ArrayList<>();
    Value key = tuple.getValue(schema, joinKey);
    if (hashTable.containsKey(key)) {
      List<Tuple> tuples = hashTable.get(key);
      tuples.forEach(t -> result.add(constructTuple(t, tuple)));
    }
    return result;
  }

  @Override
  public void init() throws ExecutionException {
    leftChild.init();
    rightChild.init();
    build();
  }

  @Override
  public Optional<TupleResult> next() throws ExecutionException {
    if (cache.isEmpty()) {
      Optional<TupleResult> maybeResult = rightChild.next();
      if (maybeResult.isPresent()) {
        cache =
            probe(maybeResult.get().tuple(), rightChild.getOutputSchema(), plan.getRightJoinKey());
      }
      if (cache.isEmpty()) {
        return Optional.empty();
      }
    }

    Tuple last = cache.getLast();
    cache.removeLast();
    return Optional.of(new TupleResult(last, RID.INVALID_RID));
  }

  @Override
  public Schema getOutputSchema() {
    return plan.getOutputSchema();
  }

  @Override
  public String toString() {
    return "HashJoinExecutor{" + "leftChild=" + leftChild + ", rightChild=" + rightChild + '}';
  }
}
