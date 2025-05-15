package dev.xxdb.storage.page;

import static org.junit.jupiter.api.Assertions.*;

import java.nio.ByteBuffer;
import java.util.Arrays;
import org.junit.jupiter.api.*;

public class PageTest {
  @Test()
  public void testAssertionsEnabled() {
    assertThrows(
        AssertionError.class,
        () -> {
          assert false; // make sure assertions are enabled with VM argument: -ea
        });
  }

  @Nested
  class GetSerializedDataTest {
    // Testing strategy
    //
    // partition on this
    // + produced by Constructor: empty, with given data

    // cover this produced by empty constructor
    @Test
    void testEmptyPage() {
      Page p = new Page(0);
      byte[] data = p.getSerializedData();
      assertEquals(Page.PAGE_SIZE, data.length);
    }

    // cover this produced by constructor with given data
    @Test
    void testDataPage() {
      String hello = "Hello";
      int cap = Page.PAGE_HEADER_SIZE + hello.length();
      ByteBuffer buffer = ByteBuffer.allocate(cap);
      byte[] charHello = hello.getBytes();
      buffer.putInt(1);
      buffer.put(charHello);
      Page p = new Page(buffer.array());

      byte[] data = p.getSerializedData();
      assertEquals(Page.PAGE_SIZE, data.length);

      byte[] usefulData = Arrays.copyOfRange(data, 0, cap);
      ByteBuffer getBackBuffer = ByteBuffer.wrap(usefulData);
      int pid = getBackBuffer.getInt(0);
      assertEquals(1, pid);
      assertArrayEquals(
          charHello, Arrays.copyOfRange(usefulData, Page.PAGE_HEADER_SIZE, usefulData.length));
    }
  }
}
