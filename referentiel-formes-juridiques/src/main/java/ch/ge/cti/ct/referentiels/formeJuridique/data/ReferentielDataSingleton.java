package ch.ge.cti.ct.referentiels.formeJuridique.data;

import java.net.URL;

import ch.ge.cti.ct.referentiels.formeJuridique.model.ReferentielFormesJuridiques;
import ch.ge.cti.ct.referentiels.ofs.ReferentielOfsException;

/**
 * Singleton de stockage de l'INSTANCE du ReferentielFormesJuridiques <br/>
 * Le singleton permet de ne charger le fichier SDMX qu'une seule fois.
 * 
 * @author desmazieresj
 * 
 */
public enum ReferentielDataSingleton {

    INSTANCE;

    /** INSTANCE de la classe de lecture du flux XML */
    private final ServiceDataReader reader = new ServiceDataReader();
    /** référentiel instancié */
    private ReferentielFormesJuridiques data;

    /**
     * Getter du référentiel
     * 
     * @return référentiel
     * @throws ReferentielOfsException
     *             erreur de traitement
     */
    public ReferentielFormesJuridiques getData() throws ReferentielOfsException {
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
		data = reader.read();
	    }
	}
    }

}
