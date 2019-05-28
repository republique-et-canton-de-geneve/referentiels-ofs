/*
 * Referentiels OFS
 *
 * Copyright (C) 2018 RÃ©publique et canton de GenÃ¨ve
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
import ch.ge.cti.ct.referentiels.communes.data.Nomenclature;
import ch.ge.cti.ct.referentiels.communes.service.ReferentielCommunesService;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.Collator;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Implémentation du service.
 *
 * @author desmazieresj
 */
//@Component
public class ReferentielCommunesServiceImpl implements ReferentielCommunesService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ReferentielCommunesServiceImpl.class);

    /**
     * Liste corrigee des cantons.
     */
    private List<Canton> cantons;

    /**
     * Liste des districts.
     */
    private List<District> districts;

    /**
     * Liste corrigee des municipalites.
     */
    private List<Municipality> municipalities;

    public ReferentielCommunesServiceImpl() {
        initializeData();
    }

    @Override
    public List<Canton> getCantons() {
        return cantons;
    }

    @Override
    public Optional<Canton> getCanton(String codeCanton) {
        return getCantons().stream()
                .filter(c -> c.getCantonAbbreviation().equalsIgnoreCase(codeCanton))
                .findFirst();
    }

    @Override
    public List<District> getDistrictsByCanton(String codeCanton) {
        return districts.stream()
                .filter(d -> d.getCanton().getCantonAbbreviation().equals(codeCanton))
                .filter(d -> d.getDistrictAbolitionDate() == null)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<District> getDistrict(int districtHistId) {
        return districts.stream()
                .filter(d -> d.getDistrictHistId() == districtHistId)
                .findFirst();
    }

    @Override
    public List<Municipality> getMunicipalitiesByCanton(String codeCanton) {
        return municipalities.stream()
                .filter(m -> m.getCantonAbbreviation().equals(codeCanton))
                .collect(Collectors.toList());
    }

    @Override
    public Optional<Municipality> getMunicipality(int municipalityId) {
        return municipalities.stream()
                .filter(m -> m.getMunicipalityId() == municipalityId)
                .findFirst();
    }

    @Override
    public List<Municipality> searchMunicipality(String critere) {
        if (StringUtils.isBlank(critere)) {
            return new ArrayList<>();
        } else {
            String critereLower = critere.toLowerCase(Locale.FRENCH);
            return municipalities.stream()
                    .filter(m -> m.getMunicipalityLongName().toLowerCase(Locale.FRENCH).startsWith(critereLower))
                    .collect(Collectors.toList());
        }
    }

    private void initializeData() {
        LOGGER.info("Initialisation des donnees");

        // chargement depuis le fichier XML + corrections des données brutes
        Nomenclature nomenclature = new NomenclatureLoader().load();

        // initialisation des listes
        cantons = new ArrayList<>(nomenclature.getCantons().getCantonList());
        districts = new ArrayList<>(nomenclature.getDistricts().getDistrictList());
        municipalities = new ArrayList<>(nomenclature.getMunicipalities().getMunicipalityList());

        // suppression des districts non pertinents (ex : en Vaud, le district "Région sans district Vaud")
        final int DISTRICT = 15;
        districts = districts.stream()
                .filter(d -> d.getDistrictEntryMode() == DISTRICT)
                .collect(Collectors.toList());

        // pour chaque district, initialisation de l'objet Canton à partir du cantonId
        Map<Integer, Canton> cantonByCantonIdMap = nomenclature.getCantons().getCantonList().stream()
                .collect(Collectors.toMap(Canton::getCantonId, Function.identity()));
        districts
                .forEach(d -> d.setCanton(cantonByCantonIdMap.get(d.getCantonId())));

        // traduction en francais des noms des cantons
        translateCantonNames(cantons);

        // tri de la liste des cantons
        cantons.sort((c1, c2) -> {
            Collator collator = Collator.getInstance(Locale.FRENCH);
            return collator.compare(c1.getCantonLongName(), c2.getCantonLongName());
        });

        // suppression des municipalites non pertinentes, cf. explications
        // dans le document https://www.bfs.admin.ch/bfsstatic/dam/assets/4062823/master de l'OFS
        final int COMMUNE = 11;
        municipalities = municipalities.stream()
                .filter(m -> m.getMunicipalityEntryMode() == COMMUNE)  // pour exclure par ex. la municipalite "Lac Léman (GE)" à Geneve
                .filter(m -> m.getMunicipalityAbolitionMode() == null)
                .collect(Collectors.toList());

        // suppression des noms des cantons dans les noms des municipalites (par ex., "Carouge (GE)" -> "Carouge")
        cleanUpMunicipalityNames(municipalities);

        // tri de la liste des municipalites
        municipalities.sort((m1, m2) -> {
            Collator collator = Collator.getInstance(Locale.FRENCH);
            return collator.compare(m1.getMunicipalityLongName(), m2.getMunicipalityLongName());
        });

        LOGGER.info("Initialisation des donnees terminee");
    }

    private void translateCantonNames(List<Canton> cantons) {
        Map<String, Canton> map = new HashMap<>();
        for (Canton c : cantons) {
            map.put(c.getCantonAbbreviation(), c);
        }

        map.get("AG").setCantonLongName("Argovie");
        map.get("AI").setCantonLongName("Appenzell Rhodes-Intérieures");
        map.get("AR").setCantonLongName("Appenzell Rhodes-Extérieures");
        map.get("BL").setCantonLongName("Bâle-Campagne");
        map.get("BS").setCantonLongName("Bâle-Ville");
        map.get("BE").setCantonLongName("Berne");
        map.get("FR").setCantonLongName("Fribourg");
        map.get("GL").setCantonLongName("Glaris");
        map.get("GR").setCantonLongName("Grisons");
        map.get("LU").setCantonLongName("Lucerne");
        map.get("OW").setCantonLongName("Obwald");
        map.get("NW").setCantonLongName("Nidwald");
        map.get("SG").setCantonLongName("Saint-Gall");
        map.get("SH").setCantonLongName("Schaffhouse");
        map.get("SO").setCantonLongName("Soleure");
        map.get("SZ").setCantonLongName("Schwytz");
        map.get("TG").setCantonLongName("Thurgovie");
        map.get("TI").setCantonLongName("Tessin");
        map.get("VS").setCantonLongName("Valais");
        map.get("ZG").setCantonLongName("Zoug");
        map.get("ZH").setCantonLongName("Zurich");
    }

    private void cleanUpMunicipalityNames(List<Municipality> municipalities) {
        for (Municipality m : municipalities) {
            String canton = m.getCantonAbbreviation();

            final int EXTENSION_SIZE = 5;

            String name = m.getMunicipalityShortName();
            if (name.contains("(" + canton + ")")) {
                m.setMunicipalityShortName(name.substring(0, name.length() - EXTENSION_SIZE));
            }

            name = m.getMunicipalityLongName();
            if (name.contains("(" + canton + ")")) {
                m.setMunicipalityLongName(name.substring(0, name.length() - EXTENSION_SIZE));
            }
        }
    }

}
