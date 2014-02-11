package ch.ge.cti.ct.referentiels.etatCivil.data;

import java.net.URL;

import ch.ge.cti.ct.act.configuration.DistributionFactory;
import ch.ge.cti.ct.referentiels.etatCivil.model.ReferentielEtatCivil;
import ch.ge.cti.ct.referentiels.ofs.ReferentielOfsException;

/**
 * Singleton de stockage de l'instance du ReferentielEtatCivil <br/>
 * Le singleton permet de ne charger le fichier des communes qu'une seule fois.
 * 
 * @author desmazieresj
 * 
 */
public enum ReferentielDataSingleton {
    instance;

    /** instance de la classe de lecture du flux XML */
    private final ServiceDataReader reader = new ServiceDataReader();
    /** r�f�rentiel instanci� */
    private ReferentielEtatCivil data;

    /**
     * Getter du r�f�rentiel
     * 
     * @return r�f�rentiel
     * @throws ReferentielOfsException
     *             erreur de traitement
     */
    public ReferentielEtatCivil getData() throws ReferentielOfsException {
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
    private synchronized void loadData() throws ReferentielOfsException {
	if (data == null) {
	    DistributionFactory.setDisableJNDI(true);
	    data = reader.read();
	}
    }

}