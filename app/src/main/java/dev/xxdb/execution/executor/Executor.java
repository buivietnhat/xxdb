package dev.xxdb.execution.executor;

import dev.xxdb.execution.ExecutionException;
import java.util.Optional;

public abstract class Executor {
  protected final ExecutionContext ctx;

  public Executor(ExecutionContext ctx) {
    this.ctx = ctx;
  }

  /**
   * initialize the executor
   */
  public abstract void init() throws ExecutionException;

  /**
   * Produce once-at-a-time tuple as in Iterator Model
   * @return TupleResult or None if there are no more tuples
   * @throws ExecutionException if something went wrong
   */
  public abstract Optional<TupleResult> next() throws ExecutionException;
}
