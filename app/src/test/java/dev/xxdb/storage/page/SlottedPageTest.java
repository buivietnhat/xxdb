package dev.xxdb.storage.page;

import static org.junit.jupiter.api.Assertions.*;

import dev.xxdb.storage.tuple.RID;
import dev.xxdb.storage.tuple.Tuple;
import dev.xxdb.storage.tuple.exception.TupleException;
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
      assertThrows(
          TupleException.class,
          () -> page.getTuple(new RID(1, 1)));
    }

    // cover this Page has data, but doesn't contain the one to find (the record indicates it is in this page)
    @Test
    void testHasDataNotFoundTheOneSamePage() throws TupleException {
      SlottedPage page = new SlottedPage(0);
      RID rid1 = page.addTuple(new Tuple(new char[] { 'a', 'b', 'c' }));
      RID rid2 = page.addTuple(new Tuple(new char[] { 'a', 'c', 'a' }));
      RID rid3 = page.addTuple( new Tuple(new char[] { 'c', 'b', 'a' }));

      // same page, but non existed slot number
      RID findingRid = new RID(0,rid1.slotNumber() + rid2.slotNumber() + rid3.slotNumber());
      assertThrows(
          TupleException.class,
          () -> page.getTuple(findingRid));
    }

    // cover this Page has data, but doesn't contain the one to find (the record indicates it is in different page)
    @Test
    void testHasDataNotFoundTheOneDifferentPage() throws TupleException {
      SlottedPage page = new SlottedPage(0);
      page.addTuple(new Tuple(new char[] { 'a', 'b', 'c' }));
      page.addTuple(new Tuple(new char[] { 'a', 'c', 'a' }));

      // different pageID
      RID findingRid = new RID(1, 0);
      assertThrows(
              TupleException.class,
              () -> {
                page.getTuple(findingRid);
              });
    }

    // cover this has data and contains the record
    @Test
    void testHasDataHasTheRecord() throws TupleException {
      SlottedPage page = new SlottedPage(0);
      String data = "hello";
      Tuple theTuple = new Tuple(data.toCharArray());

      RID theRid = page.addTuple(theTuple);
      page.addTuple(new Tuple(new char[] { 'd', 'u' }));

      Tuple theOneFound = page.getTuple(theRid);
      assertEquals(theTuple, theOneFound);
    }
  }
}
