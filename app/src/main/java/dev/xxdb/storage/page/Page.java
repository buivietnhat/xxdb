package dev.xxdb.storage.page;

import java.util.Arrays;

class PageHeader {}

public class Page {
  public static final int PAGE_SIZE = 4096;
  public static final int INVALID_PAGE_ID = -1;
  protected final char[] data = new char[PAGE_SIZE];
  protected final int pageId;

  // Abstraction function:
  // - AF(data, pageId) = an memory representation of a data page
  //
  // Rep invariant:
  // - data.length = PAGE_SIZE
  //
  // Safety from rep exposure
  // - all fields are private and final
  // - getSerializedData() return a clone of this page (defensive copy)

  // Check that the rep invariant is true
  private void checkRep() {
    assert data.length == PAGE_SIZE && pageId != INVALID_PAGE_ID;
  }

  /** Construct a blank new Page
   * @param pageId: id of this Page
   */
  public Page(final int pageId) {
    this.pageId = pageId;
    checkRep();
  }

  /**
   * Construct new Page given the serialized data
   *
   * @param pageId: id of this Page
   * @param data: requires length <= PAGE_SIZE
   */
  public Page(final int pageId, final char[] data) {
    this.pageId = pageId;
    System.arraycopy(this.data, 0, data, 0, data.length);
    checkRep();
  }

  /** Get serialization data of this Page */
  public char[] getSerializedData() {
    return Arrays.copyOf(data, PAGE_SIZE);
  }

  /** Get PageId (globally unique) of this Page */
  int getPageId() {
    return pageId;
  }
}
