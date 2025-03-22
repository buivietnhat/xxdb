package dev.xxdb.storage.page;

import dev.xxdb.storage.tuple.RID;
import dev.xxdb.storage.tuple.Tuple;
import dev.xxdb.storage.tuple.TupleException;

public class SlottedPage extends Page {

  public Tuple getTuple(final RID rid) throws TupleException {
    throw new RuntimeException("unimlemented");
  }

  public void addTuple(final RID rid, final Tuple tuple) throws TupleException {
    throw new RuntimeException("unimlemented");
  }

  public void deleteTuple(final RID rid) throws TupleException {
    throw new RuntimeException("unimlemented");
  }
}
