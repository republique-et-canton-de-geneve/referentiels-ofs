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
import ch.ge.cti.ct.referentiels.ofs.Loggable;
import ch.ge.cti.ct.referentiels.ofs.ReferentielOfsException;
import ch.ge.cti.ct.referentiels.ofs.ReferentielOfsExceptionIntercept;
import ch.ge.cti.ct.referentiels.ofs.cache.ReferentielOfsCacheIntercept;
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
@Interceptors({ ReferentielStatsIntercept.class,
	ReferentielOfsExceptionIntercept.class,
	ReferentielOfsCacheIntercept.class })
public class ReferentielFormesJuridiquesSEI implements
	ReferentielFormesJuridiquesWS, Loggable {

    /** Référence sur l'implémentation */
    private final ReferentielFormesJuridiquesServiceAble service = ReferentielFormesJuridiquesService.instance;

    /** logger SLF4j */
    private static final Logger LOG = LoggerFactory
	    .getLogger(ReferentielFormesJuridiquesSEI.class);

    @Override
    public Logger log() {
	return LOG;
    }

    @Override
    @WebMethod(operationName = "getFormesJuridiques", action = "getFormesJuridiques")
    @WebResult(name = "formeJuridique")
    public List<FormeJuridique> getFormesJuridiques()
	    throws ReferentielOfsException {
	return service.getFormesJuridiques();
    }

    @Override
    @WebMethod(operationName = "getFormeJuridique", action = "getFormeJuridique")
    @WebResult(name = "formeJuridique")
    public FormeJuridique getFormeJuridique(
	    @WebParam(name = "formeJuridique") final int formeJuridiqueId)
	    throws ReferentielOfsException {
	if (formeJuridiqueId <= 0) {
	    return null;
	}
	return service.getFormeJuridique(formeJuridiqueId);
    }

}
