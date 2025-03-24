package dev.xxdb.storage.tuple;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.*;

public class ValueTest {

  @Test()
  public void testAssertionsEnabled() {
    assertThrows(
        AssertionError.class,
        () -> {
          assert false; // make sure assertions are enabled with VM argument: -ea
        });
  }

  // @Nested
  // class GetSerializedDataTests {
  // // Testing strategy
  // //
  // // partition on this:
  // // + produced by makeBoolean()
  // // + produced by makeInteger()
  // // + produced by makeVarchar()
  //
  // // cover this produced by makeBoolean()
  // @Test
  // void testBoolean() {
  // Value value = Value.makeBoolean();
  // }
  // }
}
