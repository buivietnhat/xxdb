package dev.xxdb.storage.page;

import static org.junit.jupiter.api.Assertions.*;

import dev.xxdb.storage.tuple.RID;
import dev.xxdb.storage.tuple.Tuple;
import dev.xxdb.storage.tuple.exception.TupleException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import org.junit.jupiter.api.*;

public class SlottedPageTest {

  @Test()
  public void testAssertionsEnabled() {
    assertThrows(
        AssertionError.class,
        () -> {
          assert false; // make sure assertions are enabled with VM argument: -ea
        });
  }

  @Nested
  class GetTupleTest {
    // Testing strategy
    //
    // partition on appearance of the record:
    // + has record
    // + has no record: rid is in the page, rid is in other page
    //
    // partition on this: a fresh page, has records

    // cover this is a fresh page (no data), has data
    @Test
    void testFreshPage() {
      SlottedPage page = new SlottedPage(0);
      assertThrows(TupleException.class, () -> page.getTuple(new RID(1, 1)));
    }

    // cover this Page has data, but doesn't contain the one to find (the record
    // indicates it is in
    // this page)
    @Test
    void testHasDataNotFoundTheOneSamePage() throws TupleException {
      SlottedPage page = new SlottedPage(0);
      RID rid1 = page.addTuple(new Tuple(new byte[] { 'a', 'b', 'c' }));
      RID rid2 = page.addTuple(new Tuple(new byte[] { 'a', 'c', 'a' }));
      RID rid3 = page.addTuple(new Tuple(new byte[] { 'c', 'b', 'a' }));

      // same page, but non existed slot number
      RID findingRid = new RID(0, rid1.slotNumber() + rid2.slotNumber() + rid3.slotNumber());
      assertThrows(TupleException.class, () -> page.getTuple(findingRid));
    }

    // cover this Page has data, but doesn't contain the one to find (the record
    // indicates it is in
    // different page)
    @Test
    void testHasDataNotFoundTheOneDifferentPage() throws TupleException {
      SlottedPage page = new SlottedPage(0);
      page.addTuple(new Tuple(new byte[] { 'a', 'b', 'c' }));
      page.addTuple(new Tuple(new byte[] { 'a', 'c', 'a' }));

      // different pageID
      RID findingRid = new RID(1, 0);
      assertThrows(TupleException.class, () -> page.getTuple(findingRid));
    }

    // cover this has data and contains the record
    @Test
    void testHasDataHasTheRecord() throws TupleException {
      SlottedPage page = new SlottedPage(0);
      String data = "hello";
      Tuple theTuple = new Tuple(data.getBytes());

      RID theRid = page.addTuple(theTuple);
      page.addTuple(new Tuple(new byte[] { 'd', 'u' }));

      Tuple theOneFound = page.getTuple(theRid);
      assertEquals(theTuple, theOneFound);
    }
  }

  @Nested
  class AddTupleTest {
    // Testing strategy
    // + partition on this: fresh page, has some data and still has room for the
    // tuple, has no room for the tuple
    // + partition on the added tuple size: less than PAGE_SIZE, greater or equal
    // than PAGE_SIZE

    // cover this Page is a fresh page, adding tuple >= PAGE_SIZE
    @Test
    void testFreshPageWithTupleGreaterThanPageSize() {
      byte[] tupleData = new byte[Page.PAGE_SIZE];
      Arrays.fill(tupleData, (byte) 10);

      SlottedPage page = new SlottedPage(0);
      assertThrows(TupleException.class, () -> page.addTuple(new Tuple(tupleData)));
    }

    // cover this Page has some data, and still has room for the tuple with size <
    // PAGE_SIZE
    @Test
    void testPageHasDataAndHasRoom() throws TupleException {
      SlottedPage page = new SlottedPage(0);
      page.addTuple(new Tuple(new byte[] { 1, 2, 3 }));
      page.addTuple(new Tuple(new byte[] { 4, 5, 6 }));
      page.addTuple(new Tuple(new byte[] { 7, 8, 9 }));

      Tuple theTuple = new Tuple(new byte[] { 10, 10, 10, 10, 10 });
      RID rid = page.addTuple(theTuple);

      Tuple getBackTuple = page.getTuple(rid);
      assertEquals(theTuple, getBackTuple);
    }

    // cover this Page has some data, not full but still not has room for the
    // tuple
    @Test
    void testPageHasDataButNoRoom() throws TupleException {
      SlottedPage page = new SlottedPage(0);
      page.addTuple(new Tuple(new byte[1000]));
      page.addTuple(new Tuple(new byte[1000]));

      int unfitTupleSize = Page.PAGE_SIZE - 2000;
      assertThrows(TupleException.class, () -> page.addTuple(new Tuple(new byte[unfitTupleSize])));
    }
  }

  @Nested
  class DeleteTupleTest {
    // Testing strategy
    // + partition on this: fresh page, has data
    // + partition on existence of the tuple: page has the tuple, page doesn't have
    // the tuple

    // cover this is a fresh Page, doesn't have the tuple
    @Test
    void testFreshPage() {
      assertFalse(new SlottedPage(0).deleteTuple(new RID(0, 1)));
    }

    // cover this Page has data, but still doesn't have the tuple
    @Test
    void testPageHasDataButNotTheTuple() throws TupleException {
      SlottedPage page = new SlottedPage(0);
      RID rid1 = page.addTuple(new Tuple(new byte[5]));
      RID rid2 = page.addTuple(new Tuple(new byte[5]));
      RID rid3 = page.addTuple(new Tuple(new byte[5]));
      assertFalse(
          page.deleteTuple(new RID(0, rid1.slotNumber() + rid2.slotNumber() + rid3.slotNumber())));
    }

    // cover this Page has data and has the tuple
    @Test
    void testPageHasTheTuple() throws TupleException {
      SlottedPage page = new SlottedPage(0);
      page.addTuple(new Tuple(new byte[5]));
      page.addTuple(new Tuple(new byte[5]));
      RID theRId = page.addTuple(new Tuple(new byte[100]));
      assertTrue(page.deleteTuple(theRId));

      // now we can no longer find the tuple
      assertThrows(TupleException.class, () -> page.getTuple(theRId));
    }
  }

  @Nested
  class GetSerializedDataTest {
    // Testing strategy
    // + partition on this: fresh page, has tuples data

    // cover this Page has tuple datas
    @Test
    void hasTupleData() throws TupleException {
      Random random = new Random();
      SlottedPage page = new SlottedPage(5);
      final int numTuples = 3;
      List<RID> rids = new ArrayList<>();
      List<Tuple> tuples = new ArrayList<>();
      for (int i = 0; i < numTuples; i++) {
        byte[] randomBytes = new byte[100];
        random.nextBytes(randomBytes);
        Tuple tuple = new Tuple(randomBytes);
        tuples.addLast(tuple);
        rids.addLast(page.addTuple(tuple));
      }
      final int curentAvaliableSpace = page.currentAvailableSpace();

      byte[] data = page.getSerializedData();
      SlottedPage deserializedPage = new SlottedPage(data);
      assertEquals(5, deserializedPage.getPageId());
      assertEquals(curentAvaliableSpace, deserializedPage.currentAvailableSpace());
      for (int i = 0; i < numTuples; i++) {
        assertEquals(tuples.get(i), deserializedPage.getTuple(rids.get(i)));
      }
    }
  }
}
