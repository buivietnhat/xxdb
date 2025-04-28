package dev.xxdb.execution.executor;

import dev.xxdb.catalog.Catalog;
import dev.xxdb.execution.ExecutionException;
import dev.xxdb.execution.plan.InsertPlan;
import dev.xxdb.storage.disk.DiskManager;
import dev.xxdb.storage.file.HeapFile;
import dev.xxdb.storage.file.TableHeap;
import dev.xxdb.storage.tuple.RID;
import dev.xxdb.storage.tuple.Tuple;
import dev.xxdb.storage.tuple.exception.TupleException;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class InsertExecutorTest {
  @Nested
  class NextTest {
    // Testing strategy
    //  + partition on InsertPlan
    //    + partition on the table: exist, not existed

    // cover inserting on exising table
    @Test
    void onExistingTable() throws ExecutionException, TupleException {
      ExecutionContext mockCtx = mock(ExecutionContext.class);
      Catalog mockCatalog = mock(Catalog.class);
      when(mockCtx.catalog()).thenReturn(mockCatalog);

      TableHeap tableHeap = mock(TableHeap.class);

      String tableName = "table";
      when(mockCatalog.getTable(eq(tableName))).thenReturn(Optional.of(tableHeap));

      InsertPlan plan = new InsertPlan(tableName);

      Executor child = mock(Executor.class);
      InsertExecutor insertExecutor = new InsertExecutor(mockCtx, plan, child);
      insertExecutor.init();

      List<TupleResult> tupleResults = List.of(
          new TupleResult(new Tuple("hello".getBytes()), RID.INVALID_RID),
          new TupleResult(new Tuple("goodbye".getBytes()), RID.INVALID_RID)
      );

      when(child.next())
          .thenReturn(Optional.of(tupleResults.get(0)))
          .thenReturn(Optional.of(tupleResults.get(1)))
          .thenReturn(Optional.empty());

      Optional<TupleResult> tupleResult = insertExecutor.next();
      // Insert executor is a pipeline terminator, should not produce any results
      assertTrue(tupleResult.isEmpty());

      // Verify addTuple is called with the correct tuples
      verify(tableHeap).addTuple(eq(tupleResults.get(0).tuple()));
      verify(tableHeap).addTuple(eq(tupleResults.get(1).tuple()));
    }
  }
}