package ch.ge.cti.ct.referentiels.ofs;

import javax.interceptor.AroundInvoke;
import javax.interceptor.InvocationContext;

/**
 * Intercepteur des appels afin de gérer les exceptions sur les méthodes<br/>
 * 
 * @author DESMAZIERESJ
 */
public class ReferentielOfsExceptionIntercept {

    /**
     * interception de toutes les méthodes pour gestion du cache
     * 
     * @param ctx
     *            contexte d'appel
     * @return résultat du traitement
     * @throws Exception
     *             toute exception ..
     */
    @AroundInvoke
    public Object processException(final InvocationContext ctx)
	    throws Exception {// NOSONAR
	try {
	    return ctx.proceed();
	} catch (final Exception e) {
	    throw handleException(e, (Loggable) ctx.getTarget());// NOSONAR
	}
    }

    /**
     * Méthode partagée de traitement des exceptions<br/>
     * Les exceptions sont encapsulées dans une
     * ReferentielFormesJuridiquesException<br/>
     * Sauf si ce sont déjà des ReferentielFormesJuridiquesException
     * 
     * @param e
     *            exception
     * @return ReferentielFormesJuridiquesException exception encapsulée
     */
    protected ReferentielOfsException handleException(final Exception e,
	    final Loggable service) {
	service.log().error(e.getClass().getName(), e);
	// pas de double encapsulation
	if (e instanceof ReferentielOfsException) {
	    return (ReferentielOfsException) e;
	}
	return new ReferentielOfsException(
		"Erreur technique lors du traitement de la demande", e);
    }
}
