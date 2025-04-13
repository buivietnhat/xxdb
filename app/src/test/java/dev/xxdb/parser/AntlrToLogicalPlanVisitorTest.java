package dev.xxdb.parser;

import static org.junit.jupiter.api.Assertions.*;

import dev.xxdb.parser.antlr.SqlLexer;
import dev.xxdb.parser.antlr.SqlParser;
import dev.xxdb.parser.ast.LogicalPlan;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.RecognitionException;
import org.antlr.v4.runtime.tree.ParseTree;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class AntlrToLogicalPlanVisitorTest {
  private final AntlrToLogicalPlanVisitor planVisitor = new AntlrToLogicalPlanVisitor();

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
      LogicalPlan plan = planVisitor.visit(parseTree);
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
}
