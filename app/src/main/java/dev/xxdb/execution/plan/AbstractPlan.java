package dev.xxdb.execution.plan;

import dev.xxdb.catalog.Schema;

public abstract class AbstractPlan {
  public Schema getTableSchema() {
    throw new RuntimeException("unimplemented");
  }
}
