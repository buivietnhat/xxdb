package dev.xxdb.server;

public class XxdbException extends Exception {
  public XxdbException() {
    super();
  }

  public XxdbException(String msg) {
    super(msg);
  }

  public XxdbException(Throwable cause) {
    super(cause);
  }

  public XxdbException(String msg, Throwable cause) {
    super(msg, cause);
  }
}
