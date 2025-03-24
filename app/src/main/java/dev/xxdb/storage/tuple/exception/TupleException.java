package dev.xxdb.storage.tuple.exception;

import dev.xxdb.storage.StorageException;

public class TupleException extends StorageException {
  /** Construct a tuple exception with no message. */
  public TupleException() {
    super();
  }

  /** Construct a tuple exception with the specified message. */
  public TupleException(String msg) {
    super(msg);
  }

  /** Construct a tuple exception with the specified cause and no message. */
  public TupleException(Throwable cause) {
    super(cause);
  }

  /** Construct a tuple exception with the specified message and cause. */
  public TupleException(String msg, Throwable cause) {
    super(msg, cause);
  }
}
