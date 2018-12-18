/*
 * Referentiels OFS
 *
 * Copyright (C) 2018 République et canton de Genève
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
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
