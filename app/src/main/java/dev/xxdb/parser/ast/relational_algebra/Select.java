package dev.xxdb.parser.ast.relational_algebra;

import java.util.List;
import java.util.Optional;

public class Select implements RelationalAlgebra {
  private Optional<String> tableName;
  private final Predicate predicate;

  public Select(Predicate predicate) {
    this.predicate = predicate;
  }

  public Select(String tableName, Predicate predicate) {
    this.tableName = Optional.of(tableName);
    this.predicate = predicate;
  }
}
