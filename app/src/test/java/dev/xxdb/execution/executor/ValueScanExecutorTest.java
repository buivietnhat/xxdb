package dev.xxdb.execution.executor;

import dev.xxdb.catalog.Catalog;
import dev.xxdb.catalog.Column;
import dev.xxdb.catalog.Schema;
import dev.xxdb.execution.ExecutionException;
import dev.xxdb.execution.plan.ValueScanPlan;
import dev.xxdb.storage.tuple.RID;
import dev.xxdb.storage.tuple.Tuple;
import dev.xxdb.types.IntValue;
import dev.xxdb.types.StringValue;
import dev.xxdb.types.TypeId;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.nio.ByteBuffer;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class ValueScanExecutorTest {

  @Nested
  class InitTest {
    // Testing strategy
    // + partition on ValueScanPlan
    //  + partition on columns: correct as in the schema, not correct
    //  + partition on values type: correct as in the schema, not correct

    // cover columns are not matched with the schema
    @Test
    void columnsNotCorrect() {
      ValueScanPlan plan = new ValueScanPlan("FOO", List.of("col2", "col1"), List.of(List.of(new StringValue("hello"), new IntValue(2))));
      ExecutionContext mockCtx = mock(ExecutionContext.class);
      Catalog mockCatalog = mock(Catalog.class);
      when(mockCtx.catalog()).thenReturn(mockCatalog);
      Schema mockSchema = mock(Schema.class);
      when(mockCatalog.getTableSchema(eq("FOO"))).thenReturn(Optional.of(mockSchema));

      List<Column> columns = List.of(new Column("col1", TypeId.INTEGER, 0, 0), new Column("col2", TypeId.VARCHAR, 100, 100));
      when(mockSchema.getColumns()).thenReturn(columns);

      ValueScanExecutor valueScanExecutor = new ValueScanExecutor(mockCtx, plan);
      assertThrows(ExecutionException.class, valueScanExecutor::init);
    }

    // cover columns and types are matched with the schema
    @Test
    void columnsAndTypesCorrect() {
      ValueScanPlan plan = new ValueScanPlan("FOO", List.of("col1", "col2"), List.of(List.of(new StringValue("hello"), new IntValue(2))));
      ExecutionContext mockCtx = mock(ExecutionContext.class);
      Catalog mockCatalog = mock(Catalog.class);
      when(mockCtx.catalog()).thenReturn(mockCatalog);
      Schema mockSchema = mock(Schema.class);
      when(mockCatalog.getTableSchema(eq("FOO"))).thenReturn(Optional.of(mockSchema));

      List<Column> columns = List.of(new Column("col1", TypeId.VARCHAR, 0, 0), new Column("col2", TypeId.INTEGER, 100, 100));
      when(mockSchema.getColumns()).thenReturn(columns);

      ValueScanExecutor valueScanExecutor = new ValueScanExecutor(mockCtx, plan);
      assertDoesNotThrow(valueScanExecutor::init);
    }
  }

  // cover types are not matched with the schema
  @Test
  void typesIncorrect() {
    ValueScanPlan plan = new ValueScanPlan("FOO", List.of("col1", "col2"), List.of(List.of(new StringValue("hello"), new IntValue(2))));
    ExecutionContext mockCtx = mock(ExecutionContext.class);
    Catalog mockCatalog = mock(Catalog.class);
    when(mockCtx.catalog()).thenReturn(mockCatalog);
    Schema mockSchema = mock(Schema.class);
    when(mockCatalog.getTableSchema(eq("FOO"))).thenReturn(Optional.of(mockSchema));

    List<Column> columns = List.of(new Column("col1", TypeId.INTEGER, 0, 0), new Column("col2", TypeId.INTEGER, 100, 100));
    when(mockSchema.getColumns()).thenReturn(columns);

    ValueScanExecutor valueScanExecutor = new ValueScanExecutor(mockCtx, plan);
    assertThrows(ExecutionException.class, valueScanExecutor::init);
  }

  @Nested
  class NextTest {
    // Testing strategy
    // + partition on ValueScanPlan: 
    //    + columns are supplied fully as in the table schema, columns are supplied partially (the rest must be NULL)
    //    + values len: 1, >1

    // cover full columns are supplied, single value list
    @Test
    void fullColumnsWithSingleValue() throws ExecutionException {
      StringValue hello = new StringValue("hello");
      IntValue two = new IntValue(2);
      ValueScanPlan plan = new ValueScanPlan("FOO", List.of("col1", "col2"), List.of(List.of(hello, two)));
      ExecutionContext mockCtx = mock(ExecutionContext.class);
      Catalog mockCatalog = mock(Catalog.class);
      when(mockCtx.catalog()).thenReturn(mockCatalog);
      Schema mockSchema = mock(Schema.class);
      when(mockCatalog.getTableSchema(eq("FOO"))).thenReturn(Optional.of(mockSchema));

      List<Column> columns = List.of(new Column("col1", TypeId.VARCHAR, 0, 0), new Column("col2", TypeId.INTEGER, 100, 100));
      when(mockSchema.getColumns()).thenReturn(columns);

      ValueScanExecutor valueScanExecutor = new ValueScanExecutor(mockCtx, plan);
      valueScanExecutor.init();

      // first next, should get the value
      Optional<TupleResult> maybeTupleResult = valueScanExecutor.next();
      assertTrue(maybeTupleResult.isPresent());

      int tupleLen = hello.size() + two.size();
      byte[] bytes = new byte[tupleLen];
      ByteBuffer buffer = ByteBuffer.wrap(bytes);
      buffer.put(hello.getData());
      buffer.put(two.getData());
      Tuple expectedTuple = new Tuple(buffer.array());

      Tuple producedTuple = maybeTupleResult.get().tuple();
      assertEquals(expectedTuple, producedTuple);

      assertEquals(RID.INVALID_RID, maybeTupleResult.get().rid());

      // second next, no more tuple should be produced
      assertTrue(valueScanExecutor.next().isEmpty());
    }

    // cover full columns are supplied, multiple value lists
    @Test
    void fullColumnsWithMultipleValues() throws ExecutionException {
      StringValue john = new StringValue("John");
      IntValue salary1 = new IntValue(50000);
      StringValue jane = new StringValue("Jane");
      IntValue salary2 = new IntValue(60000);
      
      ValueScanPlan plan = new ValueScanPlan("EMPLOYEES", List.of("name", "salary"), 
          List.of(
              List.of(john, salary1),
              List.of(jane, salary2)
          ));
      
      ExecutionContext mockCtx = mock(ExecutionContext.class);
      Catalog mockCatalog = mock(Catalog.class);
      when(mockCtx.catalog()).thenReturn(mockCatalog);
      Schema mockSchema = mock(Schema.class);
      when(mockCatalog.getTableSchema(eq("EMPLOYEES"))).thenReturn(Optional.of(mockSchema));

      List<Column> columns = List.of(new Column("name", TypeId.VARCHAR, 0, 0), new Column("salary", TypeId.INTEGER, 100, 100));
      when(mockSchema.getColumns()).thenReturn(columns);

      ValueScanExecutor valueScanExecutor = new ValueScanExecutor(mockCtx, plan);
      valueScanExecutor.init();

      // first tuple
      Optional<TupleResult> maybeTupleResult = valueScanExecutor.next();
      assertTrue(maybeTupleResult.isPresent());

      int tupleLen = john.size() + salary1.size();
      byte[] bytes = new byte[tupleLen];
      ByteBuffer buffer = ByteBuffer.wrap(bytes);
      buffer.put(john.getData());
      buffer.put(salary1.getData());
      Tuple expectedTuple1 = new Tuple(buffer.array());

      Tuple producedTuple = maybeTupleResult.get().tuple();
      assertEquals(expectedTuple1, producedTuple);
      assertEquals(RID.INVALID_RID, maybeTupleResult.get().rid());

      // second tuple
      maybeTupleResult = valueScanExecutor.next();
      assertTrue(maybeTupleResult.isPresent());

      bytes = new byte[tupleLen];
      buffer = ByteBuffer.wrap(bytes);
      buffer.put(jane.getData());
      buffer.put(salary2.getData());
      Tuple expectedTuple2 = new Tuple(buffer.array());

      producedTuple = maybeTupleResult.get().tuple();
      assertEquals(expectedTuple2, producedTuple);
      assertEquals(RID.INVALID_RID, maybeTupleResult.get().rid());

      // third next, no more tuples should be produced
      assertTrue(valueScanExecutor.next().isEmpty());
    }
  }
}