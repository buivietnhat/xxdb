package dev.xxdb.parser.ast.statement;

public interface Statement {
  @Override
  String toString();

  void accept(StatementVisitor visitor);
}
