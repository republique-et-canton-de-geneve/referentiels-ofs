package ch.ge.cti.ct.referentiels.ofs.processing;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class IdFilterPredicateTest {

    private final IdFilterPredicate predicate = new IdFilterPredicate(1);

    @Test
    public void testApply() {
	TestComplexType test = new TestComplexType(0, "");
	assertFalse("Le pr�dicat est incorrect", predicate.apply(test));

	test = new TestComplexType(1, null);
	assertTrue("Le pr�dicat est incorrect", predicate.apply(test));
    }

}