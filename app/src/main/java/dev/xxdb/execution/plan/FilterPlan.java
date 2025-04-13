package dev.xxdb.execution.plan;

import dev.xxdb.storage.tuple.Tuple;
import java.util.function.Predicate;

public class FilterPlan extends AbstractPlan {
  public Predicate<Tuple> getFilterFunction() {
    throw new RuntimeException("unimplemented");
  }
}
