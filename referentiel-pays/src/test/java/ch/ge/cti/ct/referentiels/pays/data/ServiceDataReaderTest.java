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
package ch.ge.cti.ct.referentiels.pays.data;

import static org.junit.Assert.assertNotNull;

import java.net.URL;

import org.junit.Test;

import ch.ge.cti.ct.referentiels.ofs.ReferentielOfsException;
import ch.ge.cti.ct.referentiels.pays.AbstractReferentielTest;
import ch.ge.cti.ct.referentiels.pays.model.ReferentielPaysTerritoires;

public class ServiceDataReaderTest extends AbstractReferentielTest {

	@Test
	public void testGetXmlFile() throws ReferentielOfsException {
		final ServiceDataReader service = new ServiceDataReader();
		final URL url = service.getXmlFile();
		assertNotNull(url);
	}

	@Test
	public void testRead() throws ReferentielOfsException {
		final ServiceDataReader service = new ServiceDataReader();
		final ReferentielPaysTerritoires ref = service.read();
		assertNotNull(ref);
	}
}
