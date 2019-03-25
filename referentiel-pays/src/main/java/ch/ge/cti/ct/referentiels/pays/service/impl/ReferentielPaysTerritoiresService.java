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
package ch.ge.cti.ct.referentiels.pays.service.impl;

import ch.ge.cti.ct.referentiels.pays.data.Countries;
import ch.ge.cti.ct.referentiels.pays.data.Country;
import ch.ge.cti.ct.referentiels.pays.service.ReferentielPaysTerritoiresServiceAble;
import org.apache.commons.lang.StringUtils;

import java.text.Collator;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Implémentation du service.
 * 
 * @author desmazieresj
 * @author Yves Dubois-Pelerin
 * 
 */
public class ReferentielPaysTerritoiresService implements ReferentielPaysTerritoiresServiceAble {

    /**
     * Liste des pays, expurgee et completee.
     */
    private List<Country> pays;

    public ReferentielPaysTerritoiresService() {
        initialiseListOfCountries();
    }

    @Override
    public List<Country> getPays() {
        return pays;
    }

    @Override
    public Optional<Country> getPaysByIso2(String iso2) {
        return getPays().stream()
                .filter(c -> c.getIso2Id().equals(iso2))
                .findFirst();
    }

    @Override
    public Optional<Country> getPaysByIso3(String iso3) {
        return getPays().stream()
                .filter(c -> c.getIso3Id().equals(iso3))
                .findFirst();
    }

    @Override
    public List<Country> searchPays(String critere) {
        if (StringUtils.isBlank(critere)) {
            return new ArrayList<>();
        }

        return getPays().stream()
                .filter(c -> compliesWith(c, critere))
                .collect(Collectors.toList());
    }

    private void initialiseListOfCountries() {
        // chargement depuis le fichier XML + corrections des données brutes
        Countries countries = new CountriesLoader().load();

        // quelques pays vont être supprimés en trop dans le filtre plus bas
        List<Country> manquants = countries.getCountryList().stream()
                .filter(c -> c.getShortNameFr().equals("Suisse")
                        || c.getShortNameFr().equals("Palestine")
                        || c.getShortNameFr().contains("Taïwan"))
                .collect(Collectors.toList());

        // filtrer la liste brute
        pays = countries.getCountryList().stream()
                .filter(Country::isState)
                .filter(Country::isEntryValid)
                .filter(Country::isRecognizedCh)  // ce filtre-ci supprime la Palestine, Taiwan et... la Suisse
                .collect(Collectors.toList());

        // remettre les manquants
        pays.addAll(manquants);

        // trier, en evitant que les accents interferent
        pays.sort((c1, c2) -> {
            Collator collator = Collator.getInstance(Locale.FRENCH);
            return collator.compare(c1.getShortNameFr(), c2.getShortNameFr());
        });
    }

    /**
     * Rend true si le nom du pays commence par le critere.
     * Les accents sont ignores. La casse est ignoree.
     * <p/>
     * Exemples:
     * <ul>
     *     <li>(Bénin, "be") rend true</li>
     *     <li>(Belgique, "be") rend true</li>
     *     <li>(Belgique, "bé") rend true</li>
     *     <li>(Belgique, "elgique") rend false</li>
     * </ul>
     */
    private boolean compliesWith(Country c, String critere) {
        String name = c.getShortNameFr();
        String crit = critere.trim();

        if (name.length() < crit.length()) {
            return false;
        }

        String nameToCompare = name.toLowerCase(Locale.FRENCH).substring(0, crit.length());
        String critToCompare = crit.toLowerCase(Locale.FRENCH);

        Collator collator = Collator.getInstance();
        collator.setStrength(Collator.PRIMARY);
        return collator.equals(nameToCompare, critToCompare);
    }

}
