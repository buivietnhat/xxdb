package dev.xxdb.parser.ast.relationalgebra;

import dev.xxdb.types.Ops;

public class Join implements RelationalAlgebra {
  public static class Builder {
    private final Join join = new Join();
    private final JoinPredicate joinPredicate = new JoinPredicate();

    public void setLeftTable(String leftTable) {
      join.setLeftTable(leftTable);
    }

    public void setRightTable(String rightTable) {
      join.setRightTable(rightTable);
    }

    public void setJoinOps(Ops ops) {
      joinPredicate.setOps(ops);
    }

    public void setPredicate(JoinPredicate predicate) {
      join.setPredicate(predicate);
    }

    public void setLeftJoinColumn(String leftColumn) {
      joinPredicate.setLeftColumn(leftColumn);
    }

    public void setRightJoinColumn(String rightColumn) {
      joinPredicate.setRightColumn(rightColumn);
    }

    public Join build() {
      join.setPredicate(joinPredicate);
      return join;
    }
  }

  private String leftTable;
  private String rightTable;
  private JoinPredicate predicate;

  public String getLeftTable() {
    return leftTable;
  }

  public String getRightTable() {
    return rightTable;
  }

  public JoinPredicate getPredicate() {
    return predicate;
  }

  public boolean valid() {
    return leftTable != null && rightTable != null && predicate != null;
  }

  public void setLeftTable(String leftTable) {
    this.leftTable = leftTable;
  }

  public void setRightTable(String rightTable) {
    this.rightTable = rightTable;
  }

  public void setPredicate(JoinPredicate predicate) {
    this.predicate = predicate;
  }

  public Join() {}

  public Join(String leftTable, String rightTable, JoinPredicate predicate) {
    this.leftTable = leftTable;
    this.rightTable = rightTable;
    this.predicate = predicate;
  }

  @Override
  public String toString() {
    return "Join{"
        + "leftTable='"
        + leftTable
        + '\''
        + ", rightTable='"
        + rightTable
        + '\''
        + ", predicate="
        + predicate
        + '}';
  }
}
