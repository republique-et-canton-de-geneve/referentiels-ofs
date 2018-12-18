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
package ch.ge.cti.ct.referentiels.ofs.data;

import java.net.URL;

import ch.ge.cti.ct.referentiels.ofs.ReferentielOfsException;

/**
 * classe de lecture du fichier XML de référentiels des communes
 * 
 * @author desmazieresj
 * 
 */
public abstract class AbstractServiceDataReader<T> {

    /** url du fichier xml */
    private URL xmlFile = null;

    /**
     * retourne le nom de l'entrée de configuration de la localisation du
     * fichier sdmx<br/>
     * La clef de configuration doit être unique pour chaque référentiel
     * 
     * @return clef de configuration
     */
    protected abstract String getConfigurationEntry();

    /**
     * retourne l'adapteur de données SDMX spécifique au fichier de données
     * 
     * @return SDMX data adaptor
     */
    protected abstract AbstractSDMXDataAdaptor<T> getDataAdaptor();

    /**
     * Chargement du fichier référentiel<br/>
     * <ol>
     * <li>recherche du fichier référentiel</li>
     * <li>lecture du fichier</li>
     * <li>parsing du flux XML</li>
     * </ol>
     * 
     * @return ReferentielCommunes
     * @throws ReferentielOfsException
     *             erreur de chargement
     */
    public T read() throws ReferentielOfsException {
	return parse(getXmlFile());
    }

    /**
     * Lecture & parsing du fichier XML
     * 
     * @param urlXML
     *            url du fichier à lire
     * @return ReferentielCommunes référentiel
     * @throws ReferentielOfsException
     *             erreur de chargement
     */
    protected T parse(final URL urlXML) throws ReferentielOfsException {
	return getDataAdaptor().parse(urlXML);
    }

    /**
     * Chemin du fichier XML
     * 
     * @return url du fichier XML
     */
    private URL getURL() {
	    final String filename = getConfigurationEntry();
	    return getClass().getClassLoader().getResource(filename);
    }

    /**
     * getter url du fichier référentiel SDMX
     * 
     * @return url du fichier référentiel
     */
    public synchronized URL getXmlFile() {
	if (xmlFile == null) {
	    xmlFile = getURL();
	}
	return xmlFile;
    }
}
