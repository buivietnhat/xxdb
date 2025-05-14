package dev.xxdb.execution.executor;

import dev.xxdb.catalog.Catalog;
import dev.xxdb.catalog.Schema;
import dev.xxdb.execution.ExecutionException;
import dev.xxdb.execution.plan.HashJoinPlan;
import dev.xxdb.execution.plan.PhysicalPlan;
import dev.xxdb.optimizer.Optimizer;
import dev.xxdb.parser.antlr.SqlLexer;
import dev.xxdb.parser.antlr.SqlParser;
import dev.xxdb.parser.ast.plan.LogicalPlan;
import dev.xxdb.parser.ast.plan.StatementToPlanVisitor;
import dev.xxdb.parser.ast.statement.AntlrToStatementVisitor;
import dev.xxdb.parser.ast.statement.Statement;
import dev.xxdb.storage.tuple.RID;
import dev.xxdb.storage.tuple.Tuple;
import dev.xxdb.types.IntValue;
import dev.xxdb.types.StringValue;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class HashJoinExecutorTest {
  @Nested
  class NextTest {
    // Testing strategy:
    // + partition on join keys: unique, not unique
    // + partition on output schema: subset of union of two children, union of two children

    // cover join keys are unique, output schema covers whole two children
    @Test
    void joinKeysAreUnique() throws ExecutionException {
      ExecutionContext mockCtx = mock(ExecutionContext.class);

      HashJoinPlan plan = new HashJoinPlan("FOO.id", "BAR.id");
      Schema outputSchema = new Schema.Builder()
          .addIntColumn("FOO.id")
          .addVarcharColumn("FOO.col1")
          .addIntColumn("FOO.col2")
          .addIntColumn("BAR.id")
          .addVarcharColumn("BAR.col1")
          .build();
      plan.setOutputSchema(outputSchema);

      Executor leftChild = mock(Executor.class);
      Schema leftSchema = new Schema.Builder()
          .addIntColumn("FOO.id")
          .addVarcharColumn("FOO.col1")
          .addIntColumn("FOO.col2")
          .build();
      when(leftChild.getOutputSchema()).thenReturn(leftSchema);

      Executor rightChild = mock(Executor.class);
      Schema rightSchema = new Schema.Builder()
          .addIntColumn("BAR.id")
          .addVarcharColumn("BAR.col1")
          .build();
      when(rightChild.getOutputSchema()).thenReturn(rightSchema);

      HashJoinExecutor executor = new HashJoinExecutor(mockCtx, plan, leftChild, rightChild);

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

      Tuple tuple4 = tupleBuilder.addIntegerColumn(new IntValue(10).getData())
          .addVarcharColumn(new StringValue("hehehehe").getData())
          .build();
      Tuple tuple5 = tupleBuilder.addIntegerColumn(new IntValue(11).getData())
          .addVarcharColumn(new StringValue("hihihi").getData())
          .build();

      when(leftChild.next())
          .thenReturn(Optional.of(new TupleResult(tuple1, RID.INVALID_RID)))
          .thenReturn(Optional.of(new TupleResult(tuple2, RID.INVALID_RID)))
          .thenReturn(Optional.of(new TupleResult(tuple3, RID.INVALID_RID)))
          .thenReturn(Optional.empty());

      when(rightChild.next())
          .thenReturn(Optional.of(new TupleResult(tuple4, RID.INVALID_RID)))
          .thenReturn(Optional.of(new TupleResult(tuple5, RID.INVALID_RID)))
          .thenReturn(Optional.empty());

      executor.init();

      List<Tuple> expectedTuples = List.of(
          tupleBuilder.addIntegerColumn(new IntValue(10).getData())
              .addVarcharColumn(new StringValue("goodbye").getData())
              .addIntegerColumn(new IntValue(20).getData())
              .addIntegerColumn(new IntValue(10).getData())
              .addVarcharColumn(new StringValue("hehehehe").getData())
              .build(),
          tupleBuilder.addIntegerColumn(new IntValue(11).getData())
              .addVarcharColumn(new StringValue("goodbye").getData())
              .addIntegerColumn(new IntValue(21).getData())
              .addIntegerColumn(new IntValue(11).getData())
              .addVarcharColumn(new StringValue("hihihi").getData())
              .build()
      );

      List<Tuple> producedTuples = new ArrayList<>();
      Optional<TupleResult> maybeResult = executor.next();
      while (maybeResult.isPresent()) {
        producedTuples.add(maybeResult.get().tuple());
        maybeResult = executor.next();
      }

      assertEquals(expectedTuples.size(), producedTuples.size());
      expectedTuples.forEach(
          et -> assertTrue(producedTuples.contains(et))
      );

    }

    // cover join keys are not unique, output schema is a subset of union of two children
    @Test
    void joinKeysNotUnique() throws ExecutionException {
      ExecutionContext mockCtx = mock(ExecutionContext.class);

      HashJoinPlan plan = new HashJoinPlan("FOO.id", "BAR.id");
      Schema outputSchema = new Schema.Builder()
          .addVarcharColumn("FOO.col1")
          .addIntColumn("FOO.col2")
          .addVarcharColumn("BAR.col1")
          .build();
      plan.setOutputSchema(outputSchema);

      Executor leftChild = mock(Executor.class);
      Schema leftSchema = new Schema.Builder()
          .addIntColumn("FOO.id")
          .addVarcharColumn("FOO.col1")
          .addIntColumn("FOO.col2")
          .build();
      when(leftChild.getOutputSchema()).thenReturn(leftSchema);

      Executor rightChild = mock(Executor.class);
      Schema rightSchema = new Schema.Builder()
          .addIntColumn("BAR.id")
          .addVarcharColumn("BAR.col1")
          .build();
      when(rightChild.getOutputSchema()).thenReturn(rightSchema);

      HashJoinExecutor executor = new HashJoinExecutor(mockCtx, plan, leftChild, rightChild);

      Tuple.Builder tupleBuilder = new Tuple.Builder();
      Tuple tuple1 = tupleBuilder.addIntegerColumn(new IntValue(10).getData())
          .addVarcharColumn(new StringValue("goodbye").getData())
          .addIntegerColumn(new IntValue(20).getData())
          .build();
      Tuple tuple2 = tupleBuilder.addIntegerColumn(new IntValue(10).getData())
          .addVarcharColumn(new StringValue("goodbye").getData())
          .addIntegerColumn(new IntValue(21).getData())
          .build();
      Tuple tuple3 = tupleBuilder.addIntegerColumn(new IntValue(11).getData())
          .addVarcharColumn(new StringValue("hehe").getData())
          .addIntegerColumn(new IntValue(22).getData())
          .build();

      Tuple tuple4 = tupleBuilder.addIntegerColumn(new IntValue(10).getData())
          .addVarcharColumn(new StringValue("hehehehe").getData())
          .build();
      Tuple tuple5 = tupleBuilder.addIntegerColumn(new IntValue(10).getData())
          .addVarcharColumn(new StringValue("hihihi").getData())
          .build();

      when(leftChild.next())
          .thenReturn(Optional.of(new TupleResult(tuple1, RID.INVALID_RID)))
          .thenReturn(Optional.of(new TupleResult(tuple2, RID.INVALID_RID)))
          .thenReturn(Optional.of(new TupleResult(tuple3, RID.INVALID_RID)))
          .thenReturn(Optional.empty());

      when(rightChild.next())
          .thenReturn(Optional.of(new TupleResult(tuple4, RID.INVALID_RID)))
          .thenReturn(Optional.of(new TupleResult(tuple5, RID.INVALID_RID)))
          .thenReturn(Optional.empty());

      executor.init();

      List<Tuple> expectedTuples = List.of(
          tupleBuilder
              .addVarcharColumn(new StringValue("goodbye").getData())
              .addIntegerColumn(new IntValue(20).getData())
              .addVarcharColumn(new StringValue("hehehehe").getData())
              .build(),
          tupleBuilder
              .addVarcharColumn(new StringValue("goodbye").getData())
              .addIntegerColumn(new IntValue(20).getData())
              .addVarcharColumn(new StringValue("hihihi").getData())
              .build(),
          tupleBuilder
              .addVarcharColumn(new StringValue("goodbye").getData())
              .addIntegerColumn(new IntValue(21).getData())
              .addVarcharColumn(new StringValue("hehehehe").getData())
              .build(),
          tupleBuilder
              .addVarcharColumn(new StringValue("goodbye").getData())
              .addIntegerColumn(new IntValue(21).getData())
              .addVarcharColumn(new StringValue("hihihi").getData())
              .build()
      );

      List<Tuple> producedTuples = new ArrayList<>();
      Optional<TupleResult> maybeResult = executor.next();
      while (maybeResult.isPresent()) {
        producedTuples.add(maybeResult.get().tuple());
        maybeResult = executor.next();
      }

      assertEquals(expectedTuples.size(), producedTuples.size());
      expectedTuples.forEach(
          et -> assertTrue(producedTuples.contains(et))
      );

    }
  }


}