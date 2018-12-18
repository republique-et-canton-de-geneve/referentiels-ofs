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
package ch.ge.cti.ct.referentiels.ofs.data;

import java.net.URL;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ch.ge.cti.ct.referentiels.ofs.ReferentielOfsException;

/**
 * Classe abstraite d�finissant le contrat de l'adapteur de donn�es SDMX<br/>
 * L'adapteur a pour objectif de convertir la structure g�n�rique SDMX en une
 * structure sp�cifique au r�f�rentiel, pour en optimise l'exploitation
 * 
 * @param <T> type de structure du r�f�rentiel
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
	 * Ex�cution du traitement du fichier SDMX
	 * 
	 * @param urlXML url du fichier SDMX
	 * @return INSTANCE du r�f�rentiel
	 * @throws ReferentielOfsException erreur de traitement
	 */
	public abstract T parse(final URL urlXML) throws ReferentielOfsException;

	/**
	 * Getter du parseur g�n�rique SDMX
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
