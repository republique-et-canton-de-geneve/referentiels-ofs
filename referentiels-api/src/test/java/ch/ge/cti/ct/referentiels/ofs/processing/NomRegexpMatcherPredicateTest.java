package ch.ge.cti.ct.referentiels.ofs.processing;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class NomRegexpMatcherPredicateTest {

    @Test
    public void testApply() {
	NomRegexpMatcherPredicate predicate = null;

	predicate = new NomRegexpMatcherPredicate(null);
	assertFalse(predicate.apply(new TestComplexType(0, null)));
	assertFalse(predicate.apply(new TestComplexType(0, "")));

	predicate = new NomRegexpMatcherPredicate("");
	assertFalse(predicate.apply(new TestComplexType(0, null)));
	assertFalse(predicate.apply(new TestComplexType(0, "")));

	predicate = new NomRegexpMatcherPredicate("a");
	assertFalse(predicate.apply(new TestComplexType(0, null)));
	assertFalse(predicate.apply(new TestComplexType(0, "")));
	assertTrue(predicate.apply(new TestComplexType(0, "a")));
	assertTrue(predicate.apply(new TestComplexType(0, "abcdef")));
	assertTrue(predicate.apply(new TestComplexType(0, "bcadef")));
	assertTrue(predicate.apply(new TestComplexType(0, "BCADEF")));
	assertTrue(predicate.apply(new TestComplexType(0, "bcädef")));

	predicate = new NomRegexpMatcherPredicate("a.c");
	assertFalse(predicate.apply(new TestComplexType(0, null)));
	assertFalse(predicate.apply(new TestComplexType(0, "")));
	assertFalse(predicate.apply(new TestComplexType(0, "a")));
	assertTrue(predicate.apply(new TestComplexType(0, "abcdef")));
	assertTrue(predicate.apply(new TestComplexType(0, "cdabcef")));
	assertTrue(predicate.apply(new TestComplexType(0, "ABC")));
	assertFalse(predicate.apply(new TestComplexType(0, "bcadef")));
    }

}
