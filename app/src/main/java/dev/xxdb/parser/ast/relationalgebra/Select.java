package dev.xxdb.parser.ast.relationalgebra;

public class Select implements RelationalAlgebra {
  private String tableName;
  private Predicate predicate;

  public Select() {}

  public void setTableName(String tableName) {
    this.tableName = tableName;
  }

  public String getTableName() {
    return tableName;
  }

  public void setPredicate(Predicate predicate) {
    this.predicate = predicate;
  }

  public Predicate getPredicate() {
    return predicate;
  }

  public Select(Predicate predicate, String tableName) {
    this.predicate = predicate;
    this.tableName = tableName;
  }

  @Override
  public String toString() {
    return "Select{" +
        "tableName='" + tableName + '\'' +
        ", predicate=" + predicate +
        '}';
  }
}
