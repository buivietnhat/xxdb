package dev.xxdb.storage.tuple;

public record RID(int pageId, int slotNumber) {
  public static final RID INVALID_RID = new RID(-1, -1);
}
