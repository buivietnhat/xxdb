package dev.xxdb.parser.ast.plan;

import dev.xxdb.types.Value;

import java.util.ArrayList;
import java.util.List;

public class InsertPlan implements LogicalPlan {
  private String tableName;
  private List<String> columns = new ArrayList<>();
  private final List<Value> values = new ArrayList<>();

  public String getTableName() {
    return tableName;
  }

  public List<Value> getValues() {
    return values;
  }

  public void addValue(Value value) {
    this.values.addLast(value);
  }

  public void addColumn(String column) {
    this.columns.addLast(column);
  }

  public void setColumns(List<String> columns) {
    this.columns = columns;
  }

  public List<String> getColumns() {
    return columns;
  }

  public void setTableName(String tableName) {
    this.tableName = tableName;
  }

  @Override
  public <T> T accept(LogicalPlanVisitor<T> visitor) {
    throw new RuntimeException("unimplemented");
  }

  @Override
  public String toString() {
    return "InsertPlan{" +
        "tableName='" + tableName + '\'' +
        ", columns=" + columns +
        ", values=" + values +
        '}';
  }
}
