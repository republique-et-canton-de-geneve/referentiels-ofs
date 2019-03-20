/*
 * Referentiels OFS
 *
 * Copyright (C) 2018 Republique et canton de Geneve
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

import ch.ge.cti.ct.referentiels.ofs.ReferentielOfsException;
import ch.ge.cti.ct.referentiels.pays.interfaces.ws.model.PaysWS;
import org.databene.contiperf.PerfTest;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

@PerfTest(invocations = 1000, threads = 20)
public class ReferentielPaysTerritoireWSTest extends AbstractRefWSTest {

    @Test
    public void getPays_devrait_trouver_la_Suisse() throws ReferentielOfsException {
        List<PaysWS> countries = getWS().getPays();

        // controler que la liste existe
        assertNotNull("La liste des pays ne devrait pas etre null", countries);

        // controler que la liste a la bonne taille
        int min = 150;
        int max = 250;
        LOGGER.debug("Nombre de pays obtenus : {}", countries.size());
        assertTrue("Le nombre de pays devrait etre compris entre " + min + " et " + max, countries.size() > min);

        // controler l'existence d'un pays en particulier
        String nomPays = "Suisse";
        PaysWS pays = countries.stream()
                .filter(c -> c.getNom().equals(nomPays))
                .findFirst().orElseThrow(() -> new ReferentielOfsException("Le pays [" + nomPays + "] n'est pas dans la liste"));

        // controler les champs de ce pays
        assertField(pays, "nomLong", pays.getNomLong(), "Confédération suisse");
        assertField(pays, "iso2", pays.getIso2(), "CH");
        assertField(pays, "iso3", pays.getIso3(), "CHE");
    }

    @Test
    public void getPaysByIso2_devrait_trouver_la_Suisse() throws ReferentielOfsException {
        String iso2 = "CH";
        PaysWS pays = getWS().getPaysByIso2(iso2);

        assertNotNull("Le pays de code ISO 2 '" + iso2 + "' ne devrait pas etre null", pays);
        assertEquals("Le pays de code ISO 2 '" + iso2 + "' devrait etre la Suisse", "Suisse", pays.getNom());
    }

    @Test
    public void getPaysByIso2_ne_devrait_pas_planter_pour_un_code_ISO_2_null() throws ReferentielOfsException {
        String iso2 = null;
        PaysWS pays = getWS().getPaysByIso2(iso2);

        assertNull("Le pays de code ISO 2 null devrait etre null", pays);
    }

    @Test
    public void getPaysByIso3_devrait_trouver_la_Suisse() throws ReferentielOfsException {
        String iso3 = "CHE";
        PaysWS pays = getWS().getPaysByIso3(iso3);

        assertNotNull("Le pays de code ISO 3 '" + iso3 + "' ne devrait pas etre null", pays);
        assertEquals("Le pays de code ISO 3 '" + iso3 + "' devrait etre la Suisse", "Suisse", pays.getNom());
    }

    @Test
    public void searchPays_devrait_trouver_la_Suisse() throws ReferentielOfsException {
        String critere = "Suis";
        List<PaysWS> pays = getWS().searchPays(critere);

        assertNotNull("La liste de pays ne devrait pas etre null", pays);
        assertEquals("La liste de pays n'a pas la taille attendue", 1, pays.size());
        assertEquals("Le pays dans la liste n'est pas le pays attendu", "Suisse", pays.get(0).getNom());
    }

    private void assertField(PaysWS pays, String champ, Object expected, Object actual) {
        assertEquals("La valeur du champ '" + champ + "' du pays est incorrecte" + pays.getNom(), expected, actual);
    }

}
