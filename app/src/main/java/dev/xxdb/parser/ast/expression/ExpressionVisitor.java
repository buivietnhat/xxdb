package dev.xxdb.parser.ast.expression;

public interface ExpressionVisitor {
  void visitInsertNode(Insert node);

  void visitColumnDefinitionNode(ColumnDefinition node);

  void visitColumnDefinitionListNode(ColumnDefinitionList node);

  void visitColumnListNode(ColumnList node);

  void visitValueListNode(ValueList node);

  void visitCreateTableNode(CreateTable node);

  void visitIntValueNode(IntValue node);

  void visitStringValueNode(StringValue node);
}
