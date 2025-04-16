package dev.xxdb.parser.ast.plan;

public interface LogicalPlanVisitor<T> {
  T visitCreateTablePlan(CreateTablePlan plan);

  T visitSelectPlan(SelectPlan plan);

  T visitInsertPlan(InsertPlan plan);
}
