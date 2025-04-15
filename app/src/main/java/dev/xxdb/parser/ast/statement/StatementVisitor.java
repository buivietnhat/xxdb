package dev.xxdb.parser.ast.statement;

public interface StatementVisitor {
  void visitInsertNode(Insert node);

  void visitColumnDefinitionNode(ColumnDefinition node);

  void visitColumnDefinitionListNode(ColumnDefinitionList node);

  void visitColumnListNode(ColumnList node);

  void visitValueListNode(ValueList node);

  void visitCreateTableNode(CreateTable node);

  void visitIntValueNode(IntValue node);

  void visitStringValueNode(StringValue node);

  void visitSelectNode(Select node);

  void visitJoinNode(Join node);

  void visitLimitNode(Limit node);

  void visitWhereNode(Where node);

  void visitAndConditionNode(AndCondition node);

  void visitOrConditionNode(OrCondition node);

  void visitSimpleValueConditionNode(SimpleValueCondition node);

  void visitSimpleColumnConditionNode(SimpleColumnCondition node);

  void visitOperatorNode(Operator node);
}
