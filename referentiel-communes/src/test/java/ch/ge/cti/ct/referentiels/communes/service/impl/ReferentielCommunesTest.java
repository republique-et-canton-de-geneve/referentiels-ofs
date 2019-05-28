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
package ch.ge.cti.ct.referentiels.communes.service.impl;

import ch.ge.cti.ct.referentiels.communes.data.Canton;
import ch.ge.cti.ct.referentiels.communes.data.District;
import ch.ge.cti.ct.referentiels.communes.data.Municipality;
import ch.ge.cti.ct.referentiels.communes.service.ReferentielCommunesService;
import org.apache.commons.lang.StringUtils;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class ReferentielCommunesTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(ReferentielCommunesTest.class);

    private static final ReferentielCommunesService service = new ReferentielCommunesServiceImpl();

    @Test
    public void getCantons_devrait_rendre_le_nombre_attendu_de_cantons() {
        List<Canton> cantons = getService().getCantons();
        LOGGER.debug("nombre de cantons : " + cantons.size());

        // traces (pour le debugging)
        int i = 0;
        for (Canton c : cantons) {
            LOGGER.debug("{} {}", ++i, c);
        }

        assertEquals("Le nombre de cantons est incorrect", 26, cantons.size());
    }

    @Test
    public void getCantons_devrait_rendre_les_cantons_avec_tous_les_champs_non_nuls() {
        List<Canton> cantons = getService().getCantons();

        cantons.forEach(c -> {
            assertCantonFieldNotBlank(c, "cantonAbbreviation", c.getCantonAbbreviation());
            assertCantonFieldNotBlank(c, "cantonLongName", c.getCantonLongName());
        });
    }

    @Test
    public void getCantons_devrait_rendre_les_cantons_avec_des_noms_en_francais() {
        List<Canton> cantons = getService().getCantons();

        assertEquals("Libellé inattendu du canton", "Argovie", cantons.get(2).getCantonLongName());
        assertEquals("Libellé inattendu du canton", "Berne", cantons.get(5).getCantonLongName());
    }

    @Test
    public void getCantons_devrait_rendre_les_cantons_dans_l_ordre_alphabetique() {
        List<Canton> cantons = getService().getCantons();

        assertEquals("Canton incorrect en cet endroit de la liste", "AR", cantons.get(0).getCantonAbbreviation());
        assertEquals("Canton incorrect en cet endroit de la liste", "ZH", cantons.get(25).getCantonAbbreviation());
    }

    @Test
    public void getCanton_ne_devrait_rien_rendre_pour_code_inexistant() {
        Optional<Canton> canton = getService().getCanton("XX");

        assertFalse("Le canton est null", canton.isPresent());
    }

    @Test
    public void getCanton_devrait_rendre_un_canton_avec_tous_ses_champs_initialises() {
        Optional<Canton> canton = getService().getCanton("GE");
        assertTrue("Le canton est null", canton.isPresent());

        Canton c = canton.get();
        assertCantonField(c, "cantonAbbreviation", "GE", canton.get().getCantonAbbreviation());
        assertCantonField(c, "cantonLongName", "Genève", canton.get().getCantonLongName());
        assertCantonField(c, "cantonId", 25, canton.get().getCantonId());
    }

    @Test
    public void getDistrict_devrait_rendre_le_district_attendu() {
        Optional<District> district = getService().getDistrict(10279);

        assertTrue("Le district devrait exister", district.isPresent());
    }

    @Test
    public void getDistrict_ne_devrait_rendre_aucun_district_pour_un_identifiant_errone() {
        Optional<District> district = getService().getDistrict(123456789);

        assertFalse("Le district ne devrait pas exister", district.isPresent());
    }

    @Test
    public void getDistrictsByCanton_devrait_rendre_les_districts_avec_leurs_champs_initialises() {
        List<District> districts = getService().getDistrictsByCanton("VD");

        District d = districts.get(0);
        assertDistrictFieldNotNull(d, "districtHistId", d.getDistrictHistId());
        assertDistrictFieldNotNull(d, "cantonId", d.getCantonId());
        assertDistrictFieldNotNull(d, "districtId", d.getDistrictId());
        assertDistrictFieldNotBlank(d, "districtLongName", d.getDistrictLongName());
        assertDistrictFieldNotBlank(d, "districtShortName", d.getDistrictShortName());
        assertDistrictFieldNotNull(d, "districtEntryMode", d.getDistrictEntryMode());
        assertDistrictFieldNotNull(d, "districtAdmissionNumber", d.getDistrictAdmissionNumber());
        assertDistrictFieldNotNull(d, "districtAdmissionMode", d.getDistrictAdmissionMode());
        assertDistrictFieldNotNull(d, "districtAdmissionDate", d.getDistrictAdmissionDate());
        assertDistrictFieldNotNull(d, "districtDateOfChange", d.getDistrictDateOfChange());
        assertDistrictFieldNotNull(d, "canton", d.getCanton());
    }

    @Test
    public void getDistrictsByCanton_devrait_rendre_les_districts_attendus() {
        List<District> districts = getService().getDistrictsByCanton("VD");
        districts.forEach(d -> LOGGER.debug("{}", d));

        assertEquals("Nombre de districts incorrect", 10, districts.size());
    }

    @Test
    public void getMunicipality_devrait_rendre_la_municipalite_attendue() {
        Optional<Municipality> municipality = getService().getMunicipality(4761);

        assertTrue("La municipalite devrait exister", municipality.isPresent());
        assertMunicipality(municipality.get(), "Sirnach");
    }

    @Test
    public void getMunicipalitiesByCanton_devrait_rendre_le_nombre_attendu_de_municipalites() {
        List<Municipality> municipalities = getService().getMunicipalitiesByCanton("GE");

        assertNotNull("La liste ne devrait pas etre nulle", municipalities);
        assertEquals("Le nombre de municipalites est incorrect", 45, municipalities.size());
    }

    @Test
    public void getMunicipalitiesByCanton_devrait_rendre_les_municipalites_avec_leurs_champs_initialises() {
        List<Municipality> municipalities = getService().getMunicipalitiesByCanton("GE");

        Municipality m = municipalities.get(7);
        assertMunicipalityFieldNotNull(m, "historyMunicipalityId", m.getHistoryMunicipalityId());
        assertMunicipalityFieldNotNull(m, "districtHistId", m.getDistrictHistId());
        assertMunicipalityFieldNotBlank(m, "cantonAbbreviation", m.getCantonAbbreviation());
        assertMunicipalityFieldNotNull(m, "municipalityId", m.getMunicipalityId());
        assertMunicipalityFieldNotBlank(m, "municipalityLongName", m.getMunicipalityLongName());
        assertMunicipalityFieldNotNull(m, "municipalityEntryMode", m.getMunicipalityEntryMode());
        assertMunicipalityFieldNotNull(m, "municipalityStatus", m.getMunicipalityStatus());
        assertMunicipalityFieldNotNull(m, "municipalityAdmissionMode", m.getMunicipalityAdmissionMode());
    }

    @Test
    public void getMunicipalitiesByCanton_devrait_rendre_les_municipalites_dans_le_bon_ordre() {
        List<Municipality> municipalities = getService().getMunicipalitiesByCanton("GE");
        Municipality m;

        m = municipalities.get(0);
        assertMunicipality(m, "Aire-la-Ville");

        m = municipalities.get(municipalities.size() - 1);
        assertMunicipality(m, "Veyrier");
    }

    @Test
    public void getMunicipalitiesByCanton_devrait_rendre_les_municipalites_avec_les_noms_nettoyes() {
        Municipality m;

        // on attend "Carouge", pas "Carouge (GE)"
        m = getMunicipalityById("GE", 6608);
        assertMunicipality(m, "Carouge");
        assertMunicipalityField(m, "longName", "Carouge", m.getMunicipalityLongName());

        // on attend "Berg", pas "Berg (TG)"
        m = getMunicipalityById("TG", 4891);
        assertMunicipality(m, "Berg");
        assertMunicipalityField(m, "longName", "Berg", m.getMunicipalityLongName());

        // on attend que "Meyrin" n'ait pas ete modifiee
        m = getMunicipalityById("GE", 6630);
        assertMunicipality(m, "Meyrin");
        assertMunicipalityField(m, "longName", "Meyrin", m.getMunicipalityLongName());
    }

    @Test
    public void searchMunicipality_devrait_rendre_les_municipalites_attendues() {
        List<Municipality> municipalities = getService().searchMunicipality("gen");

        assertEquals("Le nombre de municipalites trouvees est incorrect", 3, municipalities.size());
        assertMunicipality(municipalities.get(0), "Genève");
        assertMunicipality(municipalities.get(1), "Genolier");
        assertMunicipality(municipalities.get(2), "Genthod");
    }

    private ReferentielCommunesService getService() {
        return service;
    }

    private Municipality getMunicipalityById(String codeCanton, int municipalityId)  {
        return getService().getMunicipalitiesByCanton(codeCanton).stream()
                .filter(m -> m.getMunicipalityId() == municipalityId)
                .findFirst()
                .get();
    }

    private void assertCantonField(Canton canton, String champ, Object expected, Object actual) {
        assertEquals("La valeur du champ '" + champ + "' du canton " + canton.getCantonAbbreviation() + " est incorrecte",
                expected, actual);
    }

    private void assertCantonFieldNotBlank(Canton canton, String champ, String actual) {
        assertFalse("La valeur du champ '" + champ + "' du canton " + canton.getCantonAbbreviation() + " est incorrecte",
                StringUtils.isBlank(actual));
    }

    private void assertDistrictFieldNotBlank(District district, String champ, String actual) {
        assertFalse("La valeur du champ '" + champ + "' du district '" + district.getDistrictShortName() + "' est incorrecte",
                StringUtils.isBlank(actual));
    }

    private void assertDistrictFieldNotNull(District district, String champ, Object actual) {
        assertNotNull("La valeur du champ '" + champ + "' du district '" + district.getDistrictShortName() + "' est nulle",
                actual);
    }

    private void assertMunicipality(Municipality municipality, String expectedShortName) {
        assertEquals("La municipalite est incorrecte",
                expectedShortName, municipality.getMunicipalityShortName());
    }

    private void assertMunicipalityField(Municipality municipality, String field, Object expected, Object actual) {
        assertEquals("La valeur du champ '" + field + "' de la municipalite '" + municipality.getMunicipalityShortName() + "' est incorrecte",
                expected, actual);
    }

    private void assertMunicipalityFieldNotBlank(Municipality municipality, String field, String actual) {
        assertFalse("La valeur du champ '" + field + "' de la municipalite '" + municipality.getMunicipalityShortName() + "' est incorrecte",
                StringUtils.isBlank(actual));
    }

    private void assertMunicipalityFieldNotNull(Municipality municipality, String field, Object actual) {
        assertNotNull("La valeur du champ '" + field + "' de la municipalite '" + municipality.getMunicipalityShortName() + "' est nulle",
                actual);
    }

}
