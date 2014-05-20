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
import ch.ge.cti.ct.referentiels.ofs.service.jmx.ReferentielStatsIntercept;

/**
 * Exposition du service au format JAX-WS
 * 
 * @author DESMAZIERESJ
 * 
 */
@Stateless
@WebService(name = ReferentielFormesJuridiquesWS.WEBSERVICE_NAME, serviceName = ReferentielFormesJuridiquesWS.SERVICE_NAME, portName = ReferentielFormesJuridiquesWS.PORT_NAME, targetNamespace = ReferentielFormesJuridiquesWS.TARGET_NAMESPACE, endpointInterface = "ch.ge.cti.ct.referentiels.formeJuridique.interfaces.ws.ReferentielFormesJuridiquesWS")
@WebContext(contextRoot = "/referentiels-ofs/formes-juridiques", urlPattern = "/referentiel-formes-juridiques")
@SOAPBinding(style = Style.DOCUMENT, use = Use.LITERAL)
@Interceptors({ ReferentielStatsIntercept.class })
public class ReferentielFormesJuridiquesSEI implements
	ReferentielFormesJuridiquesWS {

    /** Référence sur l'implémentation */
    private final ReferentielFormesJuridiquesServiceAble service = ReferentielFormesJuridiquesService.instance;

    /** logger SLF4j */
    private static final Logger LOG = LoggerFactory
	    .getLogger(ReferentielFormesJuridiquesSEI.class);

    /*
     * (non-Javadoc)
     * 
     * @see ch.ge.cti.ct.referentiels.formeJuridique.interfaces.ws.
     * ReferentielFormesJuridiquesWS#getFormesJuridiques()
     */
    @Override
    @WebMethod(operationName = "getFormesJuridiques", action = "getFormesJuridiques")
    @WebResult(name = "formeJuridique")
    public List<FormeJuridique> getFormesJuridiques()
	    throws ReferentielOfsException {
	LOG.debug("getFormesJuridiques()");
	try {
	    return service.getFormesJuridiques();
	} catch (final Exception e) {
	    throw processException(e);
	}
    }

    /*
     * (non-Javadoc)
     * 
     * @see ch.ge.cti.ct.referentiels.formeJuridique.interfaces.ws.
     * ReferentielFormesJuridiquesWS#getFormeJuridique(int)
     */
    @Override
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
	} catch (final Exception e) {
	    throw processException(e);
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
    private ReferentielOfsException processException(final Exception e) {
	LOG.error(e.getClass().getName(), e);
	// pas de double encapsulation
	if (e instanceof ReferentielOfsException) {
	    return (ReferentielOfsException) e;
	}
	return new ReferentielOfsException(
		"Erreur technique lors du traitement de la demande", e);
    }

}
