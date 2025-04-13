package dev.xxdb.parser.ast.expression;

import dev.xxdb.parser.antlr.SqlBaseVisitor;
import dev.xxdb.parser.antlr.SqlParser;

import java.util.ArrayList;
import java.util.List;

public class AntlrToExpressionVisitor extends SqlBaseVisitor<Expression> {
  @Override
  public Expression visitSql(SqlParser.SqlContext ctx) {
    return visit(ctx.getChild(0));
  }

  @Override
  public Expression visitStatement(SqlParser.StatementContext ctx) {
    return visit(ctx.getChild(0));
  }

  @Override
  public Expression visitSelectStatement(SqlParser.SelectStatementContext ctx) {
    return super.visitSelectStatement(ctx);
  }

  @Override
  public Expression visitInsertStatement(SqlParser.InsertStatementContext ctx) {
    String tableName = ctx.getChild(2).getText();
    Expression columnList = visit(ctx.getChild(4));
    Expression valueList = visit(ctx.getChild(8));
//    return new InsertNode(tableName, columnList, valueList);
    throw new RuntimeException("unimplemeted");
  }

  @Override
  public Expression visitUpdateStatement(SqlParser.UpdateStatementContext ctx) {
    return super.visitUpdateStatement(ctx);
  }

  @Override
  public Expression visitCreateTableStatement(SqlParser.CreateTableStatementContext ctx) {
    String tableName = ctx.getChild(2).getText();
    Expression columnList = visit(ctx.getChild(4));
    return new CreateTableNode(tableName, columnList);
  }

  @Override
  public Expression visitColumnDefinitionList(SqlParser.ColumnDefinitionListContext ctx) {
    List<Expression> children = new ArrayList<>();
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
  public Expression visitColumnDefinition(SqlParser.ColumnDefinitionContext ctx) {
    String columnName = ctx.getChild(0).getText();
    String dataType = ctx.getChild(1).getText();
    return new ColumnDefinitionNode(columnName, dataType);
  }

  @Override
  public Expression visitAssignmentList(SqlParser.AssignmentListContext ctx) {
    return super.visitAssignmentList(ctx);
  }

  @Override
  public Expression visitAssignment(SqlParser.AssignmentContext ctx) {
    return super.visitAssignment(ctx);
  }

  @Override
  public Expression visitColumnList(SqlParser.ColumnListContext ctx) {
    return super.visitColumnList(ctx);
  }

  @Override
  public Expression visitValueList(SqlParser.ValueListContext ctx) {
    return super.visitValueList(ctx);
  }

  @Override
  public Expression visitWhereClause(SqlParser.WhereClauseContext ctx) {
    return super.visitWhereClause(ctx);
  }

  @Override
  public Expression visitCondition(SqlParser.ConditionContext ctx) {
    return super.visitCondition(ctx);
  }

  @Override
  public Expression visitOperator(SqlParser.OperatorContext ctx) {
    return super.visitOperator(ctx);
  }

  @Override
  public Expression visitDataType(SqlParser.DataTypeContext ctx) {
    String type = ctx.getChild(0).getText();
    return new StringValue(type);
  }

  @Override
  public Expression visitColumnName(SqlParser.ColumnNameContext ctx) {
    String type = ctx.getChild(0).getText();
    return new StringValue(type);
  }

  @Override
  public Expression visitTableName(SqlParser.TableNameContext ctx) {
    String type = ctx.getChild(0).getText();
    return new StringValue(type);
  }

  @Override
  public Expression visitValue(SqlParser.ValueContext ctx) {
    return super.visitValue(ctx);
  }
}
