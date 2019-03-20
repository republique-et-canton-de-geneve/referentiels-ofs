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
package ch.ge.cti.ct.referentiels.pays.service.impl;

import ch.ge.cti.ct.referentiels.pays.data.Countries;
import ch.ge.cti.ct.referentiels.pays.data.Country;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;
import org.apache.commons.lang.StringUtils;

import java.io.InputStream;

/**
 * Charge la liste de pays depuis le fichier XML de l'OFS.
 * Corrige certaines imperfections du fichier XML, comme des accents manquants ou des codes ISO manquants.
 * La liste rendue contient encore plein de trucs inutiles, comme les Iles Feroe ou les Terres australes
 * et antarctiques francaises.
 * 
 * @author Yves Dubois-Pelerin
 */
class CountriesLoader {

    private static final String XML_FILE = "eCH0072_190321.xml";

    private Countries countries = null;

    Countries load() {
        loadCountries();
        fixCountries();
        return countries;
    }

    /**
     * En utilisant XStream, lit le fichier XML fourni par l'OFS et charge son contenu.
     */
    private void loadCountries() {
        // 1. configuration de XStream
        XStream xStream = new XStream(new DomDriver());
        xStream.alias("eCH-0072:countries", Countries.class);
        xStream.alias("country", Country.class);
        xStream.addImplicitCollection(Countries.class, "countryList");
        xStream.ignoreUnknownElements();
        // ceci est pour supprimer la trace 'Security framework of XStream not initialized, XStream is probably vulnerable.'
        xStream.allowTypesByRegExp(new String[] { ".*" });

        // 2. conversion XML -> Java
        InputStream is = getClass().getResourceAsStream("/" + XML_FILE);
        Object obj = xStream.fromXML(is);
        countries = (Countries)obj;
    }

    /**
     * Corrige la liste brute des pays obtenue par dÈsÈrialisation du fichier XML de l'OFS.
     */
    private void fixCountries() {
        fixKosovo();
        fixPalestine();
        fixShortName("BrunÈi Darussalam", "BrunÈi");
        fixShortName("BÈlarus", "BiÈlorussie");
        fixShortName("Bosnie et HerzÈgovine", "Bosnie-HerzÈgovine");
        fixShortName("Cabo Verde", "Cap-Vert");
        fixShortName("CitÈ du Vatican", "Vatican");
        fixShortName("CorÈe (Nord)", "CorÈe du Nord");
        fixShortName("CorÈe (Sud)", "CorÈe du Sud");
        fixShortName("El Salvador", "Salvador");
        fixShortName("Emirats arabes unis", "…mirats arabes unis");
        fixShortName("Eswatini", "Swaziland");
        fixShortName("Moldova", "Moldavie");
        fixShortName("Myanmar", "Birmanie");
        fixShortName("Saint-Vincent-et-les Grenadines", "Saint-Vincent-et-les-Grenadines");
        fixShortName("TaÔwan (Taipei chinois)", "TaÔwan");
        fixShortName("Timor-Leste", "Timor oriental");
    }

    private void fixKosovo() {
        Country c = getCountry("Kosovo");

        if (StringUtils.isBlank(c.getIso2Id())) {
            c.setIso2Id("XK");
        }

        if (StringUtils.isBlank(c.getIso3Id())) {
            c.setIso3Id("XKX");
        }
    }

    private void fixPalestine() {
        Country c = getCountry("Palestine");

        if (StringUtils.isBlank(c.getOfficialNameFr())) {
            c.setOfficialNameFr("…tat de Palestine");
        }
    }

    private void fixShortName(String oldName, String newName) {
        getCountry(oldName).setShortNameFr(newName);
    }

    private Country getCountry(String name) {
        return countries.countryList.stream()
                .filter(c -> c.getShortNameFr().equals(name))
                .findFirst()
                .get();
    }

}
