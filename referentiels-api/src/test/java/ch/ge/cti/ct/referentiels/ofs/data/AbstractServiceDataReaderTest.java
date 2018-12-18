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

import java.net.URL;

import org.junit.Test;

import ch.ge.cti.ct.referentiels.ofs.ReferentielOfsException;

public class AbstractServiceDataReaderTest extends AbstractDataTest {

	final TestServiceDataReader serviceDataReader = new TestServiceDataReader();

	@Test
	public void testRead() throws Exception {
		final String result = serviceDataReader.read();
		assertEquals("Le r�sultat du parsing est incorrect", "OK", result);
	}

	@Test
	public void testGetXmlFile() throws Exception {
		final URL url = serviceDataReader.getXmlFile();
		assertEquals("URL du fichier XML incorrecte", "test.xml", url.getPath()
				.substring(url.getPath().lastIndexOf('/') + 1));
	}

	public static class TestServiceDataReader extends
			AbstractServiceDataReader<String> {

		@Override
		protected String getConfigurationEntry() {
			return "test.xml";
		}

		@Override
		protected AbstractSDMXDataAdaptor<String> getDataAdaptor() {
			return new TestSDMXDataAdaptor(new SDMXParser(null, null));
		}
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
