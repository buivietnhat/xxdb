package dev.xxdb.execution.executor;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.*;

public class FilterExecutorTest {
  @Test
  public void testAssertionsEnabled() {
    assertThrows(
        AssertionError.class,
        () -> {
          assert false; // make sure assertions are enabled with VM argument: -ea
        });
  }

  @Nested
  class NextTest {
    // Testing strategy
    // + partition on the table: empty, has tuple data
    // + partition on the filter predicate and the tuples: no tuple satisfies, some
    // tuples satify

    // cover the Table is empty
    @Test
    void emptyTable() {}
  }
}
