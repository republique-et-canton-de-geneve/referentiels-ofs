package ch.ge.cti.ct.referentiels.pays.service.jmx;

import java.net.URL;

import org.jboss.system.ServiceMBeanSupport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ch.ge.cti.ct.referentiels.ofs.ReferentielOfsException;
import ch.ge.cti.ct.referentiels.ofs.service.jmx.StatistiquesServiceSingleton;
import ch.ge.cti.ct.referentiels.pays.data.ReferentielDataSingleton;

/**
 * Impl�mentation du MBean <br/>
 * L'installation du MBean sur le serveur est d�finie dans le fichier
 * META-INF/jboss-service.xml
 * 
 * @author DESMAZIERESJ
 * 
 */
public class ReferentielPaysTerritoiresMgt extends ServiceMBeanSupport implements
		ReferentielPaysTerritoiresMgtMBean {

	/** logger SLF4J */
	private final Logger log = LoggerFactory.getLogger(getClass());

	@Override
	public URL getReferentielFile() throws ReferentielOfsException {
		log.info(getClass().getName() + ": getReferentielFile()");
		return ReferentielDataSingleton.instance.getReferentielFile();
	}

	@Override
	public String displayStatitiques(final String modeDisplay) {
		DisplayMode mode = DisplayMode.XML;
		try {
			mode = DisplayMode.valueOf(modeDisplay);
		} catch (IllegalArgumentException ie) {
			log.warn("La valeur '"
					+ modeDisplay
					+ "' dans l'appel de la m�thode displayStatitiques n'est pas valide (XML, HTML)");
		}
		return mode.render();
	}

	@Override
	public void resetStatistiques() {
		StatistiquesServiceSingleton.instance.reset();
	}
}
