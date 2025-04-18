package dev.xxdb.optimizer;

import dev.xxdb.parser.ast.relationalgebra.Predicate;
import dev.xxdb.parser.ast.relationalgebra.PredicateVisitor;
import dev.xxdb.parser.ast.relationalgebra.Select;
import dev.xxdb.parser.ast.relationalgebra.ValuePredicate;
import dev.xxdb.types.IntValue;
import dev.xxdb.types.Ops;
import dev.xxdb.types.PredicateType;
import dev.xxdb.types.StringValue;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class OptimizerTest {

  @Nested
  class PredicateBuilderTest {
    // Testing strategy
    // + partition on selects len: 1, 2, >2
    // + partition on types len: 0, 1, >1

    private final Optimizer.PredicateBuidler builder = new Optimizer.PredicateBuidler();

    // cover selects len = 1, types len = 0
    @Test
    void simplePredicate() {
      Predicate pred = new ValuePredicate(Ops.EQUALS, "col1", new IntValue(20));
      List<Select> selects = List.of(new Select(pred, "FOO"));
      dev.xxdb.types.Predicate build = builder.build(selects, Collections.emptyList());
      assertEquals("SimplePredicate{column='col1', value=IntValue[value=20], op=EQUALS}", build.toString());
    }

    // cover selects len = 2, types len = 1
    @Test
    void andPredicate() {
      Predicate pred1 = new ValuePredicate(Ops.EQUALS, "col1", new IntValue(20));
      Predicate pred2 = new ValuePredicate(Ops.GREATER_THAN, "col2", new StringValue("20"));
      List<Select> selects = List.of(new Select(pred1, "FOO"), new Select(pred2, "BAR"));
      List<PredicateType> types = List.of(PredicateType.AND);
      dev.xxdb.types.Predicate build = builder.build(selects, types);
      assertEquals("AndPredicate{left=SimplePredicate{column='col1', value=IntValue[value=20], op=EQUALS}, " +
                                          "right=SimplePredicate{column='col2', value=StringValue[value=20], op=GREATER_THAN}}", build.toString());
    }
  }

  class RunTest {
    // Testing strategy
    // + partition on physical plan: insert, create, select
    // + partition on select: ...
  }
}