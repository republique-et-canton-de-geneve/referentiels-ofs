package ch.ge.cti.ct.referentiels.ofs.processing;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import org.junit.Test;

import ch.ge.cti.ct.referentiels.ofs.model.IComplexType;

public class AbstractMatcherPredicateTest {

    private final AbstractMatcherPredicate predicate = new AbstractMatcherPredicate() {
	@Override
	public boolean apply(final IComplexType arg0) {
	    return false;
	}
    };

    @Test
    public void testNormalize() {
	assertNull(predicate.normalize(null));
	assertEquals("", predicate.normalize(""));
	assertEquals("a", predicate.normalize("a"));
	assertEquals("abcdefgh", predicate.normalize("abcdefgh"));
	assertEquals(
		"abcdefghijklmnopqrstuvwxyz 1234567890,;:.-_+-/*[]{}()\\<>'~`^",
		predicate
			.normalize("abcdefghijklmnopqrstuvwxyz 1234567890,;:.-_+-/*[]{}()\\<>'~`^"));
	assertEquals("aaaaaeeeeooooouuu",
		predicate.normalize("aàäâãeéèêoòôöõuùû"));
    }

}
