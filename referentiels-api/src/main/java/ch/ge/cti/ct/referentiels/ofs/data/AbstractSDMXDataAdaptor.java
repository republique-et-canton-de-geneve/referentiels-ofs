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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ch.ge.cti.ct.referentiels.ofs.ReferentielOfsException;

/**
 * Classe abstraite définissant le contrat de l'adapteur de données SDMX<br/>
 * L'adapteur a pour objectif de convertir la structure générique SDMX en une
 * structure spécifique au référentiel, pour en optimise l'exploitation
 * 
 * @param <T> type de structure du référentiel
 */
public abstract class AbstractSDMXDataAdaptor<T> {

	/** service de parsing du fichier SDMX */
	private final SDMXParser parser;

	/** logger SLF4j */
	private final Logger log = LoggerFactory.getLogger(getClass());

	/**
	 * Constructeur avec injection du parseur SDMX
	 * 
	 * @param parser parseur
	 */
	protected AbstractSDMXDataAdaptor(final SDMXParser parser) {
		this.parser = parser;
	}

	/**
	 * Exécution du traitement du fichier SDMX
	 * 
	 * @param urlXML url du fichier SDMX
	 * @return INSTANCE du référentiel
	 * @throws ReferentielOfsException erreur de traitement
	 */
	public abstract T parse(final URL urlXML) throws ReferentielOfsException;

	/**
	 * Getter du parseur générique SDMX
	 * 
	 * @return parseur SDMX
	 */
	protected final SDMXParser getParser() {
		return parser;
	}

	/**
	 * Getter du logger
	 * 
	 * @return logger
	 */
	protected final Logger log() {
		return log;
	}
}
