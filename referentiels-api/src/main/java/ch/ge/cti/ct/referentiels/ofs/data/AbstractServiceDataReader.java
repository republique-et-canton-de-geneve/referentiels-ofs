package ch.ge.cti.ct.referentiels.ofs.data;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import ch.ge.cti.ct.act.configuration.DistributionFactory;
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
     * @throws ReferentielOfsException
     *             erreur de traitement
     */
    private URL getURL() throws ReferentielOfsException {
	try {
	    final String filename = DistributionFactory.getConfiguration()
		    .getString(getConfigurationEntry());
	    if (filename == null) {
		throw new ReferentielOfsException(
			"Impossible de trouver l'entrée ["
				+ getConfigurationEntry()
				+ "] dans le fichier Distribution.properties");
	    }
	    return toURL(filename);
	} catch (final IOException ex) {
	    throw new ReferentielOfsException(
		    "Impossible de lire le fichier Distribution.properties", ex);
	}
    }

    /**
     * Convertion en URL supportant le format avec ou sans protocole
     * 
     * @param filename
     *            nom du fichier
     * @return url
     * @throws ReferentielOfsException
     *             erreur de convertion
     */
    private URL toURL(final String filename) throws ReferentielOfsException {
	try {
	    return new URL(filename);
	} catch (final MalformedURLException mue) {
	    try {
		return new File(filename).toURI().toURL();
	    } catch (final MalformedURLException mue2) {
		throw new ReferentielOfsException("La valeur de l'entrée ["
			+ getConfigurationEntry() + "] est invalide: "
			+ filename, mue);
	    }
	}
    }

    /**
     * getter url du fichier référentiel SDMX
     * 
     * @return url du fichier référentiel
     * @throws ReferentielOfsException
     *             erreur de traitement
     */
    public synchronized URL getXmlFile() throws ReferentielOfsException {
	if (xmlFile == null) {
	    xmlFile = getURL();
	}
	return xmlFile;
    }

    /** enumération des données de configuration */
    public static enum Config {
	/** variable d'environnement distribution.properties */
	ENV("distribution.properties"),
	/** chemin par défaut vers le fichier Distribution.properties */
	FILE("/Distribution.properties");
	private final String key;

	private Config(final String key) {
	    this.key = key;
	}

	public String key() {
	    return this.key;
	}
    }
}
