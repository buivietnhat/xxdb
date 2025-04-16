package dev.xxdb.parser.ast.plan;

public interface LogicalPlan {
  // Datatype definition:
  //  + LogicalPlan = InsertPlan + UpdatePlan + SelectPlan + CreateTablePlan

  @Override
  String toString();

  <T> T accept(LogicalPlanVisitor<T> visitor);
}
