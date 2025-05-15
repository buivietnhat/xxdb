package dev.xxdb.execution.executor;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import dev.xxdb.catalog.Catalog;
import dev.xxdb.execution.ExecutionException;
import dev.xxdb.execution.plan.CreateTablePlan;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class CreateTableExecutorTest {

  @Nested
  class NextTest {
    // Testing strategy
    // + partition on existing of the table: none, already exist

    // cover create a new table that the name is already existed
    @Test
    void testNextOnExistingTable() {
      Catalog mockCatalog = mock(Catalog.class);
      ExecutionContext mockCtx = mock(ExecutionContext.class);
      when(mockCtx.catalog()).thenReturn(mockCatalog);

      CreateTablePlan plan =
          new CreateTablePlan("FOO", List.of("col1", "col2"), List.of("INT", "VARCHAR"));
      CreateTableExecutor executor = new CreateTableExecutor(mockCtx, plan);
      when(mockCatalog.createNewTable(eq("FOO"), any(), any())).thenReturn(false);
      assertThrows(ExecutionException.class, executor::next);
    }

    // cover create a new table that the name is not existed
    @Test
    void testNextOnNonExistTable() throws ExecutionException {
      Catalog mockCatalog = mock(Catalog.class);
      ExecutionContext mockCtx = mock(ExecutionContext.class);
      when(mockCtx.catalog()).thenReturn(mockCatalog);

      CreateTablePlan plan =
          new CreateTablePlan("FOO", List.of("col1", "col2"), List.of("INT", "VARCHAR"));
      CreateTableExecutor executor = new CreateTableExecutor(mockCtx, plan);
      executor.init();
      when(mockCatalog.createNewTable(eq("FOO"), any(), any())).thenReturn(true);
      Optional<TupleResult> next = executor.next();
      assertFalse(next.isPresent());
    }
  }
}
