package dev.xxdb.parser.ast.expression;

import java.util.List;

public class ColumnDefinitionListNode implements Expression {
  private final List<Expression> columnDefinitions;

  public ColumnDefinitionListNode(List<Expression> columnDefinitions) {
    this.columnDefinitions = columnDefinitions;
  }

  @Override
  public String toString() {
    StringBuilder rep = new StringBuilder();
    for (Expression child : columnDefinitions) {
      rep.append(child.toString()).append(" ");
    }
    return rep.toString();
  }

  @Override
  public void accept(ExpressionVisitor visitor) {
    visitor.visitColumnDefinitionListNode(this);
  }
}
