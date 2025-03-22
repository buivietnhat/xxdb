package dev.xxdb.storage.page;

class PageHeader {}

public abstract class Page {
  // private final PageHeader header;
  // private final int pid;

  public Page(char[] data) {
    throw new RuntimeException("unimplemented");
  }

  public char[] getSerializedData() {
    throw new RuntimeException("unimplemented");
  }

  int getPid() {
    throw new RuntimeException("unimplemented");
  }
}
