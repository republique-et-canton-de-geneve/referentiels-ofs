package ch.ge.cti.ct.referentiels.ofs.service.jmx;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class CallTest {

    @Test
    public void testHashCode() {
	final Call call1 = new Call();
	final Call call2 = new Call();

	assertTrue(call1.hashCode() == call2.hashCode());

	call2.withClasse(String.class);
	assertFalse(call1.hashCode() == call2.hashCode());
	call1.withClasse(String.class);
	assertTrue(call1.hashCode() == call2.hashCode());

	call2.withMethod(String.class.getMethods()[0]);
	assertFalse(call1.hashCode() == call2.hashCode());
	call1.withMethod(String.class.getMethods()[0]);
	assertTrue(call1.hashCode() == call2.hashCode());
    }

    @Test
    public void testEqualsObject() {
	final Call call1 = new Call();
	final Call call2 = new Call();

	assertTrue(call1.equals(call2));

	call2.withClasse(String.class);
	assertFalse(call1.equals(call2));
	call1.withClasse(String.class);
	assertTrue(call1.equals(call2));

	call2.withMethod(String.class.getMethods()[0]);
	assertFalse(call1.equals(call2));
	call1.withMethod(String.class.getMethods()[0]);
	assertTrue(call1.equals(call2));

	assertFalse(call1.equals(null));
	assertFalse(call1.equals(""));
    }

    @Test
    public void test() {
	final Call call = new Call();
	assertNull(call.getClasse());
	assertNull(call.getMethode());

	call.withClasse(String.class);
	assertEquals(String.class.getName(), call.getClasse());
	assertNull(call.getMethode());

	call.withMethod(String.class.getMethods()[0]);
	assertEquals(String.class.getName(), call.getClasse());
	assertEquals(String.class.getMethods()[0].getName(), call.getMethode());
    }

}
