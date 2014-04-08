package ch.ge.cti.ct.referentiels.socioprofessionnel.data;

import ch.ge.cti.ct.referentiels.ofs.data.AbstractSDMXDataAdaptor;
import ch.ge.cti.ct.referentiels.ofs.data.AbstractServiceDataReader;
import ch.ge.cti.ct.referentiels.socioprofessionnel.model.ReferentielSocioprofessionnel;

/**
 * classe de lecture du fichier XML de référentiels des communes
 * 
 * @author desmazieresj
 * 
 */
public class ServiceDataReader extends
	AbstractServiceDataReader<ReferentielSocioprofessionnel> {

    @Override
    protected AbstractSDMXDataAdaptor<ReferentielSocioprofessionnel> getDataAdaptor() {
	return SDMXDataAdaptor.Factory.getInstance();
    }

    /**
     * retourne le nom de l'entrée de configuration de la localisation du
     * fichier sdmx<br/>
     * La clef de configuration doit être unique pour chaque référentiel
     * 
     * @return clef de configuration
     */
    @Override
    protected String getConfigurationEntry() {
	return "referentiel.socioprofessionnel.file";
    }
}
