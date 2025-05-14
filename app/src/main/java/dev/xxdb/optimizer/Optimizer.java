package dev.xxdb.optimizer;

import dev.xxdb.catalog.Catalog;
import dev.xxdb.catalog.Schema;
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
    private String currentTable;
    private final Catalog catalog;

    public PredicateBuidler(Catalog catalog) {
      this.catalog = catalog;
    }

    public Predicate build(List<Select> selects, List<PredicateType> types) {
      if (selects.size() > 2) {
        throw new RuntimeException("unimplemeted");
      }
      List<Predicate> predicates = new ArrayList<>();
      for (Select s : selects) {
        currentTable = s.getTableName();
        predicates.addLast(s.getPredicate().accept(this));
      }

      Predicate currPre = predicates.get(0);
      for (int i = 0; i < types.size(); i++) {
        switch (types.get(i)) {
          case AND -> {
            currPre = new dev.xxdb.types.AndPredicate(currPre, predicates.get(i + 1));
          }
          case OR -> {
            currPre = new dev.xxdb.types.OrPredicate(currPre, predicates.get(i + 1));
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
      return new SimplePredicate(currentTable, predicate.getColumn(), predicate.getValue(), predicate.getOp(), catalog);
    }
  }

  private final PredicateBuidler predicateBuilder;
  private final Catalog catalog;

  public Optimizer(Catalog catalog) {
    this.catalog = catalog;
    this.predicateBuilder = new PredicateBuidler(catalog);
  }

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

  // decorate the table name for each column name, e.g Table.col1
  private List<String> decorateTableName(List<String> columns, String tableName) {
    return columns.stream()
        .map(col -> col.contains(".") ? col : tableName + "." + col)
        .toList();
  }


  @Override
  public PhysicalPlan visitSelectPlan(SelectPlan plan) {
    PhysicalPlan currentTreeNode = null;

    if (plan.getJoin().isPresent()) {
      // default to use HashJoin for now
      Join join = plan.getJoin().get();
      SequentialScanPlan leftChild = new SequentialScanPlan(join.getLeftTable());
      Schema leftSchema = catalog.getTableSchema(join.getLeftTable()).get();
      leftChild.setOutputSchema(leftSchema);

      SequentialScanPlan rightChild = new SequentialScanPlan(join.getRightTable());
      Schema rightSchema = catalog.getTableSchema(join.getRightTable()).get();
      rightChild.setOutputSchema(rightSchema);

      String leftColumn = join.getPredicate().getLeftColumn();
      String rightColumn = join.getPredicate().getRightColumn();
      // make sure the left column belongs to the left table
      if (!leftColumn.split("\\.")[0].equals(plan.getLeftTableName())) {
        String temp = leftColumn;
        leftColumn = rightColumn;
        rightColumn = temp;
      }

      HashJoinPlan hashJoinPlan = new HashJoinPlan(leftColumn, rightColumn);
      hashJoinPlan.setLeftChild(leftChild);
      hashJoinPlan.setRightChild(rightChild);
      hashJoinPlan.setOutputSchema(leftSchema.join(rightSchema));
      currentTreeNode = hashJoinPlan;
    } else {
      SequentialScanPlan sequentialScanPlan = new SequentialScanPlan(plan.getLeftTableName());
      sequentialScanPlan.setOutputSchema(catalog.getTableSchema(plan.getLeftTableName()).get());
      currentTreeNode = sequentialScanPlan;
    }

    if (!plan.getSelects().isEmpty()) {
      Predicate predicate = predicateBuilder.build(plan.getSelects(), plan.getTypes());
      FilterPlan filter = new FilterPlan(predicate);
      filter.setLeftChild(currentTreeNode);
      filter.setOutputSchema(currentTreeNode.getOutputSchema());
      currentTreeNode = filter;
    }

    List<String> columns = decorateTableName(plan.getProjection().getColumns(), plan.getLeftTableName());
    PhysicalPlan projection = new ProjectionPlan(columns);
    projection.setLeftChild(currentTreeNode);
    projection.setOutputSchema(currentTreeNode.getOutputSchema().filter(columns));
    currentTreeNode = projection;

    if (plan.getProjection().getLimit().isPresent()) {
      LimitPlan limitPlan = new LimitPlan(plan.getProjection().getLimit().get());
      limitPlan.setLeftChild(currentTreeNode);
      limitPlan.setOutputSchema(currentTreeNode.getOutputSchema());
      currentTreeNode = limitPlan;
    }

    return currentTreeNode;
  }

  @Override
  public PhysicalPlan visitInsertPlan(InsertPlan plan) {
    dev.xxdb.execution.plan.InsertPlan insert = new dev.xxdb.execution.plan.InsertPlan(plan.getTableName());
    ValueScanPlan valueScanPlan = new ValueScanPlan(plan.getTableName(), plan.getColumns(), plan.getValueSets());
    insert.setLeftChild(valueScanPlan);
    return insert;
  }
}