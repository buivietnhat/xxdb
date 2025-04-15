package dev.xxdb.parser.ast.statement;

import static org.junit.jupiter.api.Assertions.*;

import dev.xxdb.parser.antlr.SqlLexer;
import dev.xxdb.parser.antlr.SqlParser;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class AntlrToStatementVisitorTest {
  private final AntlrToStatementVisitor planVisitor = new AntlrToStatementVisitor();

  private ParseTree parseSql(final String query) {
    SqlLexer lexer = new SqlLexer(CharStreams.fromString(query));
    return new SqlParser(new CommonTokenStream(lexer)).sql();
  }

  @Nested
  class VisitCreateTableStatementTest {
    // Testing strategy
    // + partition on the sql query: valid, invalid syntax

    // cover sql query has valid syntax
    @Test
    void validSqlQuery() {
      String query = "CREATE TABLE foo (intColumn INT, stringColumn VARCHAR);";
      ParseTree parseTree = parseSql(query);
      Statement plan = planVisitor.visit(parseTree);
      assertEquals("CreateTableNode tableName:foo (intColumn:INT) (stringColumn:VARCHAR) ", plan.toString());
    }

//    // cover sql is invalid (missing the ;)
//    @Test
//    void missingSemicon() {
//      String query = "CREATE TABLE foo (intColumn INT, stringColumn VARCHAR)";
//      assertThrows(RecognitionException.class, () -> {
//        ParseTree parseTree = parseSql(query);
//        LogicalPlan plan = planVisitor.visit(parseTree);
//      });
//    }
  }

  @Nested
  class InsertStatementTest {
    @Test
    void validSqlQuery() {
      String query = "INSERT INTO table_name (column1, column2, column3) VALUES (1, 'hello', 3);";
      ParseTree parseTree = parseSql(query);
      Statement plan = planVisitor.visit(parseTree);
      assertEquals("Insert (column1 column2 column3) (Int: 1 String: hello Int: 3)", plan.toString());
    }
  }


  @Nested
  class SelectStatementTest {
    @Test
    void validSqlQuery() {
      String query = "SELECT Orders.OrderID, Customers.CustomerName, Orders.OrderDate\n" +
          "FROM Orders\n" +
          "JOIN Customers ON Orders.CustomerID=Customers.CustomerID\n" +
          "WHERE Orders.CustomerID = 1000 \n" +
          "LIMIT 10;";
      ParseTree parseTree = parseSql(query);
      Statement plan = planVisitor.visit(parseTree);
      assertEquals("Select{tableName='Orders', columnList=(Orders.OrderID Customers.CustomerName Orders.OrderDate), " +
          "joinClause=Join{tableName='Customers', condition=SimpleColumnCondition{columnName1='Orders.CustomerID', columnName2='Customers.CustomerID', operator=Operator{op=EQUALS}}}, " +
          "whereClause=Where{condition=SimpleCondition{columnName='Orders.CustomerID', operator=Operator{op=EQUALS}, value=Int: 1000}}, " +
          "limitClause=Limit{number=10}}", plan.toString());
    }
  }
}
