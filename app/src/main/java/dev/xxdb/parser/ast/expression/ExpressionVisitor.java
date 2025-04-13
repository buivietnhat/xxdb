package dev.xxdb.parser.ast.expression;

public interface ExpressionVisitor {
  void visitInsertNode(InsertNode node);

  void visitColumnDefinitionNode(ColumnDefinitionNode node);

  void visitColumnDefinitionListNode(ColumnDefinitionListNode node);

  void visitColumnListNode(ColumnListNode node);

  void visitCreateTableNode(CreateTableNode node);

  void visitIntValueNode(IntValue node);
}
