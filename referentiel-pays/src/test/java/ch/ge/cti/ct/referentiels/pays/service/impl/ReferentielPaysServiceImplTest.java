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
package ch.ge.cti.ct.referentiels.pays.service.impl;

import ch.ge.cti.ct.referentiels.pays.data.Country;
import org.apache.commons.lang.StringUtils;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.junit.Assert.*;

public class ReferentielPaysServiceImplTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(ReferentielPaysServiceImplTest.class);

    @Test
    public void getPays_devrait_rendre_le_nombre_attendu_de_pays() {
        List<Country> countries = getService().getPays();
        System.out.println("nombre de pays : " + countries.size());

        // traces (pour le debugging)
        int i = 0;
        for (Country c : countries) {
            LOGGER.debug("{} {}", ++i, c);
        }

        assertEquals("Le nombre de pays est incorrect", 198, countries.size());
    }

    @Test
    public void getPays_devrait_contenir_des_pays_avec_tous_les_champs_non_nuls() {
        List<Country> countries = getService().getPays();

        countries.forEach(c -> {
            assertFieldNotBlank(c, "shortNameFr", c.getShortNameFr());
            assertFieldNotBlank(c, "officialNameFr", c.getOfficialNameFr());
            assertFieldNotBlank(c, "iso2Id", c.getIso2Id());
            assertFieldNotBlank(c, "iso2Id", c.getIso2Id());
        });
    }

    @Test
    public void getPays_devrait_rendre_les_pays_dans_l_ordre_alphabetique() {
        List<Country> countries = getService().getPays();

        assertEquals("Pays incorrect en cet endroit de la liste", "Afghanistan", countries.get(0).getShortNameFr());
        assertEquals("Pays incorrect en cet endroit de la liste", "Lettonie", countries.get(99).getShortNameFr());
        assertEquals("Pays incorrect en cet endroit de la liste", "Zimbabwe", countries.get(197).getShortNameFr());
    }

    @Test
    public void getPays_devrait_contenir_la_Suisse() {
        List<Country> countries = getService().getPays();

        long count = countries.stream()
                .filter(c -> c.getShortNameFr().equals("Suisse"))
                .count();
        assertEquals("Nombre erroné d'occurrences de la Suisse dans la liste", 1, count);
    }

    @Test
    public void getPays_devrait_contenir_un_pays_normal_avec_les_bons_champs() {
        List<Country> countries = getService().getPays();

        Country pays = assertPaysPresent(countries, "Brésil");

        assertField(pays, "iso2Id", "BR", pays.getIso2Id());
        assertField(pays, "iso2Id", "BR", pays.getIso2Id());
        assertField(pays, "officialNameFr", "République fédérative du Brésil", pays.getOfficialNameFr());
    }

    @Test
    public void getPays_devrait_contenir_le_Kosovo_bien_initialise() {
        List<Country> countries = getService().getPays();

        Country pays = assertPaysPresent(countries, "Kosovo");

        assertField(pays, "iso2Id", "XK", pays.getIso2Id());
        assertField(pays, "iso3Id", "XKX", pays.getIso3Id());
        assertField(pays, "officialNameFr", "République du Kosovo", pays.getOfficialNameFr());
    }

    @Test
    public void getPaysByIso2_devrait_ne_pas_planter_si_le_code_iso2_est_null() {
        String iso2 = null;
        Optional<Country> country = getService().getPaysByIso2(iso2);

        assertFalse("Le pays de code ISO 2 nul ne devrait pas exister", country.isPresent());
    }

    @Test
    public void getPaysByIso2_devrait_ne_pas_planter_pour_un_code_iso2_inexistant() {
        String iso2 = "PIPO";
        Optional<Country> country = getService().getPaysByIso2(iso2);

        assertFalse("Le pays de code ISO 2 '" + iso2 + "' ne devrait pas exister", country.isPresent());
    }

    @Test
    public void getPaysByIso2_devrait_trouver_la_Suisse() {
        String iso2 = "CH";
        Optional<Country> country = getService().getPaysByIso2(iso2);

        assertTrue("Le pays de code ISO 2 '" + iso2 + "' est introuvable", country.isPresent());
        assertEquals("Le pays de code ISO 2 '" + iso2 + "' devrait être la Suisse","Suisse", country.get().getShortNameFr());
    }

    @Test
    public void getPaysByIso3_devrait_trouver_la_Suisse() {
        String iso3 = "CHE";
        Optional<Country> country = getService().getPaysByIso3(iso3);

        assertTrue("Le pays de code ISO 3 '" + iso3 + "' est introuvable", country.isPresent());
        assertEquals("Le pays de code ISO 3 '" + iso3 + "' devrait être la Suisse","Suisse", country.get().getShortNameFr());
    }

    @Test
    public void searchPays_devrait_rendre_une_liste_vide_si_le_critere_est_null() {
        String critere = null;
        List<Country> countries = getService().searchPays(critere);

        assertNotNull("La liste de pays pour critere = '" + critere + "' devrait pas etre null", countries);
        assertTrue("La liste de pays pour critere = '" + critere + "' devrait etre vide", countries.isEmpty());
    }

    @Test
    public void searchPays_devrait_rendre_une_liste_vide_si_le_critere_est_vide() {
        String critere = "";
        List<Country> countries = getService().searchPays(critere);

        assertNotNull("La liste de pays pour critere = '" + critere + "' ne devrait pas etre null", countries);
        assertTrue("La liste de pays pour critere = '" + critere + "' devrait etre vide", countries.isEmpty());
    }

    @Test
    public void searchPays_devrait_rendre_une_liste_vide_si_le_critere_est_debile() {
        String critere = "LongCritereDebile";
        List<Country> countries = getService().searchPays(critere);

        assertTrue("La liste de pays pour critere = '" + critere + "' devrait etre vide", countries.isEmpty());
    }

    @Test
    public void searchPays_devrait_rendre_une_liste_correcte_1() {
        String critere = "be";   // teste aussi l'accentuation, doit trouver Belgique, Belize et Bénin
        List<Country> countries = getService().searchPays(critere);

        assertEquals("La liste de pays pour critere = '" + critere + "' a la taille correcte", 3, countries.size());
    }

    @Test
    public void searchPays_devrait_rendre_une_liste_correcte_2() {
        String critere = "E";   // teste aussi l'accentuation, doit trouver Belgique, Belize et Bénin
        List<Country> countries = getService().searchPays(critere);

        assertEquals("La liste de pays pour critere = '" + critere + "' a la taille correcte", 8, countries.size());
        assertPays(countries,"Égypte", 0);
        assertPays(countries,"Émirats arabes unis", 1);
        assertPays(countries,"Équateur", 2);
        assertPays(countries,"Érythrée", 3);
        assertPays(countries,"Espagne", 4);
        assertPays(countries,"Estonie", 5);
    }

    private ReferentielPaysServiceImpl getService() {
        return new ReferentielPaysServiceImpl();
    }

    private Country assertPaysPresent(List<Country> countries, String nomPays) {
        List<Country> coll = countries.stream()
                .filter(c -> c.getShortNameFr().equals(nomPays))
                .collect(Collectors.toList());
        assertEquals("Nombre d'occurrence du " + nomPays + " dans la liste des pays", 1, coll.size());
        return coll.get(0);
    }

    private void assertField(Country pays, String champ, Object expected, Object actual) {
        assertEquals("La valeur du champ '" + champ + "' du pays " + pays.getShortNameFr() + " est incorrecte",
                expected, actual);
    }

    private void assertPays(List<Country> countries, String nomPays, int index) {
        assertEquals("Le pays en position " + index + " (la numerotation commencant à 0) n'est pas celui qui est attendu",
                nomPays, countries.get(index).getShortNameFr());
    }

    private void assertFieldNotBlank(Country pays, String champ, String actual) {
        assertFalse("Valeur du champ '" + champ + "' du pays " + pays.getShortNameFr(), StringUtils.isBlank(actual));
    }

}
