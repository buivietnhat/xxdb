package dev.xxdb.optimizer;

import dev.xxdb.execution.plan.PhysicalPlan;
import dev.xxdb.execution.plan.ValueScanPlan;
import dev.xxdb.parser.ast.plan.*;

public class Optimizer implements LogicalPlanVisitor<PhysicalPlan> {

  /**
   * Run the optimize algorithm to generate a physical plan that can be fed to an execution engine to run
   *
   * @param plan logical plan
   * @return physical plan
   */
  public PhysicalPlan run(LogicalPlan plan) {
    return plan.accept(this);
  }

  @Override
  public PhysicalPlan visitCreateTablePlan(CreateTablePlan plan) {
    return new dev.xxdb.execution.plan.CreateTablePlan(plan.getTableName(), plan.getColumnList(), plan.getColumnDefList());
  }

  @Override
  public PhysicalPlan visitSelectPlan(SelectPlan plan) {
    throw new RuntimeException("unimplemeted");
  }

  @Override
  public PhysicalPlan visitInsertPlan(InsertPlan plan) {
    dev.xxdb.execution.plan.InsertPlan insert = new dev.xxdb.execution.plan.InsertPlan(plan.getTableName());
    ValueScanPlan valueScanPlan = new ValueScanPlan(plan.getTableName(), plan.getColumns(), plan.getValues());
    insert.setLeftChild(valueScanPlan);
    return insert;
  }
}