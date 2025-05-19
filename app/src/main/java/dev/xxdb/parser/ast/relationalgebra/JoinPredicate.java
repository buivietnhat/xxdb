package dev.xxdb.parser.ast.relationalgebra;

import dev.xxdb.types.Op;

public class JoinPredicate implements Predicate {
  private Op op;
  private String leftColumn;
  private String rightColumn;

  public Op getOps() {
    return op;
  }

  public void setOps(Op op) {
    this.op = op;
  }

  public String getLeftColumn() {
    return leftColumn;
  }

  public void setLeftColumn(String leftColumn) {
    this.leftColumn = leftColumn;
  }

  public String getRightColumn() {
    return rightColumn;
  }

  public void setRightColumn(String rightColumn) {
    this.rightColumn = rightColumn;
  }

  public JoinPredicate() {}

  public JoinPredicate(Op op, String leftColumn, String rightColumn) {
    this.op = op;
    this.leftColumn = leftColumn;
    this.rightColumn = rightColumn;
  }

  @Override
  public String toString() {
    return "JoinPredicate{"
        + "ops="
        + op
        + ", leftColumn='"
        + leftColumn
        + '\''
        + ", rightColumn='"
        + rightColumn
        + '\''
        + '}';
  }

  @Override
  public <T> T accept(PredicateVisitor<T> visitor) {
    throw new RuntimeException("unimplemented");
  }

  //  @Override
  //  public boolean evaluate(Tuple tuple) {
  //    return false;
  //  }
}
