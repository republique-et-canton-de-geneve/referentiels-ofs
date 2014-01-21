package ch.ge.cti.ct.referentiels.ofs;

import javax.xml.ws.WebFault;

/**
 * Exception spécifique du service
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
