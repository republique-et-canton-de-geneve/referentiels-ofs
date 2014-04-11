package ch.ge.cti.ct.referentiels.professions.data;

import java.net.URL;

import ch.ge.cti.ct.act.configuration.DistributionFactory;
import ch.ge.cti.ct.referentiels.ofs.ReferentielOfsException;
import ch.ge.cti.ct.referentiels.professions.model.ReferentielProfessions;

/**
 * Singleton de stockage de l'instance du ReferentielProfessions <br/>
 * Le singleton permet de ne charger le fichier SDMX qu'une seule fois.
 * 
 * @author desmazieresj
 * 
 */
public enum ReferentielDataSingleton {
    instance;

    /** instance de la classe de lecture du flux XML */
    private final ServiceDataReader reader = new ServiceDataReader();
    /** référentiel instancié */
    private ReferentielProfessions data;

    /**
     * Getter du référentiel
     * 
     * @return référentiel
     * @throws ReferentielOfsException
     *             erreur de traitement
     */
    public ReferentielProfessions getData() throws ReferentielOfsException {
	if (data == null) {
	    loadData();
	}
	return data;
    }

    /**
     * Getter de l'url du fichier XML
     * 
     * @return url du fichier XML
     * @throws ReferentielOfsException
     *             erreur de traitement
     */
    public URL getReferentielFile() throws ReferentielOfsException {
	return reader.getXmlFile();
    }

    /**
     * Chargement du fichier XML
     * 
     * @throws ReferentielOfsException
     *             erreur de traitement
     */
    private void loadData() throws ReferentielOfsException {
	if (data == null) {
	    synchronized (this) {
		DistributionFactory.setDisableJNDI(true);
		data = reader.read();
	    }
	}
    }

}
