package dev.xxdb.storage.tuple.exception;

public class TupleNotFoundException extends TupleException {
  public TupleNotFoundException() {
    super();
  }

  public TupleNotFoundException(String msg) {
    super(msg);
  }

  public TupleNotFoundException(Throwable cause) {
    super(cause);
  }

  public TupleNotFoundException(String msg, Throwable cause) {
    super(msg, cause);
  }
}
