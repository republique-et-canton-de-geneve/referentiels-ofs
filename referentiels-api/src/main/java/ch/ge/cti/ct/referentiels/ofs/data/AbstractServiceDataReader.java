package ch.ge.cti.ct.referentiels.ofs.data;

import java.net.URL;

import ch.ge.cti.ct.referentiels.ofs.ReferentielOfsException;

/**
 * classe de lecture du fichier XML de r�f�rentiels des communes
 * 
 * @author desmazieresj
 * 
 */
public abstract class AbstractServiceDataReader<T> {

    /** url du fichier xml */
    private URL xmlFile = null;

    /**
     * retourne le nom de l'entr�e de configuration de la localisation du
     * fichier sdmx<br/>
     * La clef de configuration doit �tre unique pour chaque r�f�rentiel
     * 
     * @return clef de configuration
     */
    protected abstract String getConfigurationEntry();

    /**
     * retourne l'adapteur de donn�es SDMX sp�cifique au fichier de donn�es
     * 
     * @return SDMX data adaptor
     */
    protected abstract AbstractSDMXDataAdaptor<T> getDataAdaptor();

    /**
     * Chargement du fichier r�f�rentiel<br/>
     * <ol>
     * <li>recherche du fichier r�f�rentiel</li>
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
     *            url du fichier � lire
     * @return ReferentielCommunes r�f�rentiel
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
     */
    private URL getURL() {
	    final String filename = getConfigurationEntry();
	    return getClass().getClassLoader().getResource(filename);
    }

    /**
     * getter url du fichier r�f�rentiel SDMX
     * 
     * @return url du fichier r�f�rentiel
     */
    public synchronized URL getXmlFile() {
	if (xmlFile == null) {
	    xmlFile = getURL();
	}
	return xmlFile;
    }
}
