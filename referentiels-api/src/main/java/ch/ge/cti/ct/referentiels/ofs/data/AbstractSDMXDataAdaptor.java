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
