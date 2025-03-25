package dev.xxdb.storage.page;

import dev.xxdb.storage.tuple.RID;
import dev.xxdb.storage.tuple.Tuple;
import dev.xxdb.storage.tuple.exception.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SlottedPage extends Page {
  class TupleInfo {
    public int offset; // 4 bytes
    // size of the tuple, in bytes
    public int size; // 4 bytes
    boolean isDeleted; // 1 byte

    public TupleInfo(int offset, int size, boolean isDeleted) {
      this.offset = offset;
      this.size = size;
      this.isDeleted = isDeleted;
    }
  }

  private static final short TUPLE_INFO_SIZE = 9; // bytes

  // How many slots are currently in used
  private int numSlotsUsed = 0;
  private final List<TupleInfo> slots = new ArrayList<>();

  // Abstraction function: AF(numSlotUsed, slots, data) = A slotted page structure
  // to store tuple data
  //
  // Rep invariant:
  // + slots.length() == numSlotUsed
  // + slots[numSlotsUse-1].offset <= TUPLE_HEADER_SIZE + numSlotsUsed * TUPLE_INFO_SIZE
  //
  // Safety from rep exposure: all fields are private and not exposed to outside
  // world

  // Check that the rep invariant is true
  private void checkRep() {
    assert slots.size() == numSlotsUsed && currentAvailableSpace() >= 0;
  }

  public SlottedPage(final int pageId) {
    super(pageId);
  }

  public SlottedPage(final int pageId, byte[] data) {
    super(pageId, data);
  }

  // Make sure we have the tuple
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
    byte[] tupleData =
        Arrays.copyOfRange(data, tupleInfo.offset, tupleInfo.offset + tupleInfo.size);

    return new Tuple(tupleData);
  }

  /**
   * Update a tuple in this Page
   *
   * @param rid:   record ID of the tuple, requires to be valid
   * @param tuple: tuple data to add
   * @throws TupleException if cannot update
   */
  public void updateTuple(final RID rid, final Tuple tuple) throws TupleException {
    throw new RuntimeException("unimplemented");
  }

  // Return the offset of the starting location for a newly created tuple
  private int nextAvailableStartingOffset() {
    if (numSlotsUsed == 0) {
      return PAGE_SIZE;
    }

    return slots.get(numSlotsUsed - 1).offset;
  }

  // How many bytes current available?
  private int currentAvailableSpace() {
    return nextAvailableStartingOffset()
        - TUPLE_HEADER_SIZE
        - slots.size() * TUPLE_INFO_SIZE;

  }

  // Check if we have enough room for storing the tuple
  boolean hasRoomFor(final Tuple tuple) {
    int tupleSize = tuple.getSize();
    if (tupleSize >= PAGE_SIZE) {
      return false;
    }

    int storageRequired = tupleSize + TUPLE_INFO_SIZE;
    return currentAvailableSpace() >= storageRequired;
  }

  /**
   * Add a new tuple to this Page
   *
   * @param tuple: tuple data to add
   * @return RID of the tuple
   * @throws TupleException if cannot add
   */
  public RID addTuple(final Tuple tuple) throws TupleException {
    if (!hasRoomFor(tuple)) {
      throw new TupleException("Cannot add new tuple due to out of space");
    }

    int tupleOffset = nextAvailableStartingOffset() - tuple.getSize();
    TupleInfo info = new TupleInfo(tupleOffset, tuple.getSize(), false);

    slots.addLast(info);
    numSlotsUsed += 1;

    byte[] tupleData = tuple.getData();
    System.arraycopy(tupleData, 0, data, tupleOffset, tupleData.length);

    checkRep();

    return new RID(pageId, numSlotsUsed - 1);
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
