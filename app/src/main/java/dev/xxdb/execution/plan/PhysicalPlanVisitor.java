package dev.xxdb.execution.plan;

public interface PhysicalPlanVisitor<T> {
  T visitCreateTablePlan(CreateTablePlan plan);

  T visitFilterPlan(FilterPlan plan);

  T visitHashJoinPlan(HashJoinPlan plan);

  T visitInsertPlan(InsertPlan plan);

  T visitSequentialScanPlan(SequentialScanPlan plan);

  T visitValueScanPlan(ValueScanPlan plan);
}
