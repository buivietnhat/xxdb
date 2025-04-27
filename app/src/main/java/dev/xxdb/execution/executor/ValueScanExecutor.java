package dev.xxdb.execution.executor;

import dev.xxdb.catalog.Column;
import dev.xxdb.catalog.Schema;
import dev.xxdb.execution.ExecutionException;
import dev.xxdb.execution.plan.ValueScanPlan;
import dev.xxdb.storage.tuple.RID;
import dev.xxdb.storage.tuple.Tuple;
import dev.xxdb.types.TypeId;
import dev.xxdb.types.Value;

import java.nio.ByteBuffer;
import java.util.List;
import java.util.Optional;

public class ValueScanExecutor extends Executor {
  private final ValueScanPlan plan;
  private int valueIndex = 0;
  private List<List<Value>> values;

  public ValueScanExecutor(ExecutionContext ctx, ValueScanPlan plan) {
    super(ctx);
    this.plan = plan;
  }

  @Override
  public void init() throws ExecutionException {
    Optional<Schema> maybeSchema = ctx.catalog().getTableSchema(plan.getTableName());
    if (maybeSchema.isEmpty()) {
      throw new ExecutionException("The table " + plan.getTableName() + " does not exist");
    }

    // verify the column names schema
    List<String> columns = maybeSchema.get().getColumns().stream()
        .map(Column::name)
        .toList();
    if (!columns.equals(plan.getColumns())) {
      throw new ExecutionException("The column schema are not matched");
    }

    // verify the types
    if (plan.getValues().isEmpty()) {
      throw new ExecutionException("Value must be supplied for inserting");
    }
    List<TypeId> schemaType = maybeSchema.get().getColumns().stream()
        .map(Column::typeId)
        .toList();

    boolean mismatchType = plan.getValues().stream()
        .map(
            values -> values.stream().map(Value::getTypeId).toList()
        )
        .anyMatch(suppliedType -> !schemaType.equals(suppliedType));

    if (mismatchType) {
      throw new ExecutionException("The supplied type is mismatch");
    }

    values = plan.getValues();
  }

  @Override
  public Optional<TupleResult> next() throws ExecutionException {
    if (valueIndex >= values.size()) {
      return Optional.empty();
    }

    Integer tupleSize = values.get(valueIndex).stream()
        .map(Value::size)
        .reduce(0, Integer::sum);

    byte[] bytes = new byte[tupleSize];
    ByteBuffer buffer = ByteBuffer.wrap(bytes);
    values.get(valueIndex)
        .forEach(value -> buffer.put(value.getData()));

    Tuple tuple = new Tuple(buffer.array());

    valueIndex += 1;

    return Optional.of(new TupleResult(tuple, RID.INVALID_RID));
  }
}
