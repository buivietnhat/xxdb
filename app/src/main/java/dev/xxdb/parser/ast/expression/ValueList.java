package dev.xxdb.parser.ast.expression;

import java.util.Collections;
import java.util.List;

public class ValueList implements Expression {
  private final List<Expression> values;

  public ValueList(List<Expression> values) {
    this.values = values;
  }

  public List<Expression> getValues() {
    return Collections.unmodifiableList(values);
  }

  @Override
  public String toString() {
    StringBuilder rep = new StringBuilder();
    rep.append("(");
    for (Expression value : values) {
      rep.append(value.toString()).append(" ");
    }
    rep.deleteCharAt(rep.length() - 1);
    rep.append(")");
    return rep.toString();
  }

  @Override
  public void accept(ExpressionVisitor visitor) {
    visitor.visitValueListNode(this);
  }
}
