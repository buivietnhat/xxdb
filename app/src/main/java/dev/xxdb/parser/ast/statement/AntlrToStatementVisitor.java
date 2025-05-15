package dev.xxdb.parser.ast.statement;

import dev.xxdb.parser.antlr.SqlBaseVisitor;
import dev.xxdb.parser.antlr.SqlParser;
import java.util.ArrayList;
import java.util.List;

public class AntlrToStatementVisitor extends SqlBaseVisitor<Statement> {
  @Override
  public Statement visitSql(SqlParser.SqlContext ctx) {
    return visit(ctx.getChild(0));
  }

  @Override
  public Statement visitStatement(SqlParser.StatementContext ctx) {
    return visit(ctx.getChild(0));
  }

  @Override
  public Statement visitSelectStatement(SqlParser.SelectStatementContext ctx) {
    SelectBuilder builder = new SelectBuilder();

    Statement columnList = visit(ctx.columnList());
    builder.setColumnList(columnList);

    String tableName = ctx.tableName().getText();
    builder.setTableName(tableName);

    if (ctx.joinClause() != null) {
      Statement joinClause = visit(ctx.joinClause());
      builder.setJoinClause(joinClause);
    }
    if (ctx.whereClause() != null) {
      Statement whereClause = visit(ctx.whereClause());
      builder.setWhereClause(whereClause);
    }
    if (ctx.limitClause() != null) {
      Statement limitClause = visit(ctx.limitClause());
      builder.setLimitClause(limitClause);
    }

    return builder.build();
  }

  @Override
  public Statement visitInsertStatement(SqlParser.InsertStatementContext ctx) {
    String tableName = ctx.tableName().getText();
    Statement columnList = visit(ctx.columnList());
    Statement valueSetList = visit(ctx.valueSetList());
    return new Insert(tableName, columnList, valueSetList);
  }

  @Override
  public Statement visitUpdateStatement(SqlParser.UpdateStatementContext ctx) {
    throw new RuntimeException("unimplemented");
  }

  @Override
  public Statement visitCreateTableStatement(SqlParser.CreateTableStatementContext ctx) {
    String tableName = ctx.getChild(2).getText();
    Statement columnList = visit(ctx.getChild(4));
    return new CreateTable(tableName, columnList);
  }

  @Override
  public Statement visitColumnDefinitionList(SqlParser.ColumnDefinitionListContext ctx) {
    List<Statement> children = new ArrayList<>();
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
  public Statement visitColumnDefinition(SqlParser.ColumnDefinitionContext ctx) {
    String columnName = ctx.getChild(0).getText();
    String dataType = ctx.getChild(1).getText();
    return new ColumnDefinition(columnName, dataType);
  }

  @Override
  public Statement visitAssignmentList(SqlParser.AssignmentListContext ctx) {
    throw new RuntimeException("unimplemented");
  }

  @Override
  public Statement visitAssignment(SqlParser.AssignmentContext ctx) {
    throw new RuntimeException("unimplemented");
  }

  @Override
  public Statement visitColumnList(SqlParser.ColumnListContext ctx) {
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
  public Statement visitValueList(SqlParser.ValueListContext ctx) {
    List<Statement> values = new ArrayList<>();
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
  public Statement visitWhereClause(SqlParser.WhereClauseContext ctx) {
    Statement condition = visit(ctx.condition());
    return new Where(condition);
  }

  @Override
  public Statement visitSimpleColumnCondition(SqlParser.SimpleColumnConditionContext ctx) {
    String col1 = ctx.getChild(0).getText();
    String col2 = ctx.getChild(2).getText();
    Statement operator = visit(ctx.operator());
    return new SimpleColumnCondition(col1, col2, operator);
  }

  @Override
  public Statement visitSimpleValueCondition(SqlParser.SimpleValueConditionContext ctx) {
    String columnName = ctx.columnName().getText();
    Statement operator = visit(ctx.operator());
    Statement value = visit(ctx.value());
    return new SimpleValueCondition(columnName, operator, value);
  }

  @Override
  public Statement visitOrCondition(SqlParser.OrConditionContext ctx) {
    Statement left = visit(ctx.getChild(0));
    Statement right = visit(ctx.getChild(2));
    return new OrCondition(left, right);
  }

  @Override
  public Statement visitAndCondition(SqlParser.AndConditionContext ctx) {
    Statement left = visit(ctx.getChild(0));
    Statement right = visit(ctx.getChild(2));
    return new AndCondition(left, right);
  }

  @Override
  public Statement visitOperator(SqlParser.OperatorContext ctx) {
    String op = ctx.getChild(0).getText();
    return new Operator(op);
  }

  @Override
  public Statement visitDataType(SqlParser.DataTypeContext ctx) {
    String type = ctx.getChild(0).getText();
    return new StringValue(type);
  }

  @Override
  public Statement visitColumnName(SqlParser.ColumnNameContext ctx) {
    String type = ctx.getChild(0).getText();
    return new StringValue(type);
  }

  @Override
  public Statement visitTableName(SqlParser.TableNameContext ctx) {
    String type = ctx.getChild(0).getText();
    return new StringValue(type);
  }

  @Override
  public Statement visitValue(SqlParser.ValueContext ctx) {
    if (ctx.STRING() != null) {
      return new StringValue(ctx.STRING().getText());
    }

    if (ctx.NUMBER() != null) {
      int parsed = Integer.parseInt(ctx.NUMBER().getText());
      return new IntValue(parsed);
    }

    throw new RuntimeException("unregconized value types");
  }

  @Override
  public Statement visitJoinClause(SqlParser.JoinClauseContext ctx) {
    String tableName = ctx.tableName().getText();
    Statement condition = visit(ctx.condition());
    return new Join(tableName, condition);
  }

  @Override
  public Statement visitLimitClause(SqlParser.LimitClauseContext ctx) {
    int number = Integer.parseInt(ctx.NUMBER().getText());
    return new Limit(number);
  }

  @Override
  public Statement visitValueSetList(SqlParser.ValueSetListContext ctx) {
    List<ValueList> valueSets = new ArrayList<>();
    for (int i = 0; i < ctx.getChildCount(); i++) {
      if (i % 4 == 1) { // Skip the '(' and ',' and ')'
        valueSets.addLast((ValueList) visit(ctx.getChild(i)));
      }
    }
    return new ValueSetList(valueSets);
  }
}
