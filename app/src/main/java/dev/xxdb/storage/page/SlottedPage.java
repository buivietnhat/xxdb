package dev.xxdb.storage.page;

import dev.xxdb.storage.tuple.RID;
import dev.xxdb.storage.tuple.Tuple;
import dev.xxdb.storage.tuple.exception.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SlottedPage extends Page {
  class TupleInfo {
    public short offset;
    public char size;
  }
  // Mark that this slot (or Record ID) has been deleted
  private static final short DELETED_SLOT = -1;

  // How many slots are currently in used
  private int numSlotsUsed = 0;
  private final List<TupleInfo> slots = new ArrayList<>();

  // Abstraction function: AF(numSlotUsed, slots, data) = A slotted page structure to store tuple datas
  //
  // Rep invariant:
  // + slots.length() == numSlotUsed
  //
  // Safety from rep exposure: all fields are private and not exposed to outside world


  // Check that the rep invariant is true
  private void checkRep() {
    assert slots.size() == numSlotsUsed;
  }

  public SlottedPage(final int pageId) {
    super(pageId);
  }

  public SlottedPage(final int pageId, char[] data) {
    super(pageId, data);
  }

  private void guaranteeTupleExist(final RID rid) throws TupleException {
    if (rid.pageId() != getPageId()) {
      throw new TupleException("The rid is in a different page");
    }

    if (rid.slotNumber() >= numSlotsUsed) {
      throw new TupleException("This rid is out range of slots");
    }
  }

  /**
   * Find a tuple in this Page
   *
   * @param rid: record ID of the tuple, requires to be valid
   * @return Tuple data
   * @throws TupleException if the record not found
   */
  public Tuple getTuple(final RID rid) throws TupleException {
    guaranteeTupleExist(rid);
    TupleInfo tupleInfo = slots.get(rid.slotNumber());
    char[] tupleData = Arrays.copyOfRange(data, tupleInfo.offset, tupleInfo.offset + tupleInfo.size);
    return new Tuple(tupleData);
  }

  /**
   * Update a tuple in this Page
   *
   * @param rid: record ID of the tuple, requires to be valid
   * @param tuple: tuple data to add
   * @throws TupleException if cannot update
   */
  public void updateTuple(final RID rid, final Tuple tuple) throws TupleException {
    throw new RuntimeException("unimplemented");
  }

  /**
   * Add a new tuple to this Page
   *
   * @param tuple: tuple data to add
   * @return RID of the tuple
   * @throws TupleException if cannot add
   */
  public RID addTuple(final Tuple tuple) throws TupleException {
    throw new RuntimeException("unimlemented");
  }

  /**
   * Delete a tuple in this Page
   *
   * @param rid: record ID of the tuple, requires to be valid
   * @throws TupleException if the tuple is there but cannot delete
   */
  public void deleteTuple(final RID rid) throws TupleException {
    throw new RuntimeException("unimlemented");
  }
}
