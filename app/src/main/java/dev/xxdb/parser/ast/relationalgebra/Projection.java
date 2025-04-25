package dev.xxdb.parser.ast.relationalgebra;

import java.util.List;
import java.util.Optional;

public class Projection implements RelationalAlgebra {
  private Optional<Integer> limit = Optional.empty();
  private final List<String> columns;

  public Projection(List<String> columns) {
    this.columns = columns;
  }

  public List<String> getColumns() {
    return columns;
  }

  public Optional<Integer> getLimit() {
    return limit;
  }

  @Override
  public String toString() {
    String rep = "Projection{";
    if (limit.isPresent()) {
      rep += "limit=" + limit.get() + ", ";
    }
    rep += "columns=" + columns;
    rep += '}';
    return rep;
  }

  public void setLimit(int limit) {
    this.limit = Optional.of(limit);
  }
}
