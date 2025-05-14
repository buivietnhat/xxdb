package dev.xxdb.execution.executor;

import dev.xxdb.execution.ExecutionException;
import dev.xxdb.execution.plan.LimitPlan;
import dev.xxdb.storage.tuple.RID;
import dev.xxdb.storage.tuple.Tuple;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class LimitExecutorTest {
  @Nested
  class NextTest {
    // Testing strategy
    //  + partition on limit number and number of tuples produced by the child
    //    + limit number < number of tuples
    //    + limit number = number of tuples
    //    + limit number > number of tuples

    @Test
    void limitLessThanTuplesProduced() throws ExecutionException {
      ExecutionContext mockCtx = mock(ExecutionContext.class);
      int numLimit = 3;
      LimitPlan limitPlan = new LimitPlan(numLimit);
      Executor mockChild = mock(Executor.class);
      LimitExecutor limitExecutor = new LimitExecutor(mockCtx, limitPlan, mockChild);

      limitExecutor.init();

      when(mockChild.next())
          .thenReturn(Optional.of(new TupleResult(new Tuple(new byte[100]), RID.INVALID_RID)))
          .thenReturn(Optional.of(new TupleResult(new Tuple(new byte[200]), RID.INVALID_RID)))
          .thenReturn(Optional.of(new TupleResult(new Tuple(new byte[300]), RID.INVALID_RID)))
          .thenReturn(Optional.of(new TupleResult(new Tuple(new byte[400]), RID.INVALID_RID)))
          .thenReturn(Optional.empty());


      Optional<TupleResult> result = limitExecutor.next();
      List<Tuple> producedTuples = new ArrayList<>();
      while (result.isPresent()) {
        producedTuples.add(result.get().tuple());
        result = limitExecutor.next();
      }

      assertEquals(numLimit, producedTuples.size());
    }
  }

}