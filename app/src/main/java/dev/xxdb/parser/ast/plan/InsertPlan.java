package dev.xxdb.parser.ast.plan;

import dev.xxdb.types.Value;
import java.util.ArrayList;
import java.util.List;

public class InsertPlan implements LogicalPlan {
  private String tableName;
  private List<String> columns = new ArrayList<>();
  private List<List<Value>> valueSets = new ArrayList<>();
  private List<Value> currentValueSet = new ArrayList<>();

  public String getTableName() {
    return tableName;
  }

  public List<List<Value>> getValueSets() {
    return valueSets;
  }

  public void addValue(Value value) {
    currentValueSet.add(value);
  }

  public void finishValueSet() {
    if (!currentValueSet.isEmpty()) {
      valueSets.add(new ArrayList<>(currentValueSet));
      currentValueSet.clear();
    }
  }

  public void addColumn(String column) {
    this.columns.add(column);
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

  public InsertPlan() {}

  public InsertPlan(String tableName, List<String> columns, List<List<Value>> valueSets) {
    this.tableName = tableName;
    this.columns = columns;
    this.valueSets = valueSets;
  }

  @Override
  public <T> T accept(LogicalPlanVisitor<T> visitor) {
    return visitor.visitInsertPlan(this);
  }

  @Override
  public String toString() {
    return "InsertPlan{"
        + "tableName='"
        + tableName
        + '\''
        + ", columns="
        + columns
        + ", valueSets="
        + valueSets
        + '}';
  }
}
