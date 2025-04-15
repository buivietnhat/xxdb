package dev.xxdb.parser.ast.plan;

import dev.xxdb.parser.ast.statement.*;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class StatementToPlanVisitorTest {

//  @BeforeEach
//  void setUp() {
//  }
//
//  @AfterEach
//  void tearDown() {
//  }
  private StatementToPlanVisitor visitor = new StatementToPlanVisitor();

  @Nested
  class VisitCreateTableNodeTest {
    // Testing strategy
    // + partition on the expression: valid CreateTableExpression

    // cover the expression is a valid CreateTableExpression
    @Test
    void validCreateTableExpression() {
      String tableName = "FOO";
      List<Statement> columnLists = List.of(new ColumnDefinition("col1", "INT"), new ColumnDefinition("col2", "VARCHAR"));
      Statement columnDefList = new ColumnDefinitionList(columnLists);
      Statement createTable = new CreateTable(tableName, columnDefList);
      createTable.accept(visitor);
      LogicalPlan plan = visitor.getPlan();
      assertEquals("CreateTable FOO ( col1 INT,col2 VARCHAR )", plan.toString());
    }
  }

  @Nested
  class VisitInsertNodeTest {
    // Testing strategy
    // + partition on the expression: valid Insert expression

    // cover the expression is a valid Insert
    @Test
    void validInsertExpression() {
      String tableName = "FOO";
      Statement columns = new ColumnList(List.of("col1" , "col2", "col3", "col4"));
      Statement values = new ValueList(List.of(new IntValue(100), new StringValue("'hello'"), new IntValue(200), new StringValue("'world'")));
      Insert insert = new Insert(tableName, columns, values);
      insert.accept(visitor);
      LogicalPlan plan = visitor.getPlan();
      assertEquals("InsertPlan{tableName='FOO', columns=[col1, col2, col3, col4], values=[IntValue[value=100], " +
          "StringValue[value=hello], IntValue[value=200], StringValue[value=world]]}", plan.toString());
    }
  }
}