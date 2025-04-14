package dev.xxdb.parser.ast.plan;

import dev.xxdb.parser.ast.expression.*;

public class ExpresionToPlanVisitor implements ExpressionVisitor {
  private LogicalPlan plan;

  private CreateTablePlan getCreateTablePlan() {
    CreateTablePlan createTablePlan = (CreateTablePlan) plan;
    assert(plan != null);
    return createTablePlan;
  }

  @Override
  public void visitInsertNode(Insert node) {

  }

  @Override
  public void visitColumnDefinitionNode(ColumnDefinition node) {
    CreateTablePlan createTablePlan = getCreateTablePlan();
    createTablePlan.addColumnDefinition(node.getColumnName(), node.getDataType());
  }

  @Override
  public void visitColumnDefinitionListNode(ColumnDefinitionList node) {
    node.getColumnDefinitions()
        .forEach(expr -> expr.accept(this));
  }

  @Override
  public void visitColumnListNode(ColumnList node) {
  }

  @Override
  public void visitCreateTableNode(CreateTable node) {
    assert(plan == null);
    plan = new CreateTablePlan();
    CreateTablePlan createTablePlan = getCreateTablePlan();
    createTablePlan.setTableName(node.getTableName());

    node.getColumnDefinitionList().accept(this);
  }

  @Override
  public void visitIntValueNode(IntValue node) {

  }

  public LogicalPlan getPlan() {
    return plan;
  }
}
