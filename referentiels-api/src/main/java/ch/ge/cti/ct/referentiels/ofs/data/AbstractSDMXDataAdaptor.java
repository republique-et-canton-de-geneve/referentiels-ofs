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
