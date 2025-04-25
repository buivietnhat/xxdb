// Generated from C:/workspace/xxdb/app/src/main/java/dev/xxdb/parser/antlr/Sql.g4 by ANTLR 4.13.2
package dev.xxdb.parser.antlr;
import org.antlr.v4.runtime.tree.ParseTreeVisitor;

/**
 * This interface defines a complete generic visitor for a parse tree produced
 * by {@link SqlParser}.
 *
 * @param <T> The return type of the visit operation. Use {@link Void} for
 * operations with no return type.
 */
public interface SqlVisitor<T> extends ParseTreeVisitor<T> {
	/**
	 * Visit a parse tree produced by {@link SqlParser#sql}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSql(SqlParser.SqlContext ctx);
	/**
	 * Visit a parse tree produced by {@link SqlParser#statement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitStatement(SqlParser.StatementContext ctx);
	/**
	 * Visit a parse tree produced by {@link SqlParser#selectStatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSelectStatement(SqlParser.SelectStatementContext ctx);
	/**
	 * Visit a parse tree produced by {@link SqlParser#joinClause}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitJoinClause(SqlParser.JoinClauseContext ctx);
	/**
	 * Visit a parse tree produced by {@link SqlParser#limitClause}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLimitClause(SqlParser.LimitClauseContext ctx);
	/**
	 * Visit a parse tree produced by {@link SqlParser#insertStatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitInsertStatement(SqlParser.InsertStatementContext ctx);
	/**
	 * Visit a parse tree produced by {@link SqlParser#updateStatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitUpdateStatement(SqlParser.UpdateStatementContext ctx);
	/**
	 * Visit a parse tree produced by {@link SqlParser#createTableStatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCreateTableStatement(SqlParser.CreateTableStatementContext ctx);
	/**
	 * Visit a parse tree produced by {@link SqlParser#columnDefinitionList}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitColumnDefinitionList(SqlParser.ColumnDefinitionListContext ctx);
	/**
	 * Visit a parse tree produced by {@link SqlParser#columnDefinition}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitColumnDefinition(SqlParser.ColumnDefinitionContext ctx);
	/**
	 * Visit a parse tree produced by {@link SqlParser#assignmentList}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAssignmentList(SqlParser.AssignmentListContext ctx);
	/**
	 * Visit a parse tree produced by {@link SqlParser#assignment}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAssignment(SqlParser.AssignmentContext ctx);
	/**
	 * Visit a parse tree produced by {@link SqlParser#columnList}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitColumnList(SqlParser.ColumnListContext ctx);
	/**
	 * Visit a parse tree produced by {@link SqlParser#valueList}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitValueList(SqlParser.ValueListContext ctx);
	/**
	 * Visit a parse tree produced by {@link SqlParser#whereClause}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitWhereClause(SqlParser.WhereClauseContext ctx);
	/**
	 * Visit a parse tree produced by the {@code SimpleColumnCondition}
	 * labeled alternative in {@link SqlParser#condition}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSimpleColumnCondition(SqlParser.SimpleColumnConditionContext ctx);
	/**
	 * Visit a parse tree produced by the {@code SimpleValueCondition}
	 * labeled alternative in {@link SqlParser#condition}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSimpleValueCondition(SqlParser.SimpleValueConditionContext ctx);
	/**
	 * Visit a parse tree produced by the {@code OrCondition}
	 * labeled alternative in {@link SqlParser#condition}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitOrCondition(SqlParser.OrConditionContext ctx);
	/**
	 * Visit a parse tree produced by the {@code AndCondition}
	 * labeled alternative in {@link SqlParser#condition}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAndCondition(SqlParser.AndConditionContext ctx);
	/**
	 * Visit a parse tree produced by {@link SqlParser#operator}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitOperator(SqlParser.OperatorContext ctx);
	/**
	 * Visit a parse tree produced by {@link SqlParser#dataType}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDataType(SqlParser.DataTypeContext ctx);
	/**
	 * Visit a parse tree produced by {@link SqlParser#columnName}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitColumnName(SqlParser.ColumnNameContext ctx);
	/**
	 * Visit a parse tree produced by {@link SqlParser#tableName}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTableName(SqlParser.TableNameContext ctx);
	/**
	 * Visit a parse tree produced by {@link SqlParser#value}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitValue(SqlParser.ValueContext ctx);
}