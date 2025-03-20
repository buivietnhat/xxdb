package dev.xxdb.storage;

import dev.xxdb.server.XxdbException;

public class StorageException extends XxdbException {
  /** Construct a storage exception with no message. */
  public StorageException() {
    super();
  }

  /** Construct a storage exception with the specified message. */
  public StorageException(String msg) {
    super(msg);
  }

  /** Construct a storage exception with the specified cause and no message. */
  public StorageException(Throwable cause) {
    super(cause);
  }

  /** Construct a storage exception with the specified message and cause. */
  public StorageException(String msg, Throwable cause) {
    super(msg, cause);
  }
}
