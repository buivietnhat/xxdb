package dev.xxdb.execution.executor;

import dev.xxdb.storage.tuple.RID;
import dev.xxdb.storage.tuple.Tuple;

public record TupleResult(Tuple tuple, RID rid) {}
