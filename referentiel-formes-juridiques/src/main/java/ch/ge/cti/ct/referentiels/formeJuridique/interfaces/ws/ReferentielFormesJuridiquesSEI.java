package ch.ge.cti.ct.referentiels.formeJuridique.interfaces.ws;

import java.util.List;

import javax.ejb.Stateless;
import javax.interceptor.Interceptors;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.jws.soap.SOAPBinding.Style;
import javax.jws.soap.SOAPBinding.Use;

import org.jboss.wsf.spi.annotation.WebContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ch.ge.cti.ct.referentiels.formeJuridique.model.FormeJuridique;
import ch.ge.cti.ct.referentiels.formeJuridique.service.ReferentielFormesJuridiquesServiceAble;
import ch.ge.cti.ct.referentiels.formeJuridique.service.impl.ReferentielFormesJuridiquesService;
import ch.ge.cti.ct.referentiels.ofs.ReferentielOfsException;
import ch.ge.cti.ct.referentiels.ofs.cache.ReferentielOfsCacheIntercept;
import ch.ge.cti.ct.referentiels.ofs.service.jmx.ReferentielStatsIntercept;

/**
 * Exposition du service au format JAX-WS
 * 
 * @author DESMAZIERESJ
 * 
 */
@Stateless
@WebService(name = "referentiel-pays-JAXWS", serviceName = "referentiel-pays", portName = "referentiel-pays", targetNamespace = "http://ch.ge.cti.ct.referentiels.pays/referentiel-pays")
@WebContext(contextRoot = "/referentiels-ofs/territoires", urlPattern = "/referentiel-pays")
@SOAPBinding(style = Style.DOCUMENT, use = Use.LITERAL)
@Interceptors({ ReferentielStatsIntercept.class,
	ReferentielOfsCacheIntercept.class })
public class ReferentielFormesJuridiquesSEI {

    /** R�f�rence sur l'impl�mentation */
    private ReferentielFormesJuridiquesServiceAble service = ReferentielFormesJuridiquesService.instance;

    /** logger SLF4j */
    private static final Logger LOG = LoggerFactory
	    .getLogger(ReferentielFormesJuridiquesSEI.class);

    @WebMethod(operationName = "getFormesJuridiques", action = "getFormesJuridiques")
    @WebResult(name = "formeJuridique")
    public List<FormeJuridique> getFormesJuridiques()
	    throws ReferentielOfsException {
	LOG.debug("getContinents()");
	try {
	    return service.getFormesJuridiques();
	} catch (Exception e) {
	    throw processException(e);
	}
    }

    @WebMethod(operationName = "getFormeJuridique", action = "getFormeJuridique")
    @WebResult(name = "formeJuridique")
    public FormeJuridique getFormeJuridique(
	    @WebParam(name = "formeJuridique") final int formeJuridiqueId)
	    throws ReferentielOfsException {
	LOG.debug("getFormeJuridique(formeJuridique='{}')", formeJuridiqueId);
	if (formeJuridiqueId <= 0) {
	    return null;
	}
	try {
	    return service.getFormeJuridique(formeJuridiqueId);
	} catch (Exception e) {
	    throw processException(e);
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
    private ReferentielOfsException processException(final Exception e) {
	// pas de double encapsulation
	if (e instanceof ReferentielOfsException) {
	    return (ReferentielOfsException) e;
	}
	return new ReferentielOfsException(
		"Erreur technique lors du traitement de la demande", e);
    }

}