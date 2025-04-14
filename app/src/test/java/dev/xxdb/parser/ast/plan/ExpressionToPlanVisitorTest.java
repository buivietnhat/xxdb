package dev.xxdb.parser.ast.plan;

import dev.xxdb.parser.ast.expression.*;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ExpressionToPlanVisitorTest {

//  @BeforeEach
//  void setUp() {
//  }
//
//  @AfterEach
//  void tearDown() {
//  }
  private ExpressionToPlanVisitor visitor = new ExpressionToPlanVisitor();

  @Nested
  class VisitCreateTableNodeTest {
    // Testing strategy
    // + partition on the expression: valid CreateTableExpression

    // cover the expression is a valid CreateTableExpression
    @Test
    void validCreateTableExpression() {
      String tableName = "FOO";
      List<Expression> columnLists = List.of(new ColumnDefinition("col1", "INT"), new ColumnDefinition("col2", "VARCHAR"));
      Expression columnDefList = new ColumnDefinitionList(columnLists);
      Expression createTable = new CreateTable(tableName, columnDefList);
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
      Expression columns = new ColumnList(List.of("col1" , "col2", "col3", "col4"));
      Expression values = new ValueList(List.of(new IntValue(100), new StringValue("'hello'"), new IntValue(200), new StringValue("'world'")));
      Insert insert = new Insert(tableName, columns, values);
      insert.accept(visitor);
      LogicalPlan plan = visitor.getPlan();
      assertEquals("InsertPlan{tableName='FOO', columns=[col1, col2, col3, col4], values=[IntValue[value=100], " +
          "StringValue[value=hello], IntValue[value=200], StringValue[value=world]]}", plan.toString());
    }
  }
}