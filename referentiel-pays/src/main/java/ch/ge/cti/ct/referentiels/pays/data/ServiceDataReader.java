package ch.ge.cti.ct.referentiels.pays.data;

import ch.ge.cti.ct.referentiels.ofs.data.AbstractSDMXDataAdaptor;
import ch.ge.cti.ct.referentiels.ofs.data.AbstractServiceDataReader;
import ch.ge.cti.ct.referentiels.pays.model.ReferentielPaysTerritoires;

/**
 * classe de lecture du fichier XML de référentiels des communes
 * 
 * @author desmazieresj
 * 
 */
public class ServiceDataReader extends
		AbstractServiceDataReader<ReferentielPaysTerritoires> {

	protected AbstractSDMXDataAdaptor<ReferentielPaysTerritoires> getDataAdaptor() {
		return SDMXDataAdaptor.Factory.getInstance();
	}

	/**
	 * retourne le nom de l'entrée de configuration de la localisation du
	 * fichier sdmx<br/>
	 * La clef de configuration doit être unique pour chaque référentiel
	 * 
	 * @return clef de configuration
	 */
	protected String getConfigurationEntry() {
		return "referentiel.pays.file";
	}
}
