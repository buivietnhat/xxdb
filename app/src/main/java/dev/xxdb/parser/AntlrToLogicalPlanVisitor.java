package dev.xxdb.parser;

import dev.xxdb.parser.antlr.SqlBaseVisitor;
import dev.xxdb.parser.antlr.SqlParser;
import dev.xxdb.parser.ast.*;
import org.antlr.v4.runtime.tree.ParseTree;

import java.util.ArrayList;
import java.util.List;

public class AntlrToLogicalPlanVisitor extends SqlBaseVisitor<LogicalPlan> {
  @Override
  public LogicalPlan visitSql(SqlParser.SqlContext ctx) {
    return visit(ctx.getChild(0));
  }

  @Override
  public LogicalPlan visitStatement(SqlParser.StatementContext ctx) {
    return visit(ctx.getChild(0));
  }

  @Override
  public LogicalPlan visitSelectStatement(SqlParser.SelectStatementContext ctx) {
    return super.visitSelectStatement(ctx);
  }

  @Override
  public LogicalPlan visitInsertStatement(SqlParser.InsertStatementContext ctx) {
    return super.visitInsertStatement(ctx);
  }

  @Override
  public LogicalPlan visitUpdateStatement(SqlParser.UpdateStatementContext ctx) {
    return super.visitUpdateStatement(ctx);
  }

  @Override
  public LogicalPlan visitCreateTableStatement(SqlParser.CreateTableStatementContext ctx) {
    String tableName = ctx.getChild(2).getText();
    LogicalPlan columnList = visit(ctx.getChild(4));
    return new CreateTableNode(tableName, columnList);
  }

  @Override
  public LogicalPlan visitColumnDefinitionList(SqlParser.ColumnDefinitionListContext ctx) {
    List<LogicalPlan> children = new ArrayList<>();
    for (int i = 0; i < ctx.getChildCount(); i++) {
      if (i % 2 == 1) {
        // skip the ','
        continue;
      }
      children.addLast(visit(ctx.getChild(i)));
    }

    return new ColumnDefinitionListNode(children);
  }

  @Override
  public LogicalPlan visitColumnDefinition(SqlParser.ColumnDefinitionContext ctx) {
    String columnName = ctx.getChild(0).getText();
    String dataType = ctx.getChild(1).getText();
    return new ColumnDefinitionNode(columnName, dataType);
  }

  @Override
  public LogicalPlan visitAssignmentList(SqlParser.AssignmentListContext ctx) {
    return super.visitAssignmentList(ctx);
  }

  @Override
  public LogicalPlan visitAssignment(SqlParser.AssignmentContext ctx) {
    return super.visitAssignment(ctx);
  }

  @Override
  public LogicalPlan visitColumnList(SqlParser.ColumnListContext ctx) {
    return super.visitColumnList(ctx);
  }

  @Override
  public LogicalPlan visitValueList(SqlParser.ValueListContext ctx) {
    return super.visitValueList(ctx);
  }

  @Override
  public LogicalPlan visitWhereClause(SqlParser.WhereClauseContext ctx) {
    return super.visitWhereClause(ctx);
  }

  @Override
  public LogicalPlan visitCondition(SqlParser.ConditionContext ctx) {
    return super.visitCondition(ctx);
  }

  @Override
  public LogicalPlan visitOperator(SqlParser.OperatorContext ctx) {
    return super.visitOperator(ctx);
  }

  @Override
  public LogicalPlan visitDataType(SqlParser.DataTypeContext ctx) {
    String type = ctx.getChild(0).getText();
    return new StringValue(type);
  }

  @Override
  public LogicalPlan visitColumnName(SqlParser.ColumnNameContext ctx) {
    String type = ctx.getChild(0).getText();
    return new StringValue(type);
  }

  @Override
  public LogicalPlan visitTableName(SqlParser.TableNameContext ctx) {
    String type = ctx.getChild(0).getText();
    return new StringValue(type);
  }

  @Override
  public LogicalPlan visitValue(SqlParser.ValueContext ctx) {
    return super.visitValue(ctx);
  }
}
