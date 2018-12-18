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
package ch.ge.cti.ct.referentiels.professions.data;

import ch.ge.cti.ct.referentiels.ofs.data.AbstractSDMXDataAdaptor;
import ch.ge.cti.ct.referentiels.ofs.data.AbstractServiceDataReader;
import ch.ge.cti.ct.referentiels.professions.model.ReferentielProfessions;

/**
 * classe de lecture du fichier XML de référentiels des communes
 * 
 * @author desmazieresj
 * 
 */
public class ServiceDataReader extends
	AbstractServiceDataReader<ReferentielProfessions> {

    protected AbstractSDMXDataAdaptor<ReferentielProfessions> getDataAdaptor() {
	return SDMXDataAdaptor.Factory.getInstance();
    }

    /**
     * retourne le nom de l'entrée de configuration de la localisation du
     * fichier sdmx<br/>
     * La clef de configuration doit être unique pour chaque référentiel
     * 
     * @return clef de configuration
     */
    protected String getConfigurationEntry() {
	return "referentiel-professions.xml";
    }
}
