// Generated from
// /home/nhatbui/workspace/database_system/xxdb/app/src/main/java/dev/xxdb/parser/antlr/Sql.g4 by
// ANTLR 4.13.2
package dev.xxdb.parser.antlr;

import org.antlr.v4.runtime.tree.ParseTreeListener;

/** This interface defines a complete listener for a parse tree produced by {@link SqlParser}. */
public interface SqlListener extends ParseTreeListener {
  /**
   * Enter a parse tree produced by {@link SqlParser#sql}.
   *
   * @param ctx the parse tree
   */
  void enterSql(SqlParser.SqlContext ctx);

  /**
   * Exit a parse tree produced by {@link SqlParser#sql}.
   *
   * @param ctx the parse tree
   */
  void exitSql(SqlParser.SqlContext ctx);

  /**
   * Enter a parse tree produced by {@link SqlParser#statement}.
   *
   * @param ctx the parse tree
   */
  void enterStatement(SqlParser.StatementContext ctx);

  /**
   * Exit a parse tree produced by {@link SqlParser#statement}.
   *
   * @param ctx the parse tree
   */
  void exitStatement(SqlParser.StatementContext ctx);

  /**
   * Enter a parse tree produced by {@link SqlParser#selectStatement}.
   *
   * @param ctx the parse tree
   */
  void enterSelectStatement(SqlParser.SelectStatementContext ctx);

  /**
   * Exit a parse tree produced by {@link SqlParser#selectStatement}.
   *
   * @param ctx the parse tree
   */
  void exitSelectStatement(SqlParser.SelectStatementContext ctx);

  /**
   * Enter a parse tree produced by {@link SqlParser#joinClause}.
   *
   * @param ctx the parse tree
   */
  void enterJoinClause(SqlParser.JoinClauseContext ctx);

  /**
   * Exit a parse tree produced by {@link SqlParser#joinClause}.
   *
   * @param ctx the parse tree
   */
  void exitJoinClause(SqlParser.JoinClauseContext ctx);

  /**
   * Enter a parse tree produced by {@link SqlParser#limitClause}.
   *
   * @param ctx the parse tree
   */
  void enterLimitClause(SqlParser.LimitClauseContext ctx);

  /**
   * Exit a parse tree produced by {@link SqlParser#limitClause}.
   *
   * @param ctx the parse tree
   */
  void exitLimitClause(SqlParser.LimitClauseContext ctx);

  /**
   * Enter a parse tree produced by {@link SqlParser#insertStatement}.
   *
   * @param ctx the parse tree
   */
  void enterInsertStatement(SqlParser.InsertStatementContext ctx);

  /**
   * Exit a parse tree produced by {@link SqlParser#insertStatement}.
   *
   * @param ctx the parse tree
   */
  void exitInsertStatement(SqlParser.InsertStatementContext ctx);

  /**
   * Enter a parse tree produced by {@link SqlParser#updateStatement}.
   *
   * @param ctx the parse tree
   */
  void enterUpdateStatement(SqlParser.UpdateStatementContext ctx);

  /**
   * Exit a parse tree produced by {@link SqlParser#updateStatement}.
   *
   * @param ctx the parse tree
   */
  void exitUpdateStatement(SqlParser.UpdateStatementContext ctx);

  /**
   * Enter a parse tree produced by {@link SqlParser#createTableStatement}.
   *
   * @param ctx the parse tree
   */
  void enterCreateTableStatement(SqlParser.CreateTableStatementContext ctx);

  /**
   * Exit a parse tree produced by {@link SqlParser#createTableStatement}.
   *
   * @param ctx the parse tree
   */
  void exitCreateTableStatement(SqlParser.CreateTableStatementContext ctx);

  /**
   * Enter a parse tree produced by {@link SqlParser#columnDefinitionList}.
   *
   * @param ctx the parse tree
   */
  void enterColumnDefinitionList(SqlParser.ColumnDefinitionListContext ctx);

  /**
   * Exit a parse tree produced by {@link SqlParser#columnDefinitionList}.
   *
   * @param ctx the parse tree
   */
  void exitColumnDefinitionList(SqlParser.ColumnDefinitionListContext ctx);

  /**
   * Enter a parse tree produced by {@link SqlParser#columnDefinition}.
   *
   * @param ctx the parse tree
   */
  void enterColumnDefinition(SqlParser.ColumnDefinitionContext ctx);

  /**
   * Exit a parse tree produced by {@link SqlParser#columnDefinition}.
   *
   * @param ctx the parse tree
   */
  void exitColumnDefinition(SqlParser.ColumnDefinitionContext ctx);

  /**
   * Enter a parse tree produced by {@link SqlParser#assignmentList}.
   *
   * @param ctx the parse tree
   */
  void enterAssignmentList(SqlParser.AssignmentListContext ctx);

  /**
   * Exit a parse tree produced by {@link SqlParser#assignmentList}.
   *
   * @param ctx the parse tree
   */
  void exitAssignmentList(SqlParser.AssignmentListContext ctx);

  /**
   * Enter a parse tree produced by {@link SqlParser#assignment}.
   *
   * @param ctx the parse tree
   */
  void enterAssignment(SqlParser.AssignmentContext ctx);

  /**
   * Exit a parse tree produced by {@link SqlParser#assignment}.
   *
   * @param ctx the parse tree
   */
  void exitAssignment(SqlParser.AssignmentContext ctx);

  /**
   * Enter a parse tree produced by {@link SqlParser#columnList}.
   *
   * @param ctx the parse tree
   */
  void enterColumnList(SqlParser.ColumnListContext ctx);

  /**
   * Exit a parse tree produced by {@link SqlParser#columnList}.
   *
   * @param ctx the parse tree
   */
  void exitColumnList(SqlParser.ColumnListContext ctx);

  /**
   * Enter a parse tree produced by {@link SqlParser#valueSetList}.
   *
   * @param ctx the parse tree
   */
  void enterValueSetList(SqlParser.ValueSetListContext ctx);

  /**
   * Exit a parse tree produced by {@link SqlParser#valueSetList}.
   *
   * @param ctx the parse tree
   */
  void exitValueSetList(SqlParser.ValueSetListContext ctx);

  /**
   * Enter a parse tree produced by {@link SqlParser#valueList}.
   *
   * @param ctx the parse tree
   */
  void enterValueList(SqlParser.ValueListContext ctx);

  /**
   * Exit a parse tree produced by {@link SqlParser#valueList}.
   *
   * @param ctx the parse tree
   */
  void exitValueList(SqlParser.ValueListContext ctx);

  /**
   * Enter a parse tree produced by {@link SqlParser#whereClause}.
   *
   * @param ctx the parse tree
   */
  void enterWhereClause(SqlParser.WhereClauseContext ctx);

  /**
   * Exit a parse tree produced by {@link SqlParser#whereClause}.
   *
   * @param ctx the parse tree
   */
  void exitWhereClause(SqlParser.WhereClauseContext ctx);

  /**
   * Enter a parse tree produced by the {@code SimpleColumnCondition} labeled alternative in {@link
   * SqlParser#condition}.
   *
   * @param ctx the parse tree
   */
  void enterSimpleColumnCondition(SqlParser.SimpleColumnConditionContext ctx);

  /**
   * Exit a parse tree produced by the {@code SimpleColumnCondition} labeled alternative in {@link
   * SqlParser#condition}.
   *
   * @param ctx the parse tree
   */
  void exitSimpleColumnCondition(SqlParser.SimpleColumnConditionContext ctx);

  /**
   * Enter a parse tree produced by the {@code SimpleValueCondition} labeled alternative in {@link
   * SqlParser#condition}.
   *
   * @param ctx the parse tree
   */
  void enterSimpleValueCondition(SqlParser.SimpleValueConditionContext ctx);

  /**
   * Exit a parse tree produced by the {@code SimpleValueCondition} labeled alternative in {@link
   * SqlParser#condition}.
   *
   * @param ctx the parse tree
   */
  void exitSimpleValueCondition(SqlParser.SimpleValueConditionContext ctx);

  /**
   * Enter a parse tree produced by the {@code OrCondition} labeled alternative in {@link
   * SqlParser#condition}.
   *
   * @param ctx the parse tree
   */
  void enterOrCondition(SqlParser.OrConditionContext ctx);

  /**
   * Exit a parse tree produced by the {@code OrCondition} labeled alternative in {@link
   * SqlParser#condition}.
   *
   * @param ctx the parse tree
   */
  void exitOrCondition(SqlParser.OrConditionContext ctx);

  /**
   * Enter a parse tree produced by the {@code AndCondition} labeled alternative in {@link
   * SqlParser#condition}.
   *
   * @param ctx the parse tree
   */
  void enterAndCondition(SqlParser.AndConditionContext ctx);

  /**
   * Exit a parse tree produced by the {@code AndCondition} labeled alternative in {@link
   * SqlParser#condition}.
   *
   * @param ctx the parse tree
   */
  void exitAndCondition(SqlParser.AndConditionContext ctx);

  /**
   * Enter a parse tree produced by {@link SqlParser#operator}.
   *
   * @param ctx the parse tree
   */
  void enterOperator(SqlParser.OperatorContext ctx);

  /**
   * Exit a parse tree produced by {@link SqlParser#operator}.
   *
   * @param ctx the parse tree
   */
  void exitOperator(SqlParser.OperatorContext ctx);

  /**
   * Enter a parse tree produced by {@link SqlParser#dataType}.
   *
   * @param ctx the parse tree
   */
  void enterDataType(SqlParser.DataTypeContext ctx);

  /**
   * Exit a parse tree produced by {@link SqlParser#dataType}.
   *
   * @param ctx the parse tree
   */
  void exitDataType(SqlParser.DataTypeContext ctx);

  /**
   * Enter a parse tree produced by {@link SqlParser#columnName}.
   *
   * @param ctx the parse tree
   */
  void enterColumnName(SqlParser.ColumnNameContext ctx);

  /**
   * Exit a parse tree produced by {@link SqlParser#columnName}.
   *
   * @param ctx the parse tree
   */
  void exitColumnName(SqlParser.ColumnNameContext ctx);

  /**
   * Enter a parse tree produced by {@link SqlParser#tableName}.
   *
   * @param ctx the parse tree
   */
  void enterTableName(SqlParser.TableNameContext ctx);

  /**
   * Exit a parse tree produced by {@link SqlParser#tableName}.
   *
   * @param ctx the parse tree
   */
  void exitTableName(SqlParser.TableNameContext ctx);

  /**
   * Enter a parse tree produced by {@link SqlParser#value}.
   *
   * @param ctx the parse tree
   */
  void enterValue(SqlParser.ValueContext ctx);

  /**
   * Exit a parse tree produced by {@link SqlParser#value}.
   *
   * @param ctx the parse tree
   */
  void exitValue(SqlParser.ValueContext ctx);
}
