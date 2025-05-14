package dev.xxdb.execution.plan;

import dev.xxdb.catalog.Schema;

public abstract class PhysicalPlan {
  protected PhysicalPlan leftChild;
  protected PhysicalPlan rightChild;
  protected Schema outputSchema;

  public PhysicalPlan getLeftChild() {
    return leftChild;
  }

  public PhysicalPlan getRightChild() {
    return rightChild;
  }

  public void setLeftChild(PhysicalPlan child) {
    this.leftChild = child;
  }

  public void setRightChild(PhysicalPlan child) {
    this.rightChild = child;
  }

  public void setOutputSchema(Schema outputSchema) {
    this.outputSchema = outputSchema;
  }

  /**
   * @return output Schema of tuples after execute this plan
   */
  public Schema getOutputSchema() {
    return outputSchema;
  }

  public abstract <T> T accept(PhysicalPlanVisitor<T> visitor);

  @Override
  abstract public String toString();
}
