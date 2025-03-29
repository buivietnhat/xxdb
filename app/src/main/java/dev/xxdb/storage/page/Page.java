package dev.xxdb.storage.page;

import java.nio.ByteBuffer;
import java.util.Arrays;
import java.util.Objects;

class PageHeader {}

public class Page {
  public static final int PAGE_SIZE = 4096;
  public static final int INVALID_PAGE_ID = -1;
  private static final int PAGE_ID_OFFSET = 0;
  public static final int PAGE_HEADER_SIZE = 4; // bytes (includes pageId)
  protected final ByteBuffer buffer;

  // Abstraction function:
  // - AF(buffer, pageId) = an memory representation of a data page
  //
  // Rep invariant:
  // - buffer.array().length = PAGE_SIZE
  //
  // Safety from rep exposure
  // - all fields are private and final
  // - getSerializedData() return a clone of this page (defensive copy)

  // Check that the rep invariant is true
  private void checkRep() {
    assert buffer.array().length == PAGE_SIZE && getPageId() != INVALID_PAGE_ID;
  }

  /**
   * Construct a blank new Page
   *
   * @param pageId: id of this Page
   */
  public Page(final int pageId) {
    byte[] backedArray = new byte[PAGE_SIZE];
    this.buffer = ByteBuffer.wrap(backedArray);
    setPageId(pageId);
    checkRep();
  }

  /**
   * Construct new Page given the serialized data
   *
   * @param data: requires length <= PAGE_SIZE
   */
  public Page(final byte[] data) {
    byte[] backedArray = new byte[PAGE_SIZE];
    System.arraycopy(data, 0, backedArray, 0, data.length);
    this.buffer = ByteBuffer.wrap(backedArray);
    checkRep();
  }

  /** Get serialization data of this Page */
  public byte[] getSerializedData() {
    return Arrays.copyOf(buffer.array(), PAGE_SIZE);
  }

  /** Get PageId (globally unique) of this Page */
  public int getPageId() {
    return buffer.getInt(PAGE_ID_OFFSET);
  }

  private void setPageId(int pageId) {
    buffer.putInt(PAGE_ID_OFFSET, pageId);
  }

  @Override
  public boolean equals(Object o) {
    if (!(o instanceof Page page)) return false;
    return Arrays.equals(buffer.array(), page.buffer.array());
  }

  @Override
  public int hashCode() {
    return Objects.hashCode(buffer.array());
  }
}
