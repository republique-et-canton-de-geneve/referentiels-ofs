package ch.ge.cti.ct.referentiels.communes.interfaces.ws;

import java.util.Date;
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

import ch.ge.cti.ct.referentiels.communes.interfaces.ws.model.CantonWS;
import ch.ge.cti.ct.referentiels.communes.interfaces.ws.model.CommuneWS;
import ch.ge.cti.ct.referentiels.communes.interfaces.ws.model.DistrictWS;
import ch.ge.cti.ct.referentiels.communes.model.Canton;
import ch.ge.cti.ct.referentiels.communes.model.Commune;
import ch.ge.cti.ct.referentiels.communes.model.District;
import ch.ge.cti.ct.referentiels.communes.service.ReferentielCommunesServiceAble;
import ch.ge.cti.ct.referentiels.communes.service.impl.ReferentielCommunesService;
import ch.ge.cti.ct.referentiels.ofs.Loggable;
import ch.ge.cti.ct.referentiels.ofs.ReferentielOfsException;
import ch.ge.cti.ct.referentiels.ofs.ReferentielOfsExceptionIntercept;
import ch.ge.cti.ct.referentiels.ofs.cache.Cachable;
import ch.ge.cti.ct.referentiels.ofs.cache.ReferentielOfsCacheIntercept;
import ch.ge.cti.ct.referentiels.ofs.service.jmx.ReferentielStatsIntercept;

import com.google.common.base.Function;
import com.google.common.collect.FluentIterable;

/**
 * Exposition du service au format JAX-WS
 * 
 * @author DESMAZIERESJ
 * 
 */
@Stateless
@WebService(name = ReferentielCommunesWS.WEBSERVICE_NAME, serviceName = ReferentielCommunesWS.SERVICE_NAME, portName = ReferentielCommunesWS.PORT_NAME, targetNamespace = ReferentielCommunesWS.TARGET_NAMESPACE, endpointInterface = "ch.ge.cti.ct.referentiels.communes.interfaces.ws.ReferentielCommunesWS")
@WebContext(contextRoot = "/referentiels-ofs/communes", urlPattern = "/referentiel-communes")
@SOAPBinding(style = Style.DOCUMENT, use = Use.LITERAL)
@Interceptors({ ReferentielStatsIntercept.class,
	ReferentielOfsExceptionIntercept.class,
	ReferentielOfsCacheIntercept.class })
public class ReferentielCommunesSEI implements ReferentielCommunesWS, Loggable {

    /** Référence au service d'implémentation */
    private final ReferentielCommunesServiceAble service = ReferentielCommunesService.instance;

    /** logger SLF4j */
    private static final Logger LOG = LoggerFactory
	    .getLogger(ReferentielCommunesSEI.class);

    @Override
    public Logger log() {
	return LOG;
    }

    @Override
    @WebMethod(operationName = "getCantons", action = "getCantons")
    @WebResult(name = "canton")
    @Cachable(name = "cantons", size = Cachable.SMALL)
    public List<CantonWS> getCantons() throws ReferentielOfsException {
	return FluentIterable.from(service.getCantons())
		.transform(new CantonConvert()).toList();
    }

    @Override
    @WebMethod(operationName = "getCanton", action = "getCanton")
    @WebResult(name = "canton")
    public CantonWS getCanton(@WebParam(name = "canton") final String codeCanton)
	    throws ReferentielOfsException {
	if (StringUtils.isBlank(codeCanton)) {
	    return null;
	}
	return new CantonConvert().apply(service.getCanton(codeCanton));
    }

    @Override
    @WebMethod(operationName = "getDistrictsByCanton", action = "getDistrictsByCanton")
    @WebResult(name = "district")
    @Cachable(name = "districts", size = Cachable.MEDIUM)
    public List<DistrictWS> getDistrictsByCanton(
	    @WebParam(name = "canton") final String codeCanton)
	    throws ReferentielOfsException {
	if (StringUtils.isBlank(codeCanton)) {
	    return new LinkedList<DistrictWS>();
	}
	return FluentIterable.from(service.getDistricts(codeCanton))
		.transform(new DistrictConvert()).toList();
    }

    @Override
    @WebMethod(operationName = "getDistrict", action = "getDistrict")
    @WebResult(name = "district")
    public DistrictWS getDistrict(
	    @WebParam(name = "district") final int districtId)
	    throws ReferentielOfsException {
	if (districtId <= 0) {
	    return null;
	}
	return new DistrictConvert().apply(service.getDistrict(districtId));
    }

    @Override
    @WebMethod(operationName = "getCommunesByDistrict", action = "getCommunesByDistrict")
    @WebResult(name = "commune")
    @Cachable(name = "communes", size = Cachable.LARGE)
    public List<CommuneWS> getCommunesByDistrict(
	    @WebParam(name = "district") final int districtId)
	    throws ReferentielOfsException {
	if (districtId <= 0) {
	    return new LinkedList<CommuneWS>();
	}
	return FluentIterable.from(service.getCommunesByDistrict(districtId))
		.transform(new CommuneConvert()).toList();
    }

    @Override
    @WebMethod(operationName = "getCommunesByCanton", action = "getCommunesByCanton")
    @WebResult(name = "commune")
    @Cachable(name = "communes", size = Cachable.LARGE)
    public List<CommuneWS> getCommunesByCanton(
	    @WebParam(name = "canton") final String codeCanton)
	    throws ReferentielOfsException {
	if (StringUtils.isBlank(codeCanton)) {
	    return new LinkedList<CommuneWS>();
	}
	return FluentIterable.from(service.getCommunesByCanton(codeCanton))
		.transform(new CommuneConvert()).toList();
    }

    @Override
    @WebMethod(operationName = "getCommunesHistoriquesByCanton", action = "getCommunesHistoriquesByCanton")
    @WebResult(name = "commune")
    @Cachable(name = "communes", size = Cachable.LARGE)
    public List<CommuneWS> getCommunesHistoriquesByCanton(
	    @WebParam(name = "canton") final String codeCanton)
	    throws ReferentielOfsException {
	if (StringUtils.isBlank(codeCanton)) {
	    return new LinkedList<CommuneWS>();
	}
	return FluentIterable.from(service.getCommunesHistoriquesByCanton(codeCanton))
		.transform(new CommuneConvert()).toList();
    }
    @Override
    @WebMethod(operationName = "getCommune", action = "getCommune")
    @WebResult(name = "commune")
    public CommuneWS getCommune(@WebParam(name = "commune") final int communeId)
	    throws ReferentielOfsException {
	if (communeId <= 0) {
	    return null;
	}
	return new CommuneConvert().apply(service.getCommune(communeId));
    }

    @Override
    @WebMethod(operationName = "getCantonDate", action = "getCantonDate")
    @WebResult(name = "canton")
    public CantonWS getCantonDate(
	    @WebParam(name = "canton") final String codeCanton,
	    @WebParam(name = "dateValid") final Date dateValid)
	    throws ReferentielOfsException {
	if (StringUtils.isBlank(codeCanton)) {
	    return null;
	}
	return new CantonConvert().apply(service.getCanton(codeCanton,
		dateValid));
    }

    @Override
    @WebMethod(operationName = "getCantonsDate", action = "getCantonsDate")
    @WebResult(name = "canton")
    public List<CantonWS> getCantonsDate(
	    @WebParam(name = "dateValid") final Date dateValid)
	    throws ReferentielOfsException {
	return FluentIterable.from(service.getCantons(dateValid))
		.transform(new CantonConvert()).toList();
    }

    @Override
    @WebMethod(operationName = "getDistrictDate", action = "getDistrictDate")
    @WebResult(name = "district")
    public DistrictWS getDistrictDate(
	    @WebParam(name = "district") final int districtId,
	    @WebParam(name = "dateValid") final Date dateValid)
	    throws ReferentielOfsException {
	if (districtId <= 0) {
	    return null;
	}
	return new DistrictConvert().apply(service.getDistrict(districtId,
		dateValid));
    }

    @Override
    @WebMethod(operationName = "getDistrictsByCantonDate", action = "getDistrictsByCantonDate")
    @WebResult(name = "district")
    public List<DistrictWS> getDistrictsByCantonDate(
	    @WebParam(name = "canton") final String codeCanton,
	    @WebParam(name = "dateValid") final Date dateValid)
	    throws ReferentielOfsException {
	if (StringUtils.isBlank(codeCanton)) {
	    return new LinkedList<DistrictWS>();
	}
	return FluentIterable.from(service.getDistricts(codeCanton, dateValid))
		.transform(new DistrictConvert()).toList();
    }

    @Override
    @WebMethod(operationName = "getCommunesByDistrictDate", action = "getCommunesByDistrictDate")
    @WebResult(name = "commune")
    public List<CommuneWS> getCommunesByDistrictDate(
	    @WebParam(name = "district") final int districtId,
	    @WebParam(name = "dateValid") final Date dateValid)
	    throws ReferentielOfsException {
	if (districtId <= 0) {
	    return new LinkedList<CommuneWS>();
	}
	return FluentIterable
		.from(service.getCommunesByDistrict(districtId, dateValid))
		.transform(new CommuneConvert()).toList();
    }

    @Override
    @WebMethod(operationName = "getCommunesByCantonDate", action = "getCommunesByCantonDate")
    @WebResult(name = "commune")
    public List<CommuneWS> getCommunesByCantonDate(
	    @WebParam(name = "canton") final String codeCanton,
	    @WebParam(name = "dateValid") final Date dateValid)
	    throws ReferentielOfsException {
	if (StringUtils.isBlank(codeCanton)) {
	    return new LinkedList<CommuneWS>();
	}
	return FluentIterable
		.from(service.getCommunesByCanton(codeCanton, dateValid))
		.transform(new CommuneConvert()).toList();
    }

    @Override
    @WebMethod(operationName = "searchCommune", action = "searchCommune")
    @WebResult(name = "commune")
    public List<CommuneWS> searchCommune(
	    @WebParam(name = "critere") final String critere)
	    throws ReferentielOfsException {
	if (StringUtils.isBlank(critere)) {
	    return new LinkedList<CommuneWS>();
	}
	return FluentIterable.from(service.searchCommune(critere))
		.transform(new CommuneConvert()).toList();
    }

    @Override
    @WebMethod(operationName = "searchCommuneDate", action = "searchCommuneDate")
    @WebResult(name = "commune")
    public List<CommuneWS> searchCommuneDate(
	    @WebParam(name = "critere") final String critere,
	    @WebParam(name = "dateValid") final Date dateValid)
	    throws ReferentielOfsException {
	if (StringUtils.isBlank(critere)) {
	    return new LinkedList<CommuneWS>();
	}
	return FluentIterable.from(service.searchCommune(critere, dateValid))
		.transform(new CommuneConvert()).toList();
    }

    @Override
    @WebMethod(operationName = "searchCommuneRegexp", action = "searchCommuneRegexp")
    @WebResult(name = "commune")
    public List<CommuneWS> searchCommuneRegexp(
	    @WebParam(name = "regexp") final String regexp)
	    throws ReferentielOfsException {
	if (StringUtils.isBlank(regexp)) {
	    return new LinkedList<CommuneWS>();
	}
	return FluentIterable.from(service.searchCommuneRegexp(regexp))
		.transform(new CommuneConvert()).toList();
    }

    @Override
    @WebMethod(operationName = "searchCommuneDateRegexp", action = "searchCommuneDateRegexp")
    @WebResult(name = "commune")
    public List<CommuneWS> searchCommuneDateRegexp(
	    @WebParam(name = "regexp") final String regexp,
	    @WebParam(name = "dateValid") final Date dateValid)
	    throws ReferentielOfsException {
	if (StringUtils.isBlank(regexp)) {
	    return new LinkedList<CommuneWS>();
	}
	return FluentIterable
		.from(service.searchCommuneRegexp(regexp, dateValid))
		.transform(new CommuneConvert()).toList();
    }

    /**
     * Fonction (closure) de copie du canton en CantonWS<br/>
     * On ne copie par l'arborescence descendante (districts)
     */
    private static class CantonConvert implements Function<Canton, CantonWS> {
	@Override
	public CantonWS apply(final Canton cin) {
	    if (cin == null) {
		return null;
	    }
	    final CantonWS cout = new CantonWS();
	    cout.setCode(cin.getCode());
	    cout.setNom(cin.getNom());
	    cout.setNomCourt(cin.getNomCourt());
	    cout.setValidFrom(cin.getValidFrom());
	    cout.setValidTo(cin.getValidTo());
	    return cout;
	}
    }

    /**
     * Fonction (closure) de copie du District en DistrictWS<br/>
     * On ne copie par l'arborescence descendante (districts)
     */
    private static class DistrictConvert implements
	    Function<District, DistrictWS> {
	@Override
	public DistrictWS apply(final District cin) {
	    if (cin == null) {
		return null;
	    }
	    final DistrictWS cout = new DistrictWS();
	    cout.setId(cin.getId());
	    cout.setNom(cin.getNom());
	    cout.setNomCourt(cin.getNomCourt());
	    cout.setValidFrom(cin.getValidFrom());
	    cout.setValidTo(cin.getValidTo());
	    cout.setCodeCanton(cin.getCodeCanton());
	    return cout;
	}
    }

    /**
     * Fonction (closure) de copie du canton en CommuneWS
     */
    private static class CommuneConvert implements Function<Commune, CommuneWS> {
	@Override
	public CommuneWS apply(final Commune cin) {
	    if (cin == null) {
		return null;
	    }
	    final CommuneWS cout = new CommuneWS();
	    cout.setId(cin.getId());
	    cout.setNom(cin.getNom());
	    cout.setNomCourt(cin.getNomCourt());
	    cout.setValidFrom(cin.getValidFrom());
	    cout.setValidTo(cin.getValidTo());
	    cout.setCodeCanton(cin.getCodeCanton());
	    cout.setIdDistrict(cin.getIdDistrict());
	    return cout;
	}
    }
}
