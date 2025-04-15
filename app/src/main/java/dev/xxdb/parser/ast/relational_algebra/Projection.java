package dev.xxdb.parser.ast.relational_algebra;

import java.util.List;
import java.util.Optional;

public class Projection implements RelationalAlgebra {
  private final List<String> columns;

  public Projection(List<String> columns) {
    this.columns = columns;
  }
}
