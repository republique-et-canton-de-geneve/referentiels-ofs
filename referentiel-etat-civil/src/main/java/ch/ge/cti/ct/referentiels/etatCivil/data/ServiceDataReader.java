package ch.ge.cti.ct.referentiels.etatCivil.data;

import ch.ge.cti.ct.referentiels.etatCivil.model.ReferentielEtatCivil;
import ch.ge.cti.ct.referentiels.ofs.data.AbstractSDMXDataAdaptor;
import ch.ge.cti.ct.referentiels.ofs.data.AbstractServiceDataReader;

/**
 * classe de lecture du fichier XML de r�f�rentiels des communes
 * 
 * @author desmazieresj
 * 
 */
public class ServiceDataReader extends
	AbstractServiceDataReader<ReferentielEtatCivil> {

    protected AbstractSDMXDataAdaptor<ReferentielEtatCivil> getDataAdaptor() {
	return SDMXDataAdaptor.Factory.getInstance();
    }

    /**
     * retourne le nom de l'entr�e de configuration de la localisation du
     * fichier sdmx<br/>
     * La clef de configuration doit �tre unique pour chaque r�f�rentiel
     * 
     * @return clef de configuration
     */
    protected String getConfigurationEntry() {
	return "referentiel-etat-civil.xml";
    }
}
