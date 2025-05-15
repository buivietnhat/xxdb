package dev.xxdb.storage.file;

import static org.junit.jupiter.api.Assertions.*;

import dev.xxdb.storage.disk.DiskManager;
import dev.xxdb.storage.page.Page;
import dev.xxdb.storage.page.SlottedPageRepository;
import dev.xxdb.storage.tuple.RID;
import dev.xxdb.storage.tuple.Tuple;
import dev.xxdb.storage.tuple.exception.TupleException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class TableHeapTest {
  @Test()
  public void testAssertionsEnabled() {
    assertThrows(
        AssertionError.class,
        () -> {
          assert false; // make sure assertions are enabled with VM argument: -ea
        });
  }

  private DiskManager diskManager;
  private TableHeap tableHeap;
  private static final String FILE_PATH = "dump.db";

  @BeforeEach
  void setup() throws IOException {
    diskManager = new DiskManager(FILE_PATH);
    SlottedPageRepository pageRepository = new HeapFile(diskManager);
    tableHeap = new TableHeap(pageRepository);
  }

  @AfterEach
  void cleanup() throws IOException {
    diskManager.close();
    Files.deleteIfExists(Paths.get(FILE_PATH));
  }

  @Nested
  class GetTupleTest {
    // Testing strategy
    // + partition on existence of the tuple on this: has the tuple, does not have
    // + partition on this: fresh table, has size > Page.PAGE_SIZE (spans multiple
    // Pages)

    // cover this Table is a fresh table
    @Test
    void freshTable() {
      assertThrows(TupleException.class, () -> tableHeap.getTuple(new RID(0, 0)));
    }

    // cover this has size > Page.PAGE_SIZE, doesn't have the tuple
    @Test
    void hasDataDoesNotHaveTuple() throws TupleException {
      List<RID> rids = new ArrayList<>();
      // add a bunch of dummy tuples
      for (int i = 0; i < 100; i++) {
        rids.addLast(tableHeap.addTuple(new Tuple(new byte[1000])));
      }

      int maxPageId = rids.stream().mapToInt(RID::pageId).max().getAsInt(); // trust me

      assertThrows(TupleException.class, () -> tableHeap.getTuple(new RID(maxPageId + 1, 0)));
    }

    // cover this has size > Page.PAGE_SIZE, has the tuple
    @Test
    void hasDataHasTuple() throws TupleException {
      List<RID> rids = new ArrayList<>();
      List<Tuple> tuples = new ArrayList<>();
      Random random = new Random();
      // add a bunch of dummy tuples
      for (int i = 0; i < 100; i++) {
        byte[] randomBytes = new byte[1000];
        random.nextBytes(randomBytes);
        Tuple tuple = new Tuple(randomBytes);
        tuples.addLast(tuple);
        rids.addLast(tableHeap.addTuple(tuple));
        tableHeap.addTuple(tuple);
      }

      int randomIdx = random.nextInt(100);
      assertEquals(tuples.get(randomIdx), tableHeap.getTuple(rids.get(randomIdx)));
    }
  }

  @Nested
  class AddTupleTest {
    // Testing strategy
    // + partition on the tuple size: < Page.PAGE_SIZE, >= Page.PAGE_SIZE

    // cover the tuple with size >= Page.PAGE_SIZE
    @Test
    void tupleWithValidSize() {
      assertThrows(
          TupleException.class, () -> tableHeap.addTuple(new Tuple(new byte[Page.PAGE_SIZE])));
    }
  }

  @Nested
  class DeleteTupleTest {
    // Testing strategy
    // + partition on existence of the tuple on this: has the tuple, does not have
    // + partition on this: fresh table, has data

    // cover this is a fresh table, doesn't have the tuple
    @Test
    void freshTable() {
      assertFalse(tableHeap.deleteTuple(new RID(1, 1)));
    }

    // cover this has data, has the tuple
    @Test
    void hasDataHasTuple() throws TupleException {
      List<RID> rids = new ArrayList<>();
      List<Tuple> tuples = new ArrayList<>();
      Random random = new Random();
      // add a bunch of dummy tuples
      for (int i = 0; i < 100; i++) {
        byte[] randomBytes = new byte[1000];
        random.nextBytes(randomBytes);
        Tuple tuple = new Tuple(randomBytes);
        tuples.addLast(tuple);
        rids.addLast(tableHeap.addTuple(tuple));
        tableHeap.addTuple(tuple);
      }

      int randomIdx = random.nextInt(100);
      assertTrue(tableHeap.deleteTuple(rids.get(randomIdx)));

      // now we should not be able to read it back
      assertThrows(TupleException.class, () -> tableHeap.getTuple(rids.get(randomIdx)));
    }
  }
}
