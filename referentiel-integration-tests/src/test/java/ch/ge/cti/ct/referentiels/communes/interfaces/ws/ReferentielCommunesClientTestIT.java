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
package ch.ge.cti.ct.referentiels.communes.interfaces.ws;

import ch.ge.cti.ct.referentiels.AbstractClientTest;
import ch.ge.cti.ct.referentiels.communes.client.ReferentielCommunesClient;
import ch.ge.cti.ct.referentiels.communes.interfaces.ws.model.CantonWS;
import ch.ge.cti.ct.referentiels.communes.interfaces.ws.model.CommuneWS;
import ch.ge.cti.ct.referentiels.ofs.ReferentielOfsException;
import org.junit.Assert;
import org.junit.Test;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class ReferentielCommunesClientTestIT extends AbstractClientTest {

    private static final Date NOW = new Date();

    final ReferentielCommunesWS client = ReferentielCommunesClient.Factory
            .getClient(URL + "communes/referentiel-communes?wsdl");

    public ReferentielCommunesClientTestIT() throws Exception {
    }

    @Test
    public void test() throws Exception {
        assertTrue(client.getCantons().size() > 0);
        assertNotNull(client.getCanton("GE"));

        assertNotNull(client.getDistrict(101));
        assertTrue(client.getDistrictsByCanton("GE").size() > 0);

        assertNotNull(client.getCommune(1001));
        assertTrue(client.getCommunesByCanton("GE").size() > 0);
        assertTrue(client.searchCommune("gen").size() > 0);
    }

}
