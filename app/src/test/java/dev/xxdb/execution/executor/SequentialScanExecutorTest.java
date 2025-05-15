package dev.xxdb.execution.executor;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import dev.xxdb.catalog.Catalog;
import dev.xxdb.execution.ExecutionException;
import dev.xxdb.execution.plan.SequentialScanPlan;
import dev.xxdb.storage.disk.DiskManager;
import dev.xxdb.storage.file.HeapFile;
import dev.xxdb.storage.file.TableHeap;
import dev.xxdb.storage.page.SlottedPageRepository;
import dev.xxdb.storage.tuple.RID;
import dev.xxdb.storage.tuple.Tuple;
import dev.xxdb.storage.tuple.exception.TupleException;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class SequentialScanExecutorTest {

  @Nested
  class NextTest {
    @Test
    void basicTest() throws IOException, TupleException, ExecutionException {
      ExecutionContext mockCtx = mock(ExecutionContext.class);
      Catalog mockCatalog = mock(Catalog.class);
      SequentialScanPlan plan = new SequentialScanPlan("FOO");

      DiskManager diskManager = new DiskManager("dump.db");
      SlottedPageRepository pageRepository = new HeapFile(diskManager);
      TableHeap tableHeap = new TableHeap(pageRepository);

      List<RID> rids = new ArrayList<>();
      List<Tuple> tuples =
          List.of(
              new Tuple("tuple1".getBytes(StandardCharsets.UTF_8)),
              new Tuple("tuple2".getBytes(StandardCharsets.UTF_8)),
              new Tuple("tuple3".getBytes(StandardCharsets.UTF_8)),
              new Tuple("tuple4".getBytes(StandardCharsets.UTF_8)));

      for (Tuple tuple : tuples) {
        rids.add(tableHeap.addTuple(tuple));
      }

      when(mockCtx.catalog()).thenReturn(mockCatalog);
      when(mockCatalog.getTable(eq("FOO"))).thenReturn(Optional.of(tableHeap));

      SequentialScanExecutor executor = new SequentialScanExecutor(mockCtx, plan);
      executor.init();

      List<TupleResult> tupleResults = new ArrayList<>();
      Optional<TupleResult> tupleResult = executor.next();
      while (tupleResult.isPresent()) {
        tupleResults.add(tupleResult.get());
        tupleResult = executor.next();
      }

      assertEquals(4, tupleResults.size());
      for (int i = 0; i < rids.size(); i++) {
        assertTrue(tupleResults.contains(new TupleResult(tuples.get(i), rids.get(i))));
      }

      diskManager.close();
      Files.deleteIfExists(Paths.get("dump.db"));
    }
  }
}
