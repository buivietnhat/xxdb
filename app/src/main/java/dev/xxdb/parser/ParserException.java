package dev.xxdb.parser;

import dev.xxdb.server.XxdbException;

public class ParserException extends XxdbException {
  /** Construct a parser exception with no message. */
  public ParserException() {
    super();
  }

  /** Construct a parser exception with the specified message. */
  public ParserException(String msg) {
    super(msg);
  }

  /** Construct a parser exception with the specified cause and no message. */
  public ParserException(Throwable cause) {
    super(cause);
  }

  /** Construct a parser exception with the specified message and cause. */
  public ParserException(String msg, Throwable cause) {
    super(msg, cause);
  }
}
