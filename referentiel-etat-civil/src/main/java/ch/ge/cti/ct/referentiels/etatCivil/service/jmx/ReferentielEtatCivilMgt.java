package ch.ge.cti.ct.referentiels.etatCivil.service.jmx;

import java.net.URL;

import org.jboss.system.ServiceMBeanSupport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ch.ge.cti.ct.referentiels.etatCivil.data.ReferentielDataSingleton;
import ch.ge.cti.ct.referentiels.etatCivil.interfaces.ws.ReferentielEtatCivilSEI;
import ch.ge.cti.ct.referentiels.ofs.ReferentielOfsException;
import ch.ge.cti.ct.referentiels.ofs.service.jmx.Renderable;
import ch.ge.cti.ct.referentiels.ofs.service.jmx.StatistiquesServiceSingleton;

/**
 * Implémentation du MBean <br/>
 * L'installation du MBean sur le serveur est définie dans le fichier
 * META-INF/jboss-service.xml
 * 
 * @author DESMAZIERESJ
 * 
 */
public class ReferentielEtatCivilMgt extends ServiceMBeanSupport implements
	ReferentielEtatCivilMgtMBean {

    /** logger SLF4J */
    private final Logger log = LoggerFactory.getLogger(getClass());

    /**
     * chargement du référentiel au démarrage du MBean: le référentiel est
     * chargé au démarrage du JBoss
     */
    @Override
    protected void startService() throws Exception { // NOSONAR
	super.startService();
	initialize();
    }

    /**
     * Le MBean est instancié au démarrage du serveur<br/>
     * On utilise donc cet "événement" pour charger le référentiel afin que le
     * premier utilisateur ne soit pas pénalisé
     */
    private void initialize() {
	log.info("Initialisation du référentiel états civils");
	try {
	    ReferentielDataSingleton.instance.getData();
	} catch (final ReferentielOfsException e) {
	    log.error("Erreur de chargement du référentiel états civils: {}", e);
	}
    }

    @Override
    public URL getReferentielFile() throws ReferentielOfsException {
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
		    + "' dans l'appel de la méthode displayStatistiques n'est pas valide (XML, HTML)");
	}
	return mode.render();
    }

    @Override
    public void resetStatistiques() {
	StatistiquesServiceSingleton.instance
		.reset(ReferentielEtatCivilSEI.class);
    }
}
