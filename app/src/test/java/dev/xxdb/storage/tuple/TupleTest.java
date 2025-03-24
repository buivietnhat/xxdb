package dev.xxdb.storage.tuple;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.*;

public class TupleTest {

  @Test()
  public void testAssertionsEnabled() {
    assertThrows(
        AssertionError.class,
        () -> {
          assert false; // make sure assertions are enabled with VM argument: -ea
        });
  }

  @Test
  public void testEquals() {
    Tuple t1 = new Tuple(new char[] {'a', 'b', 'c', 'd'});
    Tuple t2 = new Tuple(new char[] {'a', 'b', 'c', 'd'});
    assertEquals(t1, t2);
  }
}
