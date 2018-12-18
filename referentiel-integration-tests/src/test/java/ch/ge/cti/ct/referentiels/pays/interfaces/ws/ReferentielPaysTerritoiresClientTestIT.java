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
package ch.ge.cti.ct.referentiels.pays.interfaces.ws;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import ch.ge.cti.ct.referentiels.AbstractClientTest;
import ch.ge.cti.ct.referentiels.pays.client.ReferentielPaysTerritoiresClient;

public class ReferentielPaysTerritoiresClientTestIT extends AbstractClientTest {

    @Test
    public void test() throws Exception {
	final ReferentielPaysTerritoiresWS client = ReferentielPaysTerritoiresClient.Factory
		//.getClient("http://jbdev20-22:20000/referentiels-ofs/territoires/referentiel-pays?wsdl");
	        .getClient("http://localhost:8080/referentiels-ofs/territoires/referentiel-pays?wsdl");
	assertTrue(client.getContinents().size() > 0);
	assertTrue(client.getRegions().size() > 0);
	assertTrue(client.getPays().size() > 0);

	assertTrue(client.getPaysByContinent(1).size() > 0);
	assertTrue(client.getPaysByRegion(11).size() > 0);
	assertTrue(client.getRegionsByContinent(1).size() > 0);

	assertTrue(client.searchPays("Fr").size() > 0);
	assertTrue(client.searchPays("FR").size() > 0);
	assertTrue(client.searchPaysRegexp(".+sse").size() > 0);
	assertTrue(client.searchPaysRegexp(".+SSE").size() > 0);
	assertTrue(client.searchPaysRegexp("sse").size() > 0);

	assertNotNull(client.getContinent(1));
	assertNotNull(client.getPaysByIso2("CH"));
	assertNotNull(client.getPaysByIso3("CHE"));
	assertNotNull(client.getRegion(11));
    }
}
