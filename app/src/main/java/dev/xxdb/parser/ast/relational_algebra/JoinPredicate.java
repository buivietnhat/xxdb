package dev.xxdb.parser.ast.relational_algebra;

import dev.xxdb.types.Ops;

public class JoinPredicate implements Predicate {
  private final Ops ops;
  private final String leftColumn;
  private final String rightColumn;

  public JoinPredicate(Ops ops, String leftColumn, String rightColumn) {
    this.ops = ops;
    this.leftColumn = leftColumn;
    this.rightColumn = rightColumn;
  }
}
