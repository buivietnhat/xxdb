package dev.xxdb.execution;

import dev.xxdb.server.XxdbException;

public class ExecutionException extends XxdbException {
  /** Construct a execution exception with no message. */
  public ExecutionException() {
    super();
  }

  /** Construct a execution exception with the specified message. */
  public ExecutionException(String msg) {
    super(msg);
  }

  /** Construct a execution exception with the specified cause and no message. */
  public ExecutionException(Throwable cause) {
    super(cause);
  }

  /** Construct a execution exception with the specified message and cause. */
  public ExecutionException(String msg, Throwable cause) {
    super(msg, cause);
  }
}
