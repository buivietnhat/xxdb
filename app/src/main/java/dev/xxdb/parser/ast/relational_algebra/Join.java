package dev.xxdb.parser.ast.relational_algebra;

import java.util.Optional;

public class Join implements RelationalAlgebra {
  private final String leftTable;
  private final String rightTable;
  private Predicate predicate;

  public Join(String leftTable, String rightTable) {
    this.leftTable = leftTable;
    this.rightTable = rightTable;
  }

  public void setPredicate(Predicate predicate) {
    this.predicate = predicate;
  }

  public Join(String leftTable, String rightTable, Predicate predicate) {
    this.leftTable = leftTable;
    this.rightTable = rightTable;
    this.predicate = predicate;
  }
}
