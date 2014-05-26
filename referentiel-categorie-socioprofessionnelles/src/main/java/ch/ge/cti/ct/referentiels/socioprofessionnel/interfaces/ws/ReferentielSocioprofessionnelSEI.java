package ch.ge.cti.ct.referentiels.socioprofessionnel.interfaces.ws;

import java.util.LinkedList;
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

import org.apache.commons.lang.StringUtils;
import org.jboss.wsf.spi.annotation.WebContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ch.ge.cti.ct.referentiels.ofs.Loggable;
import ch.ge.cti.ct.referentiels.ofs.ReferentielOfsException;
import ch.ge.cti.ct.referentiels.ofs.ReferentielOfsExceptionIntercept;
import ch.ge.cti.ct.referentiels.ofs.cache.Cachable;
import ch.ge.cti.ct.referentiels.ofs.cache.ReferentielOfsCacheIntercept;
import ch.ge.cti.ct.referentiels.ofs.service.jmx.ReferentielStatsIntercept;
import ch.ge.cti.ct.referentiels.socioprofessionnel.interfaces.ws.model.Niveau1WS;
import ch.ge.cti.ct.referentiels.socioprofessionnel.interfaces.ws.model.Niveau2WS;
import ch.ge.cti.ct.referentiels.socioprofessionnel.model.Niveau1;
import ch.ge.cti.ct.referentiels.socioprofessionnel.model.Niveau2;
import ch.ge.cti.ct.referentiels.socioprofessionnel.service.ReferentielSocioprofessionnelServiceAble;
import ch.ge.cti.ct.referentiels.socioprofessionnel.service.impl.ReferentielSocioprofessionnelService;

import com.google.common.base.Function;
import com.google.common.collect.FluentIterable;

/**
 * Exposition du service au format JAX-WS.
 * 
 * @author DESMAZIERESJ
 * 
 */
@Stateless
@WebService(name = ReferentielSocioprofessionnelWS.WEBSERVICE_NAME, serviceName = ReferentielSocioprofessionnelWS.SERVICE_NAME, portName = ReferentielSocioprofessionnelWS.PORT_NAME, targetNamespace = ReferentielSocioprofessionnelWS.TARGET_NAMESPACE, endpointInterface = "ch.ge.cti.ct.referentiels.socioprofessionnel.interfaces.ws.ReferentielSocioprofessionnelWS")
@WebContext(contextRoot = "/referentiels-ofs/socioprofessionnel", urlPattern = "/referentiel-socioprofessionnel")
@SOAPBinding(style = Style.DOCUMENT, use = Use.LITERAL)
@Interceptors({ ReferentielStatsIntercept.class,
	ReferentielOfsExceptionIntercept.class,
	ReferentielOfsCacheIntercept.class })
public class ReferentielSocioprofessionnelSEI implements
	ReferentielSocioprofessionnelWS, Loggable {

    /** logger SLF4j */
    private static final Logger LOG = LoggerFactory
	    .getLogger(ReferentielSocioprofessionnelSEI.class);

    /** Référence sur l'implémentation */
    private final ReferentielSocioprofessionnelServiceAble service = ReferentielSocioprofessionnelService.instance;

    @Override
    public Logger log() {
	return LOG;
    }

    @Override
    @WebMethod(operationName = "getNiveaux1", action = "getNiveaux1")
    @WebResult(name = "niveau1")
    @Cachable(name = "niveau1", size = Cachable.MEDIUM)
    public List<Niveau1WS> getNiveaux1() throws ReferentielOfsException {
	return FluentIterable.from(service.getNiveaux1())
		.transform(new Niveau1Convert()).toList();
    }

    @Override
    @WebMethod(operationName = "getNiveau1", action = "getNiveau1")
    @WebResult(name = "niveau1")
    @Cachable(name = "niveau1", size = Cachable.MEDIUM)
    public Niveau1WS getNiveau1(
	    @WebParam(name = "niveau1Id") final int niveau1Id)
	    throws ReferentielOfsException {
	if (niveau1Id <= 0) {
	    return null;
	}
	return new Niveau1Convert().apply(service.getNiveau1(niveau1Id));
    }

    @Override
    @WebMethod(operationName = "searchNiveaux1", action = "searchNiveaux1")
    @WebResult(name = "niveau1")
    public List<Niveau1WS> searchNiveaux1(
	    @WebParam(name = "pattern") final String pattern)
	    throws ReferentielOfsException {
	if (StringUtils.isBlank(pattern)) {
	    return new LinkedList<Niveau1WS>();
	}
	return FluentIterable.from(service.searchNiveaux1(pattern))
		.transform(new Niveau1Convert()).toList();
    }

    @Override
    @WebMethod(operationName = "searchNiveaux1Regexp", action = "searchNiveaux1Regexp")
    @WebResult(name = "niveau1")
    public List<Niveau1WS> searchNiveaux1Regexp(
	    @WebParam(name = "regexp") final String regexp)
	    throws ReferentielOfsException {
	if (StringUtils.isBlank(regexp)) {
	    return new LinkedList<Niveau1WS>();
	}
	return FluentIterable.from(service.searchNiveaux1Regexp(regexp))
		.transform(new Niveau1Convert()).toList();
    }

    @Override
    @WebMethod(operationName = "getNiveaux2", action = "getNiveaux2")
    @WebResult(name = "niveau2")
    @Cachable(name = "niveau2", size = Cachable.MEDIUM)
    public List<Niveau2WS> getNiveaux2() throws ReferentielOfsException {
	return FluentIterable.from(service.getNiveaux2())
		.transform(new Niveau2Convert()).toList();
    }

    @Override
    @WebMethod(operationName = "getNiveaux2ByNiveau1", action = "getNiveaux2ByNiveau1")
    @WebResult(name = "niveau2")
    @Cachable(name = "niveau2", size = Cachable.MEDIUM)
    public List<Niveau2WS> getNiveaux2ByNiveau1(
	    @WebParam(name = "niveau1Id") final int niveau1Id)
	    throws ReferentielOfsException {
	if (niveau1Id <= 0) {
	    return null;
	}
	return FluentIterable.from(service.getNiveaux2(niveau1Id))
		.transform(new Niveau2Convert()).toList();
    }

    @Override
    @WebMethod(operationName = "getNiveau2", action = "getNiveau2")
    @WebResult(name = "niveau2")
    public Niveau2WS getNiveau2(
	    @WebParam(name = "niveau2Id") final int niveau2Id)
	    throws ReferentielOfsException {
	if (niveau2Id <= 0) {
	    return null;
	}
	return new Niveau2Convert().apply(service.getNiveau2(niveau2Id));
    }

    @Override
    @WebMethod(operationName = "searchNiveaux2", action = "searchNiveaux2")
    @WebResult(name = "niveau2")
    public List<Niveau2WS> searchNiveaux2(
	    @WebParam(name = "pattern") final String pattern)
	    throws ReferentielOfsException {
	if (StringUtils.isBlank(pattern)) {
	    return new LinkedList<Niveau2WS>();
	}
	return FluentIterable.from(service.searchNiveaux2(pattern))
		.transform(new Niveau2Convert()).toList();
    }

    @Override
    @WebMethod(operationName = "searchNiveaux2Regexp", action = "searchNiveaux2Regexp")
    @WebResult(name = "niveau2")
    public List<Niveau2WS> searchNiveaux2Regexp(
	    @WebParam(name = "regexp") final String regexp)
	    throws ReferentielOfsException {
	if (StringUtils.isBlank(regexp)) {
	    return new LinkedList<Niveau2WS>();
	}
	return FluentIterable.from(service.searchNiveaux2Regexp(regexp))
		.transform(new Niveau2Convert()).toList();
    }

    /**
     * Fonction (closure) de copie du continent en ClasseWS<br/>
     * On ne copie par l'arborescence descendante (Classe)
     */
    private static class Niveau1Convert implements Function<Niveau1, Niveau1WS> {
	@Override
	public Niveau1WS apply(final Niveau1 cin) {
	    if (cin == null) {
		return null;
	    }
	    final Niveau1WS cout = new Niveau1WS();
	    cout.setId(cin.getId());
	    cout.setNom(cin.getNom());
	    return cout;
	}
    }

    /**
     * Fonction (closure) de copie du continent en GroupeWS<br/>
     * On ne copie par l'arborescence descendante (Groupe)
     */
    private static class Niveau2Convert implements Function<Niveau2, Niveau2WS> {
	@Override
	public Niveau2WS apply(final Niveau2 cin) {
	    if (cin == null) {
		return null;
	    }
	    final Niveau2WS cout = new Niveau2WS();
	    cout.setId(cin.getId());
	    cout.setNom(cin.getNom());
	    cout.setNiveau1Id(cin.getNiveau1Id());
	    return cout;
	}
    }
}
