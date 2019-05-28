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

import ch.ge.cti.ct.referentiels.communes.interfaces.ws.model.CantonWS;
import ch.ge.cti.ct.referentiels.communes.interfaces.ws.model.CommuneWS;
import ch.ge.cti.ct.referentiels.communes.interfaces.ws.model.DistrictWS;
import ch.ge.cti.ct.referentiels.ofs.ReferentielOfsException;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

public class ReferentielCommunesWSTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(ReferentielCommunesWSTest.class);

    private static final ReferentielCommunesWS ws = new ReferentielCommunesSEI();

    @Test
    public void getCantons_devrait_rendre_une_liste_non_vide() throws ReferentielOfsException {
        List<CantonWS> cantons = getWS().getCantons();

        // controler que la liste existe
        assertNotNull("La liste des cantons ne devrait pas etre nulle", cantons);

        // controler que la liste n'est pas vide
        LOGGER.debug("Nombre de cantons obtenus : {}", cantons.size());
        assertTrue("La liste de cantons ne devrait pas etre vide", cantons.size() > 0);
    }

    @Test
    public void getCanton_devrait_rendre_un_canton() throws ReferentielOfsException {
        CantonWS canton = getWS().getCanton("VD");

        assertNotNull("Le canton VD devrait etre present", canton);

        // controler que les champs ont ete initialises
        assertField("code", "VD", canton.getCode());
        assertField("nom", "Vaud", canton.getNom());
        assertField("nomCourt", "Vaud", canton.getNomCourt());
    }

    @Test
    public void getCanton_devrait_rendre_null() throws ReferentielOfsException {
        CantonWS canton = getWS().getCanton("XX");

        assertNull("Le canton XX ne devrait etre present", canton);
    }

    @Test
    public void getDistrict_devrait_rendre_un_district() throws ReferentielOfsException {
        DistrictWS district = getWS().getDistrict(10279);

        assertNotNull("Le district devrait etre present", district);

        // controler que les champs ont ete initialises
        assertField("code", 10279, district.getId());
        assertField("nom", "VD", district.getCodeCanton());
        assertField("nomCourt", "District de Morges", district.getNom());
        assertField("nomCourt", "Morges", district.getNomCourt());
    }

    @Test
    public void getDistrictsByCanton_devrait_rendre_une_liste_non_vide() throws ReferentielOfsException {
        List<DistrictWS> districts = getWS().getDistrictsByCanton("VD");

        // controler que la liste existe
        assertNotNull("La liste de districts ne devrait pas etre nulle", districts);

        // controler que la liste n'est pas vide
        LOGGER.debug("Nombre de districts obtenus : {}", districts.size());
        assertTrue("La liste de districts ne devrait pas etre vide", districts.size() > 0);
    }

    @Test
    public void getCommune_devrait_rendre_une_commune() throws ReferentielOfsException {
        CommuneWS commune = getWS().getCommune(4761);

        assertNotNull("La commune devrait etre presente", commune);

        // controler que les champs ont ete initialises
        assertField("code", 4761, commune.getId());
        assertField("nom", "TG", commune.getCodeCanton());
        assertField("nomCourt", "Sirnach", commune.getNom());
        assertField("nomCourt", "Sirnach", commune.getNomCourt());
        assertField("nomCourt", 10296, commune.getIdDistrict());
    }

    @Test
    public void getCommunesByCanton_devrait_rendre_le_nombre_attendu_de_communes() throws ReferentielOfsException {
        String canton = "GE";
        List<CommuneWS> communes = getWS().getCommunesByCanton(canton);

        assertEquals("Le nombre de communes dans le canton '" + canton + "' est incorrect",
                45, communes.size());
    }

    @Test
    public void searchCommune_devrait_rendre_des_communes() throws ReferentielOfsException {
        String critere = "gen";
        List<CommuneWS> communes = getWS().searchCommune(critere);

        assertEquals("Le nombre de communes obtenues avec le critere '" + critere + "' est incorrect",
                3, communes.size());
    }

    private ReferentielCommunesWS getWS() {
        return ws;
    }

    private void assertField(String champ, Object expected, Object actual) {
        assertEquals("La valeur du champ '" + champ + "' est incorrecte", expected, actual);
    }

}