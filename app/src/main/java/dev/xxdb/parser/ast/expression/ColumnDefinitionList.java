package dev.xxdb.parser.ast.expression;

import java.util.Collections;
import java.util.List;

public class ColumnDefinitionList implements Expression {
  private final List<Expression> columnDefinitions;

  public ColumnDefinitionList(List<Expression> columnDefinitions) {
    this.columnDefinitions = columnDefinitions;
  }

  public List<Expression> getColumnDefinitions() {
    return Collections.unmodifiableList(columnDefinitions);
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
