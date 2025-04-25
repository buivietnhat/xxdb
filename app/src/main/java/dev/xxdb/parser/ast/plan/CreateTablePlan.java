package dev.xxdb.parser.ast.plan;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CreateTablePlan implements LogicalPlan {
  private String tableName;
  private List<String> columns = new ArrayList<>();
  private List<String> types = new ArrayList<>();

  public CreateTablePlan(String tableName, List<String> columns, List<String> types) {
    this.tableName = tableName;
    this.columns = columns;
    this.types = types;
  }

  public CreateTablePlan() {}

  public void setTableName(String table) {
    tableName = table;
  }

  public void addColumnDefinition(String columnName, String columnType) {
    columns.addLast(columnName);
    types.addLast(columnType);
  }

  public String getTableName() {
    return tableName;
  }

  public List<String> getColumnList() {
    return Collections.unmodifiableList(columns);
  }

  public List<String> getColumnDefList() {
    return Collections.unmodifiableList(types);
  }

  @Override
  public String toString() {
    StringBuilder rep = new StringBuilder("CreateTable ");
    rep.append(tableName).append(" ");
    rep.append("( ");
    for (int i = 0; i < columns.size(); i++) {
      rep.append(columns.get(i)).append(" ").append(types.get(i)).append(",");
    }
    rep.deleteCharAt(rep.length() - 1);
    rep.append(" )");

    return rep.toString();
  }

  @Override
  public <T> T accept(LogicalPlanVisitor<T> visitor) {
    return visitor.visitCreateTablePlan(this);
  }
}
