package dev.xxdb.parser.ast.plan;

import dev.xxdb.parser.ast.relational_algebra.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class SelectPlan implements LogicalPlan {
  //        Projection
  //            |
  //      Select <Predicate> *
  //            |
  //     Join <Predicate> ?
  private String tableName;
  private Projection projection;
  private final List<Select> selects = new ArrayList<>();
  private Optional<Join> join;

  public void setTableName(String tableName) {
    this.tableName = tableName;
  }

  public String getTableName() {
    return tableName;
  }

  void addProjection(List<String> columns) {
    projection = new Projection(columns);
  }

  void addFilter(Predicate predicate) {
    selects.addLast(new Select(predicate));
  }

  void addJoin(String leftTable, String rightTable) {
    join = Optional.of(new Join(leftTable, rightTable));
  }

  void setJoinPredicate(Predicate predicate) {
    join.get().setPredicate(predicate);
  }

  @Override
  public void accept(LogicalPlanVisitor visitor) {
    throw new RuntimeException("unimplemented");
  }
}
