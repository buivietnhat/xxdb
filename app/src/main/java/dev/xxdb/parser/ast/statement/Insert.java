package dev.xxdb.parser.ast.statement;

public class Insert implements Statement {
  private final String tableName;
  private final Statement columns;
  private final Statement values;

  public String getTableName() {
    return tableName;
  }

  public Statement getColumns() {
    return columns;
  }

  public Statement getValues() {
    return values;
  }

  public Insert(String tableName, Statement columns, Statement values) {
    this.tableName = tableName;
    this.columns = columns;
    this.values = values;
  }

  @Override
  public String toString() {
    StringBuilder rep = new StringBuilder("Insert ");
    rep.append(columns.toString()).append(" ");
    rep.append(values.toString());
    return rep.toString();
  }

  @Override
  public void accept(StatementVisitor visitor) {
    visitor.visitInsertNode(this);
  }
}
