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
    Expression columnList = visit(ctx.getChild(1));
    String tableName = ctx.getChild(3).getText();
    if (ctx.getChildCount() > 4) {
      Expression whereClause = visit(ctx.getChild(4));
    }

    throw new RuntimeException("unimplemented");
  }

  @Override
  public Expression visitInsertStatement(SqlParser.InsertStatementContext ctx) {
    String tableName = ctx.getChild(2).getText();
    Expression columnList = visit(ctx.getChild(4));
    Expression valueList = visit(ctx.getChild(8));
    return new Insert(tableName, columnList, valueList);
  }

  @Override
  public Expression visitUpdateStatement(SqlParser.UpdateStatementContext ctx) {
    throw new RuntimeException("unimplemented");
  }

  @Override
  public Expression visitCreateTableStatement(SqlParser.CreateTableStatementContext ctx) {
    String tableName = ctx.getChild(2).getText();
    Expression columnList = visit(ctx.getChild(4));
    return new CreateTable(tableName, columnList);
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

    return new ColumnDefinitionList(children);
  }

  @Override
  public Expression visitColumnDefinition(SqlParser.ColumnDefinitionContext ctx) {
    String columnName = ctx.getChild(0).getText();
    String dataType = ctx.getChild(1).getText();
    return new ColumnDefinition(columnName, dataType);
  }

  @Override
  public Expression visitAssignmentList(SqlParser.AssignmentListContext ctx) {
    throw new RuntimeException("unimplemented");
  }

  @Override
  public Expression visitAssignment(SqlParser.AssignmentContext ctx) {
    throw new RuntimeException("unimplemented");
  }

  @Override
  public Expression visitColumnList(SqlParser.ColumnListContext ctx) {
    List<String> columns = new ArrayList<>();
    for (int i = 0; i < ctx.getChildCount(); i++) {
      if (i % 2 == 1) {
        // skip the ','
        continue;
      }
      columns.addLast(ctx.getChild(i).getText());
    }

    return new ColumnList(columns);
  }

  @Override
  public Expression visitValueList(SqlParser.ValueListContext ctx) {
    List<Expression> values = new ArrayList<>();
    for (int i = 0; i < ctx.getChildCount(); i++) {
      if (i % 2 == 1) {
        // skip the ','
        continue;
      }
      values.addLast(visit(ctx.getChild(i)));
    }

    return new ValueList(values);
  }

  @Override
  public Expression visitWhereClause(SqlParser.WhereClauseContext ctx) {
    throw new RuntimeException("unimplemented");
  }

  @Override
  public Expression visitCondition(SqlParser.ConditionContext ctx) {
    throw new RuntimeException("unimplemented");
  }

  @Override
  public Expression visitOperator(SqlParser.OperatorContext ctx) {
    throw new RuntimeException("unimplemented");
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
    if (ctx.STRING() != null) {
      return new StringValue(ctx.STRING().getText());
    }

    if (ctx.NUMBER() != null) {
      int parsed = Integer.parseInt(ctx.NUMBER().getText());
      return new IntValue(parsed);
    }

    throw new RuntimeException("unregconized value types");
  }
}
