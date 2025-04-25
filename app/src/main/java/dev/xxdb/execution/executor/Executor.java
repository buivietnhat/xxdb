package dev.xxdb.execution.executor;

import dev.xxdb.execution.ExecutionException;
import dev.xxdb.storage.tuple.Tuple;

import java.util.Optional;

public abstract class Executor {
  protected final ExecutionContext ctx;

  public Executor(ExecutionContext ctx) {
    this.ctx = ctx;
  }

  public abstract Optional<TupleResult> next() throws ExecutionException;
}
