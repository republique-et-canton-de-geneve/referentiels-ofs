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
package ch.ge.cti.ct.referentiels.formeJuridique.interfaces.ws;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import ch.ge.cti.ct.referentiels.AbstractClientTest;
import ch.ge.cti.ct.referentiels.formeJuridique.client.ReferentielFormesJuridiquesClient;

public class ReferentielFormesJuridiquesClientTestIT extends AbstractClientTest {

    @Test
    public void test() throws Exception {
	final ReferentielFormesJuridiquesWS client = ReferentielFormesJuridiquesClient.Factory
	        .getClient(URL + "formes-juridiques/referentiel-formes-juridiques?wsdl");
	assertTrue(client.getFormesJuridiques().size() > 0);
	assertNotNull(client.getFormeJuridique(1));
    }
}
