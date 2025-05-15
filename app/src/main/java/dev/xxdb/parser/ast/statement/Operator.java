package dev.xxdb.parser.ast.statement;

import dev.xxdb.types.Ops;

public class Operator implements Statement {
  private final Ops op;

  public Operator(String op) {
    switch (op) {
      case ">" -> this.op = Ops.GREATER_THAN;
      case "<" -> this.op = Ops.LESS_THAN;
      case "=" -> this.op = Ops.EQUALS;
      case ">=" -> this.op = Ops.GREATER_THAN_OR_EQUAL;
      case "<=" -> this.op = Ops.LESS_THAN_OR_EQUAL;
      case "!=" -> this.op = Ops.NOT_EQUALS;
      default -> throw new IllegalArgumentException("Unknown operator: " + op);
    }
  }

  public Ops getOp() {
    return op;
  }

  @Override
  public void accept(StatementVisitor visitor) {
    visitor.visitOperatorNode(this);
  }

  @Override
  public String toString() {
    return "Operator{" + "op=" + op + '}';
  }
}
