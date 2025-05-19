package dev.xxdb.parser.ast.statement;

import dev.xxdb.types.Op;

public class Operator implements Statement {
  private final Op op;

  public Operator(String op) {
    switch (op) {
      case ">" -> this.op = Op.GREATER_THAN;
      case "<" -> this.op = Op.LESS_THAN;
      case "=" -> this.op = Op.EQUALS;
      case ">=" -> this.op = Op.GREATER_THAN_OR_EQUAL;
      case "<=" -> this.op = Op.LESS_THAN_OR_EQUAL;
      case "!=" -> this.op = Op.NOT_EQUALS;
      default -> throw new IllegalArgumentException("Unknown operator: " + op);
    }
  }

  public Op getOp() {
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
