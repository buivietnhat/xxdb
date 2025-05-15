package dev.xxdb.parser.ast.relationalgebra;

import dev.xxdb.types.Ops;

public class JoinPredicate implements Predicate {
  private Ops ops;
  private String leftColumn;
  private String rightColumn;

  public Ops getOps() {
    return ops;
  }

  public void setOps(Ops ops) {
    this.ops = ops;
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

  public JoinPredicate(Ops ops, String leftColumn, String rightColumn) {
    this.ops = ops;
    this.leftColumn = leftColumn;
    this.rightColumn = rightColumn;
  }

  @Override
  public String toString() {
    return "JoinPredicate{"
        + "ops="
        + ops
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
