package ch.ge.cti.ct.referentiels.ofs.service.jmx;

import javax.interceptor.AroundInvoke;
import javax.interceptor.InvocationContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Intercepteur des appels afin d'alimenter le MBean de statistiques
 * 
 * @author DESMAZIERESJ
 * 
 */
public class ReferentielStatsIntercept {
    /** logger SLF4J */
    private final Logger log = LoggerFactory.getLogger(getClass());

    /**
     * interception de toutes les méthodes pour enregistrement de l'appel avec
     * le temps d'exécution en nanosecondes
     */
    @AroundInvoke
    public Object processStats(InvocationContext ctx) throws Exception {
	final long start = System.nanoTime();
	try {
	    return ctx.proceed();
	} finally {
	    try {
		StatistiquesServiceSingleton.instance.registerCall(ctx
			.getTarget().getClass(), ctx.getMethod(), ctx
			.getParameters(), System.nanoTime() - start);
	    } catch (Exception e) {
		log.warn("Erreur lors de l'enregistrement des statistiques", e);
	    }
	}
    }
}
