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
package ch.ge.cti.ct.referentiels.ofs;

import javax.xml.ws.WebFault;

/**
 * Exception sp�cifique du service
 * 
 * @author DESMAZIERESJ
 * 
 */
@WebFault
public class ReferentielOfsException extends Exception {

	/** serialVersionUID */
	private static final long serialVersionUID = -5915412385331078873L;

	/**
	 * Constructeur
	 */
	public ReferentielOfsException() {
		super();
	}

	/**
	 * Constructeur
	 * 
	 * @param message message d'erreur
	 * @param cause exception originale
	 */
	public ReferentielOfsException(final String message,
			final Throwable cause) {
		super(message, cause);
	}

	/**
	 * Constructeur
	 * 
	 * @param message message d'erreur
	 */
	public ReferentielOfsException(final String message) {
		super(message);
	}

	/**
	 * Constructeur
	 * 
	 * @param cause exception originale
	 */
	public ReferentielOfsException(final Throwable cause) {
		super(cause);
	}

}
