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
package ch.ge.cti.ct.referentiels.communes.service.impl;

import ch.ge.cti.ct.referentiels.communes.data.Canton;
import ch.ge.cti.ct.referentiels.communes.data.Cantons;
import ch.ge.cti.ct.referentiels.communes.data.District;
import ch.ge.cti.ct.referentiels.communes.data.Districts;
import ch.ge.cti.ct.referentiels.communes.data.Municipalities;
import ch.ge.cti.ct.referentiels.communes.data.Municipality;
import ch.ge.cti.ct.referentiels.communes.data.Nomenclature;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.converters.basic.DateConverter;
import com.thoughtworks.xstream.io.xml.DomDriver;

import java.io.InputStream;

/**
 * Charge les listes suivantes :
 * <ul>
 *     <li>liste des cantons</li>
 *     <li>liste des districts</li>
 *     <li>liste des communes (municipalites)</li>
 * </ul>
 * depuis le
 * <a href="https://www.bfs.admin.ch/bfs/fr/home/bases-statistiques/repertoire-officiel-communes-suisse/liste-historisee-communes.assetdetail.6986907.html">
 * fichier XML de l'OFS</a>.
 * <br/>
 * Corrige certaines imperfections du fichier XML, comme des noms de communes postfix�es par le canton.
 *
 * @author Yves Dubois-Pelerin
 */
class NomenclatureLoader {

    private static final String XML_FILE = "eCH0071_190101.xml";

    Nomenclature load() {
        return loadXml();
    }

    /**
     * En utilisant XStream, lit le fichier XML fourni par l'OFS et charge son contenu.
     */
    private Nomenclature loadXml() {
        // 1. configuration de XStream
        XStream xStream = new XStream(new DomDriver());
        xStream.alias("eCH-0071:nomenclature", Nomenclature.class);
        xStream.alias("cantons", Cantons.class);
        xStream.alias("canton", Canton.class);
        xStream.alias("districts", Districts.class);
        xStream.alias("district", District.class);
        xStream.alias("municipalities", Municipalities.class);
        xStream.alias("municipality", Municipality.class);
        xStream.addImplicitCollection(Cantons.class, "cantonList");
        xStream.addImplicitCollection(Districts.class, "districtList");
        xStream.addImplicitCollection(Municipalities.class, "municipalityList");
        xStream.registerConverter(new DateConverter("yyyy-MM-dd", null));
        xStream.ignoreUnknownElements();
        // ceci est pour supprimer la trace 'Security framework of XStream not initialized, XStream is probably vulnerable.'
        xStream.allowTypesByRegExp(new String[] { ".*" });

        // 2. conversion XML -> Java
        InputStream is = getClass().getResourceAsStream("/" + XML_FILE);
        Object obj = xStream.fromXML(is);
        return (Nomenclature)obj;
    }

}
