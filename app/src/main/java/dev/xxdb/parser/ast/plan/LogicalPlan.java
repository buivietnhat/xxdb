package dev.xxdb.parser.ast.plan;

public interface LogicalPlan {
  // Datatype definition:
  //  + LogicalPlan = InsertPlan + UpdatePlan + SelectPlan + CreateTablePlan

  @Override
  String toString();

  void accept(LogicalPlanVisitor visitor);
}
