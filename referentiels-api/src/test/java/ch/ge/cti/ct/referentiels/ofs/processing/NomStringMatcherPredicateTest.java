package ch.ge.cti.ct.referentiels.ofs.processing;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class NomStringMatcherPredicateTest {

    @Test
    public void testApply() {
	NomStringMatcherPredicate predicate = null;

	predicate = new NomStringMatcherPredicate(null);
	assertTrue(predicate.apply(new _ComplexType(0, null)));
	assertTrue(predicate.apply(new _ComplexType(0, "")));
	assertFalse(predicate.apply(new _ComplexType(0, "a")));
	assertFalse(predicate.apply(new _ComplexType(0, "abc")));
	assertFalse(predicate.apply(new _ComplexType(0, "abcdef")));

	predicate = new NomStringMatcherPredicate("");
	assertTrue(predicate.apply(new _ComplexType(0, null)));
	assertTrue(predicate.apply(new _ComplexType(0, "")));
	assertFalse(predicate.apply(new _ComplexType(0, "a")));
	assertFalse(predicate.apply(new _ComplexType(0, "abc")));
	assertFalse(predicate.apply(new _ComplexType(0, "abcdef")));

	predicate = new NomStringMatcherPredicate("a");
	assertFalse(predicate.apply(new _ComplexType(0, null)));
	assertFalse(predicate.apply(new _ComplexType(0, "")));
	assertTrue(predicate.apply(new _ComplexType(0, "a")));
	assertTrue(predicate.apply(new _ComplexType(0, "abc")));
	assertTrue(predicate.apply(new _ComplexType(0, "abcdef")));

	predicate = new NomStringMatcherPredicate("abc");
	assertFalse(predicate.apply(new _ComplexType(0, null)));
	assertFalse(predicate.apply(new _ComplexType(0, "")));
	assertFalse(predicate.apply(new _ComplexType(0, "a")));
	assertTrue(predicate.apply(new _ComplexType(0, "abc")));
	assertTrue(predicate.apply(new _ComplexType(0, "abcdef")));

	predicate = new NomStringMatcherPredicate("abcdef");
	assertFalse(predicate.apply(new _ComplexType(0, null)));
	assertFalse(predicate.apply(new _ComplexType(0, "")));
	assertFalse(predicate.apply(new _ComplexType(0, "a")));
	assertFalse(predicate.apply(new _ComplexType(0, "abc")));
	assertTrue(predicate.apply(new _ComplexType(0, "abcdef")));

	predicate = new NomStringMatcherPredicate("abcdefghi");
	assertFalse(predicate.apply(new _ComplexType(0, null)));
	assertFalse(predicate.apply(new _ComplexType(0, "")));
	assertFalse(predicate.apply(new _ComplexType(0, "a")));
	assertFalse(predicate.apply(new _ComplexType(0, "abc")));
	assertFalse(predicate.apply(new _ComplexType(0, "abcdef")));

	predicate = new NomStringMatcherPredicate("ABC");
	assertTrue(predicate.apply(new _ComplexType(0, "abc")));
	assertTrue(predicate.apply(new _ComplexType(0, "abcdef")));
	assertTrue(predicate.apply(new _ComplexType(0, "Abc")));
	assertTrue(predicate.apply(new _ComplexType(0, "Abcdef")));
	assertTrue(predicate.apply(new _ComplexType(0, "aBc")));
	assertTrue(predicate.apply(new _ComplexType(0, "aBcdef")));
	assertTrue(predicate.apply(new _ComplexType(0, "ABC")));
	assertTrue(predicate.apply(new _ComplexType(0, "ABCDEF")));
    }

    @Test
    public void testApplyNormalized() {
	NomStringMatcherPredicate predicate = null;

	predicate = new NomStringMatcherPredicate("abcdef");
	assertTrue(predicate.apply(new _ComplexType(0, "abcdef")));
	assertTrue(predicate.apply(new _ComplexType(0, "àbcdef")));
	assertTrue(predicate.apply(new _ComplexType(0, "äbcdef")));
	assertTrue(predicate.apply(new _ComplexType(0, "abcdéf")));
	assertTrue(predicate.apply(new _ComplexType(0, "àbcdèf")));
	assertTrue(predicate.apply(new _ComplexType(0, "äbcdëf")));
	assertTrue(predicate.apply(new _ComplexType(0, "ÀBCDEF")));
    }
}
