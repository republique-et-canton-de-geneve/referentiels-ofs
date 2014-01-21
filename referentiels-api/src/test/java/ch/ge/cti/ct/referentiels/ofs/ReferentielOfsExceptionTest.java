package ch.ge.cti.ct.referentiels.ofs;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.io.IOException;

import org.junit.Test;

public class ReferentielOfsExceptionTest {

	@Test
	public void testReferentielOfsException() {
		final ReferentielOfsException roe = new ReferentielOfsException();
		assertNull("ReferentielOfsException.message est incorrect",
				roe.getMessage());
		assertNull("ReferentielOfsException.cause est incorrect",
				roe.getCause());
	}

	@Test
	public void testReferentielOfsExceptionStringThrowable() {
		final ReferentielOfsException roe = new ReferentielOfsException(
				"message", new IOException("__IOException__"));
		assertEquals("ReferentielOfsException.message est incorrect",
				"message", roe.getMessage());
		assertEquals("ReferentielOfsException.cause est incorrect",
				IOException.class, roe.getCause().getClass());
	}

	@Test
	public void testReferentielOfsExceptionString() {
		final ReferentielOfsException roe = new ReferentielOfsException(
				"message");
		assertEquals("ReferentielOfsException.message est incorrect",
				"message", roe.getMessage());
		assertNull("ReferentielOfsException.cause est incorrect",
				roe.getCause());
	}

	@Test
	public void testReferentielOfsExceptionThrowable() {
		final ReferentielOfsException roe = new ReferentielOfsException(
				new IOException("__IOException__"));
		assertEquals("ReferentielOfsException.message est incorrect",
				"java.io.IOException: __IOException__", roe.getMessage());
		assertEquals("ReferentielOfsException.cause est incorrect",
				IOException.class, roe.getCause().getClass());
	}

}
