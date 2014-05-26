package ch.ge.cti.ct.referentiels.ofs;

import javax.interceptor.AroundInvoke;
import javax.interceptor.InvocationContext;

/**
 * Intercepteur des appels afin de g�rer les exceptions sur les m�thodes<br/>
 * 
 * @author DESMAZIERESJ
 */
public class ReferentielOfsExceptionIntercept {

    /**
     * interception de toutes les m�thodes pour gestion du cache
     * 
     * @param ctx
     *            contexte d'appel
     * @return r�sultat du traitement
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
     * M�thode partag�e de traitement des exceptions<br/>
     * Les exceptions sont encapsul�es dans une
     * ReferentielFormesJuridiquesException<br/>
     * Sauf si ce sont d�j� des ReferentielFormesJuridiquesException
     * 
     * @param e
     *            exception
     * @return ReferentielFormesJuridiquesException exception encapsul�e
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
