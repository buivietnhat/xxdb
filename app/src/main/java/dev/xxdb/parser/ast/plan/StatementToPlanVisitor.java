package dev.xxdb.parser.ast.plan;

import dev.xxdb.parser.ast.statement.*;
import dev.xxdb.types.PredicateType;

public class StatementToPlanVisitor implements StatementVisitor {
  private LogicalPlan plan;

  private CreateTablePlan getCreateTablePlan() {
    return (CreateTablePlan) plan;
  }

  private InsertPlan getInsertPlan() {
    return (InsertPlan) plan;
  }

  private SelectPlan.Builder selectBuilder;

  @Override
  public void visitInsertNode(Insert node) {
    assert (plan == null);
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
    node.getColumnDefinitions().forEach(expr -> expr.accept(this));
  }

  @Override
  public void visitColumnListNode(ColumnList node) {
    InsertPlan insertPlan = getInsertPlan();
    // only insert and select plan have the ColumnList expression
    if (insertPlan != null) {
      insertPlan.setColumns(node.getColumns());
    } else {
      selectBuilder.addProjection(node.getColumns());
    }
  }

  @Override
  public void visitValueListNode(ValueList node) {
    node.getValues().forEach(expr -> expr.accept(this));
  }

  @Override
  public void visitCreateTableNode(CreateTable node) {
    assert (plan == null);
    plan = new CreateTablePlan();
    CreateTablePlan createTablePlan = getCreateTablePlan();
    createTablePlan.setTableName(node.getTableName());

    node.getColumnDefinitionList().accept(this);
  }

  @Override
  public void visitIntValueNode(IntValue node) {
    InsertPlan insertPlan = getInsertPlan();
    if (insertPlan != null) {
      insertPlan.addValue(new dev.xxdb.types.IntValue(node.getValue()));
    } else {
      selectBuilder.setValueForPredicate(new dev.xxdb.types.IntValue(node.getValue()));
    }
  }

  @Override
  public void visitStringValueNode(StringValue node) {
    InsertPlan insertPlan = getInsertPlan();
    if (insertPlan != null) {
      insertPlan.addValue(new dev.xxdb.types.StringValue(node.getValue()));
    } else {
      selectBuilder.setValueForPredicate(new dev.xxdb.types.StringValue(node.getValue()));
    }
  }

  @Override
  public void visitSelectNode(Select node) {
    selectBuilder = new SelectPlan.Builder();
    selectBuilder.setLeftTableName(node.getTableName());

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
    selectBuilder.setLeftJoinTable(selectBuilder.getLeftTableName());
    selectBuilder.setRightJoinTable(node.getTableName());
    selectBuilder.buildingJoin = true;
    node.getCondition().accept(this);
  }

  @Override
  public void visitLimitNode(Limit node) {
    selectBuilder.setLimit(node.getNumber());
  }

  @Override
  public void visitWhereNode(Where node) {
    node.getCondition().accept(this);
  }

  @Override
  public void visitAndConditionNode(AndCondition node) {
    node.getLeft().accept(this);
    node.getRight().accept(this);
    selectBuilder.addPredicateType(PredicateType.AND);
  }

  @Override
  public void visitOrConditionNode(OrCondition node) {
    node.getLeft().accept(this);
    node.getRight().accept(this);
    selectBuilder.addPredicateType(PredicateType.OR);
  }

  @Override
  public void visitSimpleValueConditionNode(SimpleValueCondition node) {
    selectBuilder.addColumnNameForPredicate(node.getColumnName());
    node.getOperator().accept(this);
    node.getValue().accept(this);
  }

  @Override
  public void visitSimpleColumnConditionNode(SimpleColumnCondition node) {
    selectBuilder.setLeftJoinColumn(node.getColumnName1());
    selectBuilder.setRightJoinColumn(node.getColumnName2());
    node.getOperator().accept(this);
  }

  @Override
  public void visitOperatorNode(Operator node) {
    if (selectBuilder != null) {
      if (selectBuilder.buildingJoin) {
        selectBuilder.setJoinOps(node.getOp());
        selectBuilder.buildingJoin = false;
      } else {
        selectBuilder.setPredicateOp(node.getOp());
      }
    }
  }

  @Override
  public void visitValueSetList(ValueSetList node) {
    InsertPlan insertPlan = getInsertPlan();
    if (insertPlan != null) {
      for (ValueList valueList : node.getValueSets()) {
        valueList.accept(this);
        insertPlan.finishValueSet();
      }
    }
  }

  public LogicalPlan getPlan() {
    if (this.plan != null) {
      return this.plan;
    }

    return selectBuilder.build();
  }
}
