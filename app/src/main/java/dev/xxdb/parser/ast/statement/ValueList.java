package dev.xxdb.parser.ast.statement;

import java.util.Collections;
import java.util.List;

public class ValueList implements Statement {
  private final List<Statement> values;

  public ValueList(List<Statement> values) {
    this.values = values;
  }

  public List<Statement> getValues() {
    return Collections.unmodifiableList(values);
  }

  @Override
  public String toString() {
    StringBuilder rep = new StringBuilder();
    rep.append("(");
    for (Statement value : values) {
      rep.append(value.toString()).append(" ");
    }
    rep.deleteCharAt(rep.length() - 1);
    rep.append(")");
    return rep.toString();
  }

  @Override
  public void accept(StatementVisitor visitor) {
    visitor.visitValueListNode(this);
  }
}
