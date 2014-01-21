package ch.ge.cti.ct.referentiels.ofs.data;

import java.net.URL;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ch.ge.cti.ct.referentiels.ofs.ReferentielOfsException;

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
	 * @return instance du référentiel
	 * @throws ReferentielOfsException erreur de traitement
	 */
	public abstract T parse(final URL urlXML) throws ReferentielOfsException;

	protected SDMXParser getParser() {
		return parser;
	}

	protected Logger log() {
		return log;
	}
}
