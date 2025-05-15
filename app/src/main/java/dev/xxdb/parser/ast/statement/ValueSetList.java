package dev.xxdb.parser.ast.statement;

import java.util.List;

public class ValueSetList implements Statement {
  private final List<ValueList> valueSets;

  public ValueSetList(List<ValueList> valueSets) {
    this.valueSets = valueSets;
  }

  public List<ValueList> getValueSets() {
    return valueSets;
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    for (int i = 0; i < valueSets.size(); i++) {
      if (i > 0) {
        sb.append(", ");
      }
      sb.append(valueSets.get(i).toString());
    }
    return sb.toString();
  }

  @Override
  public void accept(StatementVisitor visitor) {
    visitor.visitValueSetList(this);
  }
}
