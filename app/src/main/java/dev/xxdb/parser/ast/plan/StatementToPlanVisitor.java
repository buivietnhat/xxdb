package dev.xxdb.parser.ast.plan;

import dev.xxdb.parser.ast.statement.*;

public class StatementToPlanVisitor implements StatementVisitor {
  private LogicalPlan plan;

  private CreateTablePlan getCreateTablePlan() {
    return (CreateTablePlan) plan;
  }

  private SelectPlan getSelectPlan() {
    return (SelectPlan) plan;
  }

  private InsertPlan getInsertPlan() {
    return (InsertPlan) plan;
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
    // only insert and select plan have the ColumnList expression
    if (insertPlan != null) {
      insertPlan.setColumns(node.getColumns());
    } else {
      getSelectPlan().addProjection(node.getColumns());
    }
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
    insertPlan.addValue(new dev.xxdb.types.IntValue(node.getValue()));
  }

  @Override
  public void visitStringValueNode(StringValue node) {
    InsertPlan insertPlan = getInsertPlan();
    insertPlan.addValue(new dev.xxdb.types.StringValue(node.getValue()));
  }

  @Override
  public void visitSelectNode(Select node) {
    assert(plan == null);
    plan = new SelectPlan();

    SelectPlan selectPlan = getSelectPlan();
    selectPlan.setTableName(node.getTableName());

    node.getColumnList().accept(this);

    if (node.getJoinClause().isPresent()) {
      node.getJoinClause().get().accept(this);
    }

    if (node.getWhereClause().isPresent()) {
      node.getWhereClause().get().accept(this);
    }

    if (node.getLimitClause().isPresent()) {
      node.getLimitClause().get().accept(this);
    }

  }

  @Override
  public void visitJoinNode(Join node) {
    SelectPlan selectPlan = getSelectPlan();
    selectPlan.addJoin(selectPlan.getTableName(), node.getTableName());
    node.getCondition().accept(this);
  }

  @Override
  public void visitLimitNode(Limit node) {
    throw new RuntimeException("unimplemented");
  }

  @Override
  public void visitWhereNode(Where node) {
    throw new RuntimeException("unimplemented");
  }

  @Override
  public void visitAndConditionNode(AndCondition node) {
    throw new RuntimeException("unimplemented");
  }

  @Override
  public void visitOrConditionNode(OrCondition node) {
    throw new RuntimeException("unimplemented");
  }

  @Override
  public void visitSimpleValueConditionNode(SimpleValueCondition node) {
    throw new RuntimeException("unimplemented");
  }

  @Override
  public void visitSimpleColumnConditionNode(SimpleColumnCondition node) {
    throw new RuntimeException("unimplemented");
  }

  @Override
  public void visitOperatorNode(Operator node) {
    throw new RuntimeException("unimplemented");
  }

  public LogicalPlan getPlan() {
    LogicalPlan plan = this.plan;
    this.plan = null;
    return plan;
  }
}
