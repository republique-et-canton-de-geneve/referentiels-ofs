package ch.ge.cti.ct.referentiels.ofs.processing;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class IdFilterPredicateTest {

    private final IdFilterPredicate predicate = new IdFilterPredicate(1);

    @Test
    public void testApply() {
	_ComplexType test = new _ComplexType(0, "");
	assertFalse("Le prédicat est incorrect", predicate.apply(test));

	test = new _ComplexType(1, null);
	assertTrue("Le prédicat est incorrect", predicate.apply(test));
    }

}
