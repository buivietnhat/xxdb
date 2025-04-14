package dev.xxdb.parser.ast.plan;

import dev.xxdb.parser.ast.expression.ColumnDefinition;
import dev.xxdb.parser.ast.expression.ColumnDefinitionList;
import dev.xxdb.parser.ast.expression.CreateTable;
import dev.xxdb.parser.ast.expression.Expression;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ExpresionToPlanVisitorTest {

//  @BeforeEach
//  void setUp() {
//  }
//
//  @AfterEach
//  void tearDown() {
//  }
  private ExpresionToPlanVisitor visitor = new ExpresionToPlanVisitor();

  @Nested
  class VisitCreateTableNodeTest {
    // Testing strategy
    // + parition on the expression: valid CreateTableExpression

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
}