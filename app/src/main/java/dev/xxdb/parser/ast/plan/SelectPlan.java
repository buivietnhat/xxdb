package dev.xxdb.parser.ast.plan;

import dev.xxdb.parser.ast.relationalgebra.*;
import dev.xxdb.types.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class SelectPlan implements LogicalPlan {
  //           Limit
  //            |
  //           Sort
  //            |
  //        Projection
  //            |
  //    Select <Predicate> *
  //            |
  //    Join <Predicate> ?

  public static class Builder {
    private final SelectPlan plan = new SelectPlan();
    private final Join.Builder joinBuilder = new Join.Builder();
    private String leftTableName;
    public boolean buildingJoin = false;

    public void setJoinOps(Ops ops) {
      joinBuilder.setJoinOps(ops);
    }

    public void setLeftJoinColumn(String leftColumn) {
      joinBuilder.setLeftJoinColumn(leftColumn);
    }

    public void setRightJoinColumn(String rightColumn) {
      joinBuilder.setRightJoinColumn(rightColumn);
    }

    public void setLeftTableName(String leftTableName) {
      this.leftTableName = leftTableName;
      plan.setLeftTableName(leftTableName);
    }

    public String getLeftTableName() {
      return leftTableName;
    }

    public void setLeftJoinTable(String leftTable) {
      joinBuilder.setLeftTable(leftTable);
    }

    public void setRightJoinTable(String rightTable) {
      joinBuilder.setRightTable(rightTable);
    }

    public void setLimit(int limit) {
      plan.addLimit(limit);
    }

    public void addProjection(List<String> columns) {
      plan.addProjection(columns);
    }

    public void setValueForPredicate(Value value) {
      ((ValuePredicate) plan.getCurrentFilter().getPredicate()).setValue(value);
    }

    public void addColumnNameForPredicate(String columnName) {
      plan.addFilter(new Select());
      plan.getCurrentFilter().setTableName(leftTableName);
      plan.getCurrentFilter().setPredicate(new ValuePredicate());
      ((ValuePredicate) plan.getCurrentFilter().getPredicate()).setColumn(columnName);
    }

    public void setPredicateOp(Ops op) {
      ((ValuePredicate) plan.getCurrentFilter().getPredicate()).setOp(op);
    }

    public void addPredicateType(PredicateType type) {
      plan.addPredicateType(type);
    }

    public SelectPlan build() {
      Join join = joinBuilder.build();
      if (join.valid()) {
        plan.addJoin(join);
      }
      return plan;
    }

  }

  private String leftTableName;
  private Projection projection;
  private final List<Select> selects = new ArrayList<>();
  private final List<PredicateType> types = new ArrayList<>();
  private Optional<Join> join = Optional.empty();

  void addProjection(List<String> columns) {
    projection = new Projection(columns);
  }

  public void setLeftTableName(String leftTableName) {
    this.leftTableName = leftTableName;
  }

  void addFilter(Select select) {
    selects.addLast(select);
  }

  void addPredicateType(PredicateType type) {
    types.addLast(type);
  }

  Select getCurrentFilter() {
    return selects.getLast();
  }

  void addJoin(Join join) {
    this.join = Optional.of(join);
  }

  void addLimit(int limit) {
    projection.setLimit(limit);
  }

  public String getLeftTableName() {
    return leftTableName;
  }

  public Projection getProjection() {
    return projection;
  }

  public List<Select> getSelects() {
    return selects;
  }

  public List<PredicateType> getTypes() {
    return types;
  }

  public Optional<Join> getJoin() {
    return join;
  }

  @Override
  public String toString() {
    String rep = "SelectPlan{";
    rep += "table: " + leftTableName;
    rep += ", projection=" + projection;

    if (!selects.isEmpty()) {
      rep += ", predicates=[";
      for (int i = 0; i < selects.size() - 1; i++) {
        rep += selects.get(i) + " " + types.get(i) + " ";
      }
      rep += selects.get(selects.size() - 1);
      rep += "]";
    }

    if (join.isPresent()) {
      rep += ", join=" + join.get();
    }
    rep += '}';

    return rep;
  }

  @Override
  public <T> T accept(LogicalPlanVisitor<T> visitor) {
    return visitor.visitSelectPlan(this);
  }
}
