package dev.xxdb.storage.page;

import static org.junit.jupiter.api.Assertions.*;

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
      char[] data = p.getSerializedData();
      assertEquals(Page.PAGE_SIZE, data.length);
    }

    // cover this produced by constructor with given data
    @Test
    void testDataPage() {
      String hello = "Hello";
      char[] charHello = hello.toCharArray();
      Page p = new Page(0, charHello);

      char[] data = p.getSerializedData();
      assertEquals(Page.PAGE_SIZE, data.length);

      char[] usefulData = Arrays.copyOfRange(data, 0, hello.length());
      assertArrayEquals(charHello, usefulData);
    }
  }
}
