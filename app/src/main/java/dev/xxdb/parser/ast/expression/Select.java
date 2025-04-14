package dev.xxdb.parser.ast.expression;

public class Select implements Expression {
  @Override
  public void accept(ExpressionVisitor visitor) {
    throw new RuntimeException("unimplemented");
  }
}
