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
import ch.ge.cti.ct.referentiels.ofs.ReferentielOfsException;
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
@WebService(name = "referentiel-communes-JAXWS", serviceName = "referentiel-communes", portName = "referentiel-communes", targetNamespace = "http://ch.ge.cti.ct.referentiels.communes/referentiel-communes")
@WebContext(contextRoot = "/referentiels-ofs/communes", urlPattern = "/referentiel-communes")
@SOAPBinding(style = Style.DOCUMENT, use = Use.LITERAL)
@Interceptors({ ReferentielStatsIntercept.class,
	ReferentielOfsCacheIntercept.class })
public class ReferentielCommunesSEI {

    /** R�f�rence au service d'impl�mentation */
    private ReferentielCommunesServiceAble service = ReferentielCommunesService.instance;

    /** logger SLF4j */
    private static final Logger LOG = LoggerFactory
	    .getLogger(ReferentielCommunesSEI.class);

    @WebMethod(operationName = "getCantons", action = "getCantons")
    @WebResult(name = "canton")
    @Cachable(name = "cantons", size = 1)
    public List<CantonWS> getCantons() throws ReferentielOfsException {
	LOG.debug("getCantons()");
	try {
	    return FluentIterable.from(service.getCantons())
		    .transform(new CantonConvert()).toList();
	} catch (Exception e) {
	    throw processException(e);
	}
    }

    @WebMethod(operationName = "getCanton", action = "getCanton")
    @WebResult(name = "canton")
    public CantonWS getCanton(@WebParam(name = "canton") final String codeCanton)
	    throws ReferentielOfsException {
	LOG.debug("getCanton(canton='{}')", codeCanton);
	if (StringUtils.isBlank(codeCanton)) {
	    return null;
	}
	try {
	    return new CantonConvert().apply(service.getCanton(codeCanton));
	} catch (Exception e) {
	    throw processException(e);
	}
    }

    @WebMethod(operationName = "getDistrictsByCanton", action = "getDistrictsByCanton")
    @WebResult(name = "district")
    @Cachable(name = "districts", size = 200)
    public List<DistrictWS> getDistricts(
	    @WebParam(name = "canton") final String codeCanton)
	    throws ReferentielOfsException {
	LOG.debug("getDistricts(canton='{}')", codeCanton);
	if (StringUtils.isBlank(codeCanton)) {
	    return new LinkedList<DistrictWS>();
	}
	try {
	    return FluentIterable.from(service.getDistricts(codeCanton))
		    .transform(new DistrictConvert()).toList();
	} catch (Exception e) {
	    throw processException(e);
	}
    }

    @WebMethod(operationName = "getDistrict", action = "getDistrict")
    @WebResult(name = "district")
    public DistrictWS getDistrict(
	    @WebParam(name = "district") final int districtId)
	    throws ReferentielOfsException {
	LOG.debug("getDistricts(district='{}')", districtId);
	if (districtId <= 0) {
	    return null;
	}
	try {
	    return new DistrictConvert().apply(service.getDistrict(districtId));
	} catch (Exception e) {
	    throw processException(e);
	}
    }

    @WebMethod(operationName = "getCommunesByDistrict", action = "getCommunesByDistrict")
    @WebResult(name = "commune")
    @Cachable(name = "communes", size = 500)
    public List<CommuneWS> getCommunesByDistrict(
	    @WebParam(name = "district") final int districtId)
	    throws ReferentielOfsException {
	LOG.debug("getCommunesByDistrict(district='{}')", districtId);
	if (districtId <= 0) {
	    return new LinkedList<CommuneWS>();
	}
	try {
	    return FluentIterable
		    .from(service.getCommunesByDistrict(districtId))
		    .transform(new CommuneConvert()).toList();
	} catch (Exception e) {
	    throw processException(e);
	}
    }

    @WebMethod(operationName = "getCommunesByCanton", action = "getCommunesByCanton")
    @WebResult(name = "commune")
    @Cachable(name = "communes", size = 500)
    public List<CommuneWS> getCommunesByCanton(
	    @WebParam(name = "canton") final String codeCanton)
	    throws ReferentielOfsException {
	LOG.debug("getCommunesByCanton(canton='{}')", codeCanton);
	if (StringUtils.isBlank(codeCanton)) {
	    return new LinkedList<CommuneWS>();
	}
	try {
	    return FluentIterable.from(service.getCommunesByCanton(codeCanton))
		    .transform(new CommuneConvert()).toList();
	} catch (Exception e) {
	    throw processException(e);
	}
    }

    @WebMethod(operationName = "getCommune", action = "getCommune")
    @WebResult(name = "commune")
    public CommuneWS getCommune(@WebParam(name = "commune") final int communeId)
	    throws ReferentielOfsException {
	LOG.debug("getCommune(commune='{}')", communeId);
	if (communeId <= 0) {
	    return null;
	}
	try {
	    return new CommuneConvert().apply(service.getCommune(communeId));
	} catch (Exception e) {
	    throw processException(e);
	}
    }

    @WebMethod(operationName = "getCantonDate", action = "getCantonDate")
    @WebResult(name = "canton")
    public CantonWS getCantonDate(
	    @WebParam(name = "canton") final String codeCanton,
	    @WebParam(name = "dateValid") final Date dateValid)
	    throws ReferentielOfsException {
	LOG.debug("getCanton(canton='{}', {})", codeCanton, dateValid);
	if (StringUtils.isBlank(codeCanton)) {
	    return null;
	}
	try {
	    return new CantonConvert().apply(service.getCanton(codeCanton,
		    dateValid));
	} catch (Exception e) {
	    throw processException(e);
	}
    }

    @WebMethod(operationName = "getCantonsDate", action = "getCantonsDate")
    @WebResult(name = "canton")
    public List<CantonWS> getCantonsDate(
	    @WebParam(name = "dateValid") final Date dateValid)
	    throws ReferentielOfsException {
	LOG.debug("getCantons(date='{}')", dateValid);
	try {
	    return FluentIterable.from(service.getCantons(dateValid))
		    .transform(new CantonConvert()).toList();
	} catch (Exception e) {
	    throw processException(e);
	}
    }

    @WebMethod(operationName = "getDistrictDate", action = "getDistrictDate")
    @WebResult(name = "district")
    public DistrictWS getDistrictDate(
	    @WebParam(name = "district") final int districtId,
	    @WebParam(name = "dateValid") final Date dateValid)
	    throws ReferentielOfsException {
	LOG.debug("getDistricts(district='{}', {})", districtId, dateValid);
	if (districtId <= 0) {
	    return null;
	}
	try {
	    return new DistrictConvert().apply(service.getDistrict(districtId,
		    dateValid));
	} catch (Exception e) {
	    throw processException(e);
	}
    }

    @WebMethod(operationName = "getDistrictsByCantonDate", action = "getDistrictsByCantonDate")
    @WebResult(name = "district")
    public List<DistrictWS> getDistrictsByCantonDate(
	    @WebParam(name = "canton") final String codeCanton,
	    @WebParam(name = "dateValid") final Date dateValid)
	    throws ReferentielOfsException {
	LOG.debug("getDistricts(canton='{}', date='{}')", codeCanton);
	if (StringUtils.isBlank(codeCanton)) {
	    return new LinkedList<DistrictWS>();
	}
	try {
	    return FluentIterable
		    .from(service.getDistricts(codeCanton, dateValid))
		    .transform(new DistrictConvert()).toList();
	} catch (Exception e) {
	    throw processException(e);
	}
    }

    @WebMethod(operationName = "getCommunesByDistrictDate", action = "getCommunesByDistrictDate")
    @WebResult(name = "commune")
    public List<CommuneWS> getCommunesByDistrictDate(final int districtId,
	    @WebParam(name = "dateValid") final Date dateValid)
	    throws ReferentielOfsException {
	LOG.debug("getCommunesByDistrict(district='{}', date='{}')",
		districtId, dateValid);
	if (districtId <= 0) {
	    return new LinkedList<CommuneWS>();
	}
	try {
	    return FluentIterable
		    .from(service.getCommunesByDistrict(districtId, dateValid))
		    .transform(new CommuneConvert()).toList();
	} catch (Exception e) {
	    throw processException(e);
	}
    }

    @WebMethod(operationName = "getCommunesByCantonDate", action = "getCommunesByCantonDate")
    @WebResult(name = "commune")
    public List<CommuneWS> getCommunesByCantonDate(
	    @WebParam(name = "canton") final String codeCanton,
	    @WebParam(name = "dateValid") final Date dateValid)
	    throws ReferentielOfsException {
	LOG.debug("getCommunesByCanton(canton='{}', date='{}')", codeCanton,
		dateValid);
	if (StringUtils.isBlank(codeCanton)) {
	    return new LinkedList<CommuneWS>();
	}
	try {
	    return FluentIterable
		    .from(service.getCommunesByCanton(codeCanton, dateValid))
		    .transform(new CommuneConvert()).toList();
	} catch (Exception e) {
	    throw processException(e);
	}
    }

    @WebMethod(operationName = "searchCommune", action = "searchCommune")
    @WebResult(name = "commune")
    public List<CommuneWS> searchCommune(
	    @WebParam(name = "critere") final String critere)
	    throws ReferentielOfsException {
	LOG.debug("searchCommune(critere='{}')", critere);
	if (StringUtils.isBlank(critere)) {
	    return new LinkedList<CommuneWS>();
	}
	try {
	    return FluentIterable.from(service.searchCommune(critere))
		    .transform(new CommuneConvert()).toList();
	} catch (Exception e) {
	    throw processException(e);
	}
    }

    @WebMethod(operationName = "searchCommuneDate", action = "searchCommuneDate")
    @WebResult(name = "commune")
    public List<CommuneWS> searchCommuneDate(
	    @WebParam(name = "critere") final String critere,
	    @WebParam(name = "dateValid") final Date dateValid)
	    throws ReferentielOfsException {
	LOG.debug("searchCommune(critere='{}', date='{}')", critere, dateValid);
	if (StringUtils.isBlank(critere)) {
	    return new LinkedList<CommuneWS>();
	}
	try {
	    return FluentIterable
		    .from(service.searchCommune(critere, dateValid))
		    .transform(new CommuneConvert()).toList();
	} catch (Exception e) {
	    throw processException(e);
	}
    }

    /**
     * M�thode partag�e de traitement des exceptions<br/>
     * Les exceptions sont encapsul�es dans une ReferentielCommunesException<br/>
     * Sauf si ce sont d�j� des ReferentielCommunesException
     * 
     * @param e
     *            exception
     * @return ReferentielCommunesException exception encapsul�e
     */
    private ReferentielOfsException processException(final Exception e) {
	// pas de double encapsulation
	if (e instanceof ReferentielOfsException) {
	    return (ReferentielOfsException) e;
	}
	return new ReferentielOfsException(
		"Erreur technique lors du traitement de la demande", e);
    }

    /**
     * Fonction (closure) de copie du canton en CantonWS<br/>
     * On ne copie par l'arborescence descendante (districts)
     */
    private class CantonConvert implements Function<Canton, CantonWS> {
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
    private class DistrictConvert implements Function<District, DistrictWS> {
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
    private class CommuneConvert implements Function<Commune, CommuneWS> {
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
