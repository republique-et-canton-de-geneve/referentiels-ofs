/*
 * Referentiels OFS
 *
 * Copyright (C) 2018 R√©publique et canton de Gen√®ve
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
package ch.ge.cti.ct.referentiels.professions.interfaces.ws;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import ch.ge.cti.ct.referentiels.AbstractClientTest;
import ch.ge.cti.ct.referentiels.professions.client.ReferentielProfessionsClient;

public class ReferentielProfessionsClientTestIT extends AbstractClientTest {

    @Test
    public void test() throws Exception {
	final ReferentielProfessionsWS client = ReferentielProfessionsClient.Factory
		//.getClient("http://jbdev20-22:20000/referentiels-ofs/professions/referentiel-professions?wsdl");
	        .getClient("http://localhost:8080/referentiels-ofs/professions/referentiel-professions?wsdl");
	assertTrue(client.getClasses().size() > 0);
	assertTrue(client.getDivisions().size() > 0);
	assertTrue(client.getGenres().size() > 0);

	assertTrue(client.getClassesByDivision(1).size() > 0);
	assertTrue(client.getGenresByGroup(111).size() > 0);
	assertTrue(client.getGroupesByClasse(11).size() > 0);

	assertTrue(client.searchClasse("IngÈnieur").size() > 0);
	assertTrue(client.searchClasseRegexp("gÈnieur").size() > 0);
	assertTrue(client.searchGenre("IngÈnieur").size() > 0);
	assertTrue(client.searchGenreRegexp("gÈnieur").size() > 0);
	assertTrue(client.searchGroupe("IngÈnieur").size() > 0);
	assertTrue(client.searchGroupeRegexp("gÈnieur").size() > 0);
	assertTrue(client.searchDivision("Professions").size() > 0);
	assertTrue(client.searchDivisionRegexp("rofession").size() > 0);

	assertNotNull(client.getClasse(11));
	assertNotNull(client.getDivision(1));
	assertNotNull(client.getGenre(11101));
	assertNotNull(client.getGroupe(111));
    }
}
