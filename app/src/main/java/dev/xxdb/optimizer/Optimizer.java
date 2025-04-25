package dev.xxdb.optimizer;

import dev.xxdb.execution.plan.*;
import dev.xxdb.parser.ast.plan.*;
import dev.xxdb.parser.ast.plan.CreateTablePlan;
import dev.xxdb.parser.ast.plan.InsertPlan;
import dev.xxdb.parser.ast.relationalgebra.*;
import dev.xxdb.types.Predicate;
import dev.xxdb.types.PredicateType;
import dev.xxdb.types.SimplePredicate;

import java.util.ArrayList;
import java.util.List;

public class Optimizer implements LogicalPlanVisitor<PhysicalPlan> {
  public static class PredicateBuidler implements PredicateVisitor<Predicate> {
    public Predicate build(List<Select> selects, List<PredicateType> types) {
      if (selects.size() > 2) {
        throw new RuntimeException("unimplemeted");
     }
      List<Predicate> predicates = new ArrayList<>();
      for (Select s : selects) {
        predicates.addLast(s.getPredicate().accept(this));
      }

      Predicate currPre = predicates.get(0);
      for (int i = 0; i < types.size(); i++) {
        switch (types.get(i)) {
          case AND -> {
            currPre = new dev.xxdb.types.AndPredicate(currPre, predicates.get(i+1));
          }
          case OR -> {
            currPre = new dev.xxdb.types.OrPredicate(currPre, predicates.get(i+1));
          }
        }
      }

      return currPre;
    }

    @Override
    public Predicate visitAndPredicate(AndPredicate predicate) {
      Predicate left = predicate.getLeft().accept(this);
      Predicate right = predicate.getRight().accept(this);
      return new dev.xxdb.types.AndPredicate(left, right);
    }

    @Override
    public Predicate visitOrPredicate(OrPredicate predicate) {
      throw new RuntimeException("unimplemented");
//      Predicate left = predicate.getLeft().accept(this);
//      Predicate right = predicate.getRight().accept(this);
//      return new dev.xxdb.types.AndPredicate(left, right);
    }

    @Override
    public Predicate visitValuePredicate(ValuePredicate predicate) {
      return new SimplePredicate(predicate.getColumn(), predicate.getValue(), predicate.getOp());
    }
  }

  private final PredicateBuidler predicateBuilder = new PredicateBuidler();

  /**
   * Run the optimize algorithm to generate a physical plan that can be fed to an execution engine to run
   *
   * @param plan logical plan
   * @return physical plan
   */
  public PhysicalPlan run(LogicalPlan plan) {
    return plan.accept(this);
  }

  @Override
  public PhysicalPlan visitCreateTablePlan(CreateTablePlan plan) {
    return new dev.xxdb.execution.plan.CreateTablePlan(plan.getTableName(), plan.getColumnList(), plan.getColumnDefList());
  }

  @Override
  public PhysicalPlan visitSelectPlan(SelectPlan plan) {
    PhysicalPlan root = null;
    PhysicalPlan curr = null;

    if (plan.getProjection().getLimit().isPresent()) {
      root = new LimitPlan(plan.getProjection().getLimit().get());
      curr = root;
    }

    PhysicalPlan projection = new ProjectionPlan(plan.getProjection().getColumns());
    if (root == null) {
      root = projection;
      curr = root;
    } else {
      curr.setLeftChild(projection);
      curr = projection;
    }

    if (!plan.getSelects().isEmpty()) {
      Predicate predicate = predicateBuilder.build(plan.getSelects(), plan.getTypes());
      FilterPlan filter = new FilterPlan(predicate);
      curr.setLeftChild(filter);
      curr = filter;
    }

    if (plan.getJoin().isPresent()) {
      // default to use HashJoin for now
      Join join = plan.getJoin().get();
      SequentialScanPlan leftChild = new SequentialScanPlan(join.getLeftTable());
      SequentialScanPlan rightChild = new SequentialScanPlan(join.getRightTable());
      HashJoinPlan hashJoinPlan = new HashJoinPlan(join.getPredicate().getLeftColumn(), join.getPredicate().getRightColumn());
      hashJoinPlan.setLeftChild(leftChild);
      hashJoinPlan.setRightChild(rightChild);
      curr.setLeftChild(hashJoinPlan);
    } else {
      SequentialScanPlan sequentialScanPlan = new SequentialScanPlan(plan.getLeftTableName());
      curr.setLeftChild(sequentialScanPlan);
    }

    return root;
  }

  @Override
  public PhysicalPlan visitInsertPlan(InsertPlan plan) {
    dev.xxdb.execution.plan.InsertPlan insert = new dev.xxdb.execution.plan.InsertPlan(plan.getTableName());
    ValueScanPlan valueScanPlan = new ValueScanPlan(plan.getTableName(), plan.getColumns(), plan.getValues());
    insert.setLeftChild(valueScanPlan);
    return insert;
  }
}