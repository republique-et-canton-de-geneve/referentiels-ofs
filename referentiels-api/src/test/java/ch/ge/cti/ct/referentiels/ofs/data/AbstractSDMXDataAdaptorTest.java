package ch.ge.cti.ct.referentiels.ofs.data;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.net.URL;

import org.junit.Test;
import org.slf4j.Logger;

import ch.ge.cti.ct.referentiels.ofs.ReferentielOfsException;

public class AbstractSDMXDataAdaptorTest extends AbstractDataTest {

	final TestSDMXDataAdaptor sdmxDataAdaptor = new TestSDMXDataAdaptor(
			new SDMXParser(null, null));

	@Test
	public void testGetParser() throws Exception {
		final SDMXParser parser = sdmxDataAdaptor.getParser();
		assertNotNull("Getter du parser incorrect", parser);
	}

	@Test
	public void testLog() throws Exception {
		final Logger log = sdmxDataAdaptor.log();
		assertNotNull("Getter du logger incorrect", log);
		assertEquals(
				"Logger incorrect",
				"ch.ge.cti.ct.referentiels.ofs.data.AbstractSDMXDataAdaptorTest$TestSDMXDataAdaptor",
				log.getName());
	}

	@Test
	public void testParse() throws Exception {
		final String result = sdmxDataAdaptor.parse(null);
		assertEquals("Parsing incorrect", "OK", result);
	}

	public static class TestSDMXDataAdaptor extends
			AbstractSDMXDataAdaptor<String> {

		protected TestSDMXDataAdaptor(final SDMXParser parser) {
			super(parser);
		}

		@Override
		public String parse(final URL urlXML) throws ReferentielOfsException {
			return "OK";
		}
	}
}
