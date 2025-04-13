package dev.xxdb.parser.ast.expression;

public interface Expression {
  @Override
  String toString();

  void accept(ExpressionVisitor visitor);
}
