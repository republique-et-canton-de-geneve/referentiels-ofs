package ch.ge.cti.ct.referentiels.communes.service.jmx;

import java.net.URI;

import org.jboss.system.ServiceMBeanSupport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ch.ge.cti.ct.referentiels.communes.data.ReferentielDataSingleton;
import ch.ge.cti.ct.referentiels.communes.interfaces.ws.ReferentielCommunesSEI;
import ch.ge.cti.ct.referentiels.ofs.ReferentielOfsException;
import ch.ge.cti.ct.referentiels.ofs.service.jmx.Renderable;
import ch.ge.cti.ct.referentiels.ofs.service.jmx.StatistiquesServiceSingleton;

/**
 * Impl�mentation du MBean <br/>
 * L'installation du MBean sur le serveur est d�finie dans le fichier
 * META-INF/jboss-service.xml
 * 
 * @author DESMAZIERESJ
 * 
 */
public class ReferentielCommunesMgt extends ServiceMBeanSupport implements
	ReferentielCommunesMgtMBean {

    /** logger SLF4J */
    private final Logger log = LoggerFactory.getLogger(getClass());

    /**
     * chargement du r�f�rentiel au d�marrage du MBean: le r�f�rentiel est
     * charg� au d�marrage du JBoss
     */
    @Override
    protected void startService() throws Exception { // NOSONAR
	super.startService();
	initialize();
    }

    /**
     * Le MBean est instanci� au d�marrage du serveur<br/>
     * On utilise donc cet "�v�nement" pour charger le r�f�rentiel afin que le
     * premier utilisateur ne soit pas p�nalis�
     */
    private void initialize() {
	log.info("Initialisation du r�f�rentiel communes");
	try {
	    ReferentielDataSingleton.instance.getData();
	} catch (final ReferentielOfsException e) {
	    log.error("Erreur de chargement du r�f�rentiel communes: {}", e);
	}
    }

    @Override
    public URI getReferentielFile() throws ReferentielOfsException {
	log.info(getClass().getName() + ": getReferentielFile()");
	return ReferentielDataSingleton.instance.getReferentielFile();
    }

    @Override
    public String displayStatitiques(final String modeDisplay) {
	Renderable mode = DisplayMode.XML;
	try {
	    mode = DisplayMode.valueOf(modeDisplay);
	} catch (final IllegalArgumentException ie) {
	    log.warn("La valeur '"
		    + modeDisplay
		    + "' dans l'appel de la m�thode displayStatitiques n'est pas valide (XML, HTML)");
	}
	return mode.render();
    }

    @Override
    public void resetStatistiques() {
	StatistiquesServiceSingleton.instance
		.reset(ReferentielCommunesSEI.class);
    }
}
