package ch.ge.cti.ct.referentiels.etatCivil.interfaces.ws;

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

import ch.ge.cti.ct.referentiels.etatCivil.model.EtatCivil;
import ch.ge.cti.ct.referentiels.etatCivil.service.ReferentielEtatCivilServiceAble;
import ch.ge.cti.ct.referentiels.etatCivil.service.impl.ReferentielEtatCivilService;
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
@WebService(name = ReferentielEtatCivilWS.WEBSERVICE_NAME, serviceName = ReferentielEtatCivilWS.SERVICE_NAME, portName = ReferentielEtatCivilWS.PORT_NAME, targetNamespace = ReferentielEtatCivilWS.TARGET_NAMESPACE)
@WebContext(contextRoot = "/referentiels-ofs/etat-civil", urlPattern = "/referentiel-etat-civil")
@SOAPBinding(style = Style.DOCUMENT, use = Use.LITERAL)
@Interceptors({ ReferentielStatsIntercept.class,
	ReferentielOfsCacheIntercept.class })
public class ReferentielEtatCivilSEI implements ReferentielEtatCivilWS {

    /** Référence sur l'implémentation */
    private final ReferentielEtatCivilServiceAble service = ReferentielEtatCivilService.instance;

    /** logger SLF4j */
    private static final Logger LOG = LoggerFactory
	    .getLogger(ReferentielEtatCivilSEI.class);

    /*
     * (non-Javadoc)
     * 
     * @see
     * ch.ge.cti.ct.referentiels.etatCivil.interfaces.ws.ReferentielEtatCivilWS
     * #getEtatsCivils()
     */
    @Override
    @WebMethod(operationName = "getEtatsCivils", action = "getEtatsCivils")
    @WebResult(name = "etatCivil")
    public List<EtatCivil> getEtatsCivils() throws ReferentielOfsException {
	LOG.debug("getEtatsCivils()");
	try {
	    return service.getEtatsCivils();
	} catch (final Exception e) {
	    throw processException(e);
	}
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * ch.ge.cti.ct.referentiels.etatCivil.interfaces.ws.ReferentielEtatCivilWS
     * #getEtatCivil(int)
     */
    @Override
    @WebMethod(operationName = "getEtatCivil", action = "getEtatCivil")
    @WebResult(name = "etatCivil")
    public EtatCivil getEtatCivil(
	    @WebParam(name = "etatCivil") final int etatCivilId)
	    throws ReferentielOfsException {
	LOG.debug("getEtatCivil(etatCivil='{}')", etatCivilId);
	if (etatCivilId <= 0) {
	    return null;
	}
	try {
	    return service.getEtatCivil(etatCivilId);
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
	// pas de double encapsulation
	if (e instanceof ReferentielOfsException) {
	    return (ReferentielOfsException) e;
	}
	return new ReferentielOfsException(
		"Erreur technique lors du traitement de la demande", e);
    }

}
