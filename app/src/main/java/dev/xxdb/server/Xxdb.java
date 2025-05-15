package dev.xxdb.server;

import dev.xxdb.catalog.Catalog;
import dev.xxdb.catalog.Schema;
import dev.xxdb.execution.ExecutionEngine;
import dev.xxdb.execution.ExecutionException;
import dev.xxdb.execution.executor.ExecutionContext;
import dev.xxdb.execution.executor.TupleResult;
import dev.xxdb.execution.plan.PhysicalPlan;
import dev.xxdb.optimizer.Optimizer;
import dev.xxdb.parser.antlr.SqlLexer;
import dev.xxdb.parser.antlr.SqlParser;
import dev.xxdb.parser.ast.plan.LogicalPlan;
import dev.xxdb.parser.ast.plan.StatementToPlanVisitor;
import dev.xxdb.parser.ast.statement.AntlrToStatementVisitor;
import dev.xxdb.parser.ast.statement.Statement;
import dev.xxdb.storage.disk.DiskManager;
import dev.xxdb.storage.file.HeapFile;
import dev.xxdb.storage.page.SlottedPageRepository;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;

import java.io.IOException;
import java.util.List;

public class Xxdb {
  private DiskManager diskManager;
  private String filePath = "dump.db";
  private Optimizer optimizer;
  private ExecutionEngine executionEngine;

  public static class Builder {
    private final Xxdb xxdb = new Xxdb();
    public Xxdb buildAll() throws XxdbException {
      String filePath = "xxdb.db";
      DiskManager diskManager;
      try {
        diskManager = new DiskManager(filePath);
      } catch (IOException e) {
        throw new RuntimeException(e);
      }

      SlottedPageRepository slottedPageRepository = new HeapFile(diskManager);
      Catalog catalog = new Catalog(slottedPageRepository);
      ExecutionContext executionContext = new ExecutionContext(catalog);
      Optimizer optimizer = new Optimizer(catalog);
      return filePath(filePath)
          .diskManager(diskManager)
          .executionEngine(new ExecutionEngine(executionContext))
          .optimizer(optimizer).build();
    }

    public Builder filePath(String filePath) {
      xxdb.setFilePath(filePath);
      return this;
    }

    public Builder diskManager(DiskManager diskManager) {
      xxdb.setDiskManager(diskManager);
      return this;
    }

    public Builder optimizer(Optimizer optimizer) {
      xxdb.setOptimizer(optimizer);
      return this;
    }

    public Builder executionEngine(ExecutionEngine executionEngine) {
      xxdb.setExecutionEngine(executionEngine);
      return this;
    }

    public Xxdb build() throws XxdbException {
      xxdb.check();;
      return xxdb;
    }
  }

  private void setDiskManager(DiskManager diskManager) {
    this.diskManager = diskManager;
  }

  private void setFilePath(String filePath) {
    this.filePath = filePath;
  }

  private void setOptimizer(Optimizer optimizer) {
    this.optimizer = optimizer;
  }

  private void setExecutionEngine(ExecutionEngine executionEngine) {
    this.executionEngine = executionEngine;
  }

  private void check() throws XxdbException {
    if (diskManager == null || filePath == null || optimizer == null || executionEngine == null) {
      throw new XxdbException("You haven't built all elements for Xxdb");
    }
  }

  private static LogicalPlan queryToLogicalPlan(String query) {
    AntlrToStatementVisitor planVisitor = new AntlrToStatementVisitor();
    SqlLexer lexer = new SqlLexer(CharStreams.fromString(query));
    SqlParser.SqlContext sql = new SqlParser(new CommonTokenStream(lexer)).sql();
    Statement select = planVisitor.visit(sql);
    StatementToPlanVisitor visitor = new StatementToPlanVisitor();
    select.accept(visitor);
    return visitor.getPlan();
  }

  private String prettyPrintResult(List<TupleResult> results, Schema outputSchema) {
    StringBuilder sb = new StringBuilder();
    if (results == null || results.isEmpty()) {
      sb.append("OK\n");
      return sb.toString();
    }
    // Print header
    String header = outputSchema.getColumns().stream()
        .map(col -> col.name())
        .collect(java.util.stream.Collectors.joining(" | "));
    sb.append(header).append("\n");
    // Print separator
    String sep = outputSchema.getColumns().stream()
        .map(col -> "-".repeat(col.name().length()))
        .collect(java.util.stream.Collectors.joining("-+-"));
    sb.append(sep).append("\n");
    // Print each tuple using its print logic
    for (TupleResult result : results) {
      String row = outputSchema.getColumns().stream()
          .map(col -> {
            var value = result.tuple().getValue(outputSchema, col.name());
            if (value instanceof dev.xxdb.types.StringValue sv) {
              try {
                var vField = sv.getClass().getDeclaredField("value");
                vField.setAccessible(true);
                String padded = (String) vField.get(sv);
                // Remove trailing nulls and spaces
                int end = padded.length();
                while (end > 0 && (padded.charAt(end - 1) == '\0' || padded.charAt(end - 1) == ' ')) {
                  end--;
                }
                return padded.substring(0, end);
              } catch (Exception e) {
                return value.toString();
              }
            } else if (value instanceof dev.xxdb.types.IntValue iv) {
              return Integer.toString(iv.value());
            } else {
              return value.toString();
            }
          })
          .collect(java.util.stream.Collectors.joining(" | "));
      sb.append(row).append("\n");
    }
    return sb.toString();
  }

  /**
   * Execute the SQL query
   * @param query to run
   * @return String output of the query
   */
  public String execute(String query) throws XxdbException {
    LogicalPlan logicalPlan = queryToLogicalPlan(query);
    PhysicalPlan plan = optimizer.run(logicalPlan);
    List<TupleResult> results;
    try {
       results = executionEngine.execute(plan);
    } catch (ExecutionException e) {
      throw new XxdbException(e);
    }
    return prettyPrintResult(results, executionEngine.getOutputSchema());
  }
}
