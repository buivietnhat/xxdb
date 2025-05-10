package dev.xxdb.execution.executor;

import dev.xxdb.catalog.Schema;
import dev.xxdb.execution.ExecutionException;
import dev.xxdb.execution.plan.ProjectionPlan;
import dev.xxdb.storage.tuple.RID;
import dev.xxdb.storage.tuple.Tuple;
import dev.xxdb.types.IntValue;
import dev.xxdb.types.StringValue;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class ProjectionExecutorTest {
  @Nested
  class NextTest {
    // Testing strategy
    //  + partition on column list of projection plan: + covers the whole table, subset of the table

    // cover column list covers whole table
    @Test
    void coverWholeTable() throws ExecutionException {
      ExecutionContext mockCtx = mock(ExecutionContext.class);
      List<String> columns = List.of("col1", "col2", "col3");
      ProjectionPlan plan = new ProjectionPlan(columns);
      Executor child = mock(Executor.class);
      Schema schema = new Schema.Builder().addIntColumn("col1")
          .addVarcharColumn("col2")
          .addIntColumn("col3").build();
      when(child.getOutputSchema()).thenReturn(schema);

      ProjectionExecutor projectionExecutor = new ProjectionExecutor(mockCtx, plan, child);

      projectionExecutor.init();

      Tuple.Builder tupleBuilder = new Tuple.Builder();
      Tuple tuple1 = tupleBuilder.addIntegerColumn(new IntValue(10).getData())
          .addVarcharColumn(new StringValue("goodbye").getData())
          .addIntegerColumn(new IntValue(20).getData())
          .build();
      Tuple tuple2 = tupleBuilder.addIntegerColumn(new IntValue(11).getData())
          .addVarcharColumn(new StringValue("goodbye").getData())
          .addIntegerColumn(new IntValue(21).getData())
          .build();
      Tuple tuple3 = tupleBuilder.addIntegerColumn(new IntValue(12).getData())
          .addVarcharColumn(new StringValue("hehe").getData())
          .addIntegerColumn(new IntValue(22).getData())
          .build();

      when(child.next())
          .thenReturn(Optional.of(new TupleResult(tuple1, RID.INVALID_RID)))
          .thenReturn(Optional.of(new TupleResult(tuple2, RID.INVALID_RID)))
          .thenReturn(Optional.of(new TupleResult(tuple3, RID.INVALID_RID)))
          .thenReturn(Optional.empty());

      List<Tuple> producedTuples = new ArrayList<>();
      Optional<TupleResult> result = projectionExecutor.next();
      while (result.isPresent()) {
        producedTuples.add(result.get().tuple());
        result = projectionExecutor.next();
      }

      assertEquals(List.of(tuple1, tuple2, tuple3), producedTuples);
    }

    // cover column list is a subset of the table
    @Test
    void subsetOfTable() throws ExecutionException {
      ExecutionContext mockCtx = mock(ExecutionContext.class);
      List<String> columns = List.of("col1", "col3");
      ProjectionPlan plan = new ProjectionPlan(columns);
      Executor child = mock(Executor.class);

      Schema schema = new Schema.Builder().addIntColumn("col1")
          .addVarcharColumn("col2")
          .addIntColumn("col3").build();
      when(child.getOutputSchema()).thenReturn(schema);
      
      ProjectionExecutor projectionExecutor = new ProjectionExecutor(mockCtx, plan, child);

      projectionExecutor.init();

      Tuple.Builder tupleBuilder = new Tuple.Builder();
      Tuple tuple1 = tupleBuilder.addIntegerColumn(new IntValue(10).getData())
          .addVarcharColumn(new StringValue("goodbye").getData())
          .addIntegerColumn(new IntValue(20).getData())
          .build();
      Tuple tuple2 = tupleBuilder.addIntegerColumn(new IntValue(11).getData())
          .addVarcharColumn(new StringValue("goodbye").getData())
          .addIntegerColumn(new IntValue(21).getData())
          .build();
      Tuple tuple3 = tupleBuilder.addIntegerColumn(new IntValue(12).getData())
          .addVarcharColumn(new StringValue("hehe").getData())
          .addIntegerColumn(new IntValue(22).getData())
          .build();

      when(child.next())
          .thenReturn(Optional.of(new TupleResult(tuple1, RID.INVALID_RID)))
          .thenReturn(Optional.of(new TupleResult(tuple2, RID.INVALID_RID)))
          .thenReturn(Optional.of(new TupleResult(tuple3, RID.INVALID_RID)))
          .thenReturn(Optional.empty());

      List<Tuple> producedTuples = new ArrayList<>();
      Optional<TupleResult> result = projectionExecutor.next();
      while (result.isPresent()) {
        producedTuples.add(result.get().tuple());
        result = projectionExecutor.next();
      }


      List<Tuple> expectedTuples = List.of(
          tupleBuilder.addIntegerColumn(new IntValue(10).getData()).addIntegerColumn(new IntValue(20).getData()).build(),
          tupleBuilder.addIntegerColumn(new IntValue(11).getData()).addIntegerColumn(new IntValue(21).getData()).build(),
          tupleBuilder.addIntegerColumn(new IntValue(12).getData()).addIntegerColumn(new IntValue(22).getData()).build()
      );

      assertEquals(expectedTuples, producedTuples);
    }
  }

}