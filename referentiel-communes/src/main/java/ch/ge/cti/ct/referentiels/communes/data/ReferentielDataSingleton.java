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
package ch.ge.cti.ct.referentiels.communes.data;

import java.net.URL;

import ch.ge.cti.ct.referentiels.communes.model.ReferentielCommunes;
import ch.ge.cti.ct.referentiels.ofs.ReferentielOfsException;

/**
 * Singleton de stockage de l'INSTANCE du ReferentielCommunes <br/>
 * Le singleton permet de ne charger le fichier SDMX qu'une seule fois.
 * 
 * @author desmazieresj
 * 
 */
public enum ReferentielDataSingleton {

    INSTANCE;

    /** INSTANCE de la classe de lecture du flux XML */
    private final ServiceDataReader reader = new ServiceDataReader();
    /** référentiel instancié */
    private ReferentielCommunes data;

    /**
     * Getter du référentiel
     * 
     * @return référentiel
     * @throws ReferentielOfsException
     *             erreur de traitement
     */
    public ReferentielCommunes getData() throws ReferentielOfsException {
	if (data == null) {
	    loadData();
	}
	return data;
    }

    /**
     * Getter de l'url du fichier XML
     * 
     * @return url du fichier XML
     * @throws ReferentielOfsException
     *             erreur de traitement
     */
    public URL getReferentielFile() throws ReferentielOfsException {
	return reader.getXmlFile();
    }

    /**
     * Chargement du fichier XML
     * 
     * @throws ReferentielOfsException
     *             erreur de traitement
     */
    private void loadData() throws ReferentielOfsException {
	if (data == null) {
	    synchronized (this) {
		data = reader.read();
	    }
	}
    }

}
