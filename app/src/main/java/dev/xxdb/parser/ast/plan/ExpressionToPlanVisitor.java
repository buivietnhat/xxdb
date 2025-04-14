package dev.xxdb.parser.ast.plan;

import dev.xxdb.parser.ast.expression.*;

public class ExpressionToPlanVisitor implements ExpressionVisitor {
  private LogicalPlan plan;

  private CreateTablePlan getCreateTablePlan() {
    CreateTablePlan createTablePlan = (CreateTablePlan) plan;
    assert(plan != null);
    return createTablePlan;
  }

  private InsertPlan getInsertPlan() {
    InsertPlan insertPlan = (InsertPlan) plan;
    assert(plan != null);
    return insertPlan;
  }

  @Override
  public void visitInsertNode(Insert node) {
    assert(plan == null);
    plan = new InsertPlan();
    InsertPlan insertPlan = getInsertPlan();
    insertPlan.setTableName(node.getTableName());
    node.getColumns().accept(this);
    node.getValues().accept(this);
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
    InsertPlan insertPlan = getInsertPlan();
    insertPlan.setColumns(node.getColumns());
  }

  @Override
  public void visitValueListNode(ValueList node) {
    node.getValues().forEach(
        expr -> expr.accept(this)
    );
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
    InsertPlan insertPlan = getInsertPlan();
    insertPlan.addValue(new dev.xxdb.storage.tuple.IntValue(node.getValue()));
  }

  @Override
  public void visitStringValueNode(StringValue node) {
    InsertPlan insertPlan = getInsertPlan();
    insertPlan.addValue(new dev.xxdb.storage.tuple.StringValue(node.getValue()));
  }

  public LogicalPlan getPlan() {
    LogicalPlan plan = this.plan;
    this.plan = null;
    return plan;
  }
}
