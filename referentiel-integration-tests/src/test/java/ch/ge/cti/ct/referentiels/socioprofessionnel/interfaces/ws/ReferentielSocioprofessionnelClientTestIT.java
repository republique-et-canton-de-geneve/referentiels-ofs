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
package ch.ge.cti.ct.referentiels.socioprofessionnel.interfaces.ws;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import ch.ge.cti.ct.referentiels.AbstractClientTest;
import ch.ge.cti.ct.referentiels.socioprofessionnel.client.ReferentielSocioprofessionnelClient;

public class ReferentielSocioprofessionnelClientTestIT extends
	AbstractClientTest {

    @Test
    public void test() throws Exception {
	final ReferentielSocioprofessionnelWS client = ReferentielSocioprofessionnelClient.Factory
	        .getClient(URL + "socioprofessionnel/referentiel-socioprofessionnel?wsdl");

	assertTrue(client.getNiveaux1().size() > 0);
	assertTrue(client.searchNiveaux1("Dirigeant").size() > 0);
	assertTrue(client.searchNiveaux1Regexp("^Dirigeant").size() > 0);

	assertTrue(client.getNiveaux2().size() > 0);
	assertTrue(client.getNiveaux2ByNiveau1(20).size() > 0);
	assertTrue(client.searchNiveaux2("Dirigeant").size() > 0);
	assertTrue(client.searchNiveaux2Regexp("^Dirigeant").size() > 0);

	assertNotNull(client.getNiveau1(20));
	assertNotNull(client.getNiveau2(201));
	assertNotNull(client.getNiveaux2ByNiveau1(20));
    }
}
