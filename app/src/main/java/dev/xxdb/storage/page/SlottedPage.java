package dev.xxdb.storage.page;

import dev.xxdb.execution.executor.TupleResult;
import dev.xxdb.storage.tuple.RID;
import dev.xxdb.storage.tuple.Tuple;
import dev.xxdb.storage.tuple.exception.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
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
  // + slots[numSlotsUse-1].offset <= TUPLE_HEADER_SIZE + numSlotsUsed *
  // TUPLE_INFO_SIZE
  //
  // Safety from rep exposure: all fields are private and not exposed to outside
  // world


  // Implement Iterator<TupleResult> interface for traversing all the tuples in this Page
  public Iterator<TupleResult> getIterator() {
    return new TupleIterator();
  }

  private class TupleIterator implements Iterator<TupleResult> {
    private int currentSlot = 0;

    @Override
    public boolean hasNext() {
      while (currentSlot < numSlotsUsed) {
        TupleInfo tupleInfo = slots.get(currentSlot);
        if (!tupleInfo.isDeleted) {
          return true;
        }
        currentSlot += 1;
      }

      return false;
    }

    @Override
    public TupleResult next() {
      if (hasNext()) {
        try {
          Tuple tuple = getTuple(currentSlot);
          TupleResult tupleResult = new TupleResult(tuple, new RID(getPageId(), currentSlot));
          currentSlot += 1;
          return tupleResult;
        } catch (TupleException e) {
          throw new RuntimeException(e);
        }
      }

      return null;
    }
  }

  // Check that the rep invariant is true
  private void checkRep() {
    assert slots.size() == numSlotsUsed && currentAvailableSpace() >= 0;
  }

  public SlottedPage(final int pageId) {
    super(pageId);
  }

  public SlottedPage(byte[] data) {
    super(data);
    deserializedHeader();
    checkRep();
  }

  // Do we have the tuple in this Page?
  private boolean tupleNotExists(final RID rid) {
    if (rid.pageId() != getPageId()) {
      return true;
    }
    if (rid.slotNumber() >= numSlotsUsed) {
      return true;
    }
    return false;
  }

  // Make sure we have the tuple
  private void guaranteeTupleExists(final RID rid) throws TupleException {
    if (tupleNotExists(rid)) {
      throw new TupleException("This page doesn't have the tuple");
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
    guaranteeTupleExists(rid);
    return getTuple(rid.slotNumber());
  }


  private Tuple getTuple(int slotNumber) throws TupleException {
    TupleInfo tupleInfo = slots.get(slotNumber);
    if (tupleInfo.isDeleted) {
      throw new TupleException("The tuple was deleted");
    }

    byte[] tupleData = Arrays.copyOfRange(buffer.array(), tupleInfo.offset, tupleInfo.offset + tupleInfo.size);

    checkRep();

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

    // TODO: this will not be correct if we decide to reuse space of deleted tuples
    return slots.get(numSlotsUsed - 1).offset;
  }

  // How many bytes current available?
  public int currentAvailableSpace() {
    return nextAvailableStartingOffset() - PAGE_HEADER_SIZE - slots.size() * TUPLE_INFO_SIZE;
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
    System.arraycopy(tupleData, 0, buffer.array(), tupleOffset, tupleData.length);

    checkRep();

    return new RID(getPageId(), numSlotsUsed - 1);
  }

  /**
   * Delete a tuple in this Page
   *
   * @param rid: record ID of the tuple, requires to be valid
   * @return true if the tuple exists and delete successfully, false otherwise
   */
  public boolean deleteTuple(final RID rid) {
    if (tupleNotExists(rid)) {
      return false;
    }

    TupleInfo tupleInfo = slots.get(rid.slotNumber());
    tupleInfo.isDeleted = true;
    // TODO: shift the elements to save space
    checkRep();
    return true;
  }

  private void serializedHeader() {
    buffer.position(PAGE_HEADER_SIZE); // skip the page header
    buffer.putInt(numSlotsUsed);
    for (TupleInfo info : slots) {
      buffer.putInt(info.offset);
      buffer.putInt(info.size);
      buffer.put((byte) (info.isDeleted ? 1 : 0));
    }
  }

  private void deserializedHeader() {
    buffer.position(PAGE_HEADER_SIZE); // Skip to where slot info starts

    numSlotsUsed = buffer.getInt();
    slots.clear();

    for (int i = 0; i < numSlotsUsed; i++) {
      int offset = buffer.getInt();
      int size = buffer.getInt();
      boolean isDeleted = buffer.get() != 0;
      slots.add(new TupleInfo(offset, size, isDeleted));
    }
  }

  @Override
  public byte[] getSerializedData() {
    serializedHeader();
    return super.getSerializedData();
  }
}
