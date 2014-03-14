package ch.ge.cti.ct.referentiels.ofs.processing;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class NomComparatorTest {

    private final NomComparator comparator = new NomComparator();

    @Test
    public void testCompare() {
	assertTrue(comparator.compare(new TestComplexType(0, null),
		new TestComplexType(0, null)) == 0);
	assertTrue(comparator.compare(new TestComplexType(0, ""),
		new TestComplexType(0, "")) == 0);
	assertTrue(comparator.compare(new TestComplexType(0, "a"),
		new TestComplexType(0, "a")) == 0);
	assertFalse(comparator.compare(new TestComplexType(0, "a"),
		new TestComplexType(0, "A")) == 0);
	assertFalse(comparator.compare(new TestComplexType(0, null),
		new TestComplexType(0, "")) == 0);
	assertFalse(comparator.compare(new TestComplexType(0, null),
		new TestComplexType(0, "a")) == 0);
	assertFalse(comparator.compare(new TestComplexType(0, ""),
		new TestComplexType(0, null)) == 0);
	assertFalse(comparator.compare(new TestComplexType(0, "a"),
		new TestComplexType(0, null)) == 0);
    }

}
