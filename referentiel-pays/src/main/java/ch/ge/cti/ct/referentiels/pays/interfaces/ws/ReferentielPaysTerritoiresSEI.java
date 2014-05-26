package ch.ge.cti.ct.referentiels.pays.interfaces.ws;

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
import ch.ge.cti.ct.referentiels.pays.interfaces.ws.model.ContinentWS;
import ch.ge.cti.ct.referentiels.pays.interfaces.ws.model.PaysWS;
import ch.ge.cti.ct.referentiels.pays.interfaces.ws.model.RegionWS;
import ch.ge.cti.ct.referentiels.pays.model.Continent;
import ch.ge.cti.ct.referentiels.pays.model.Pays;
import ch.ge.cti.ct.referentiels.pays.model.Region;
import ch.ge.cti.ct.referentiels.pays.service.ReferentielPaysTerritoiresServiceAble;
import ch.ge.cti.ct.referentiels.pays.service.impl.ReferentielPaysTerritoiresService;

import com.google.common.base.Function;
import com.google.common.collect.FluentIterable;

/**
 * Exposition du service au format JAX-WS
 * 
 * @author DESMAZIERESJ
 * 
 */
@Stateless
@WebService(name = ReferentielPaysTerritoiresWS.WEBSERVICE_NAME, serviceName = ReferentielPaysTerritoiresWS.SERVICE_NAME, portName = ReferentielPaysTerritoiresWS.PORT_NAME, targetNamespace = ReferentielPaysTerritoiresWS.TARGET_NAMESPACE, endpointInterface = "ch.ge.cti.ct.referentiels.pays.interfaces.ws.ReferentielPaysTerritoiresWS")
@WebContext(contextRoot = "/referentiels-ofs/territoires", urlPattern = "/referentiel-pays")
@SOAPBinding(style = Style.DOCUMENT, use = Use.LITERAL)
@Interceptors({ ReferentielStatsIntercept.class,
	ReferentielOfsExceptionIntercept.class,
	ReferentielOfsCacheIntercept.class })
public class ReferentielPaysTerritoiresSEI implements
	ReferentielPaysTerritoiresWS, Loggable {

    /** Référence sur l'implémentation */
    private final ReferentielPaysTerritoiresServiceAble service = ReferentielPaysTerritoiresService.instance;

    /** logger SLF4j */
    private static final Logger LOG = LoggerFactory
	    .getLogger(ReferentielPaysTerritoiresSEI.class);

    @Override
    public Logger log() {
	return LOG;
    }

    @Override
    @WebMethod(operationName = "getContinents", action = "getContinents")
    @WebResult(name = "continent")
    @Cachable(name = "continents", size = Cachable.SMALL)
    public List<ContinentWS> getContinents() throws ReferentielOfsException {
	try {
	    return FluentIterable.from(service.getContinents())
		    .transform(new ContinentConvert()).toList();
	} catch (final Exception e) {
	    throw processException(e);
	}
    }

    @Override
    @WebMethod(operationName = "getContinent", action = "getContinent")
    @WebResult(name = "continent")
    public ContinentWS getContinent(
	    @WebParam(name = "continent") final int continentId)
	    throws ReferentielOfsException {
	if (continentId <= 0) {
	    return null;
	}
	try {
	    return new ContinentConvert().apply(service
		    .getContinent(continentId));
	} catch (final Exception e) {
	    throw processException(e);
	}
    }

    @Override
    @WebMethod(operationName = "getRegionsByContinent", action = "getRegionsByContinent")
    @WebResult(name = "region")
    @Cachable(name = "regions", size = Cachable.MEDIUM)
    public List<RegionWS> getRegionsByContinent(
	    @WebParam(name = "continent") final int continentId)
	    throws ReferentielOfsException {
	if (continentId <= 0) {
	    return new LinkedList<RegionWS>();
	}
	try {
	    return FluentIterable.from(service.getRegions(continentId))
		    .transform(new RegionConvert()).toList();
	} catch (final Exception e) {
	    throw processException(e);
	}
    }

    @Override
    @WebMethod(operationName = "getRegions", action = "getRegions")
    @WebResult(name = "region")
    @Cachable(name = "regions", size = Cachable.MEDIUM)
    public List<RegionWS> getRegions() throws ReferentielOfsException {
	try {
	    return FluentIterable.from(service.getRegions())
		    .transform(new RegionConvert()).toList();
	} catch (final Exception e) {
	    throw processException(e);
	}
    }

    @Override
    @WebMethod(operationName = "getRegion", action = "getRegion")
    @WebResult(name = "region")
    public RegionWS getRegion(@WebParam(name = "region") final int regionId)
	    throws ReferentielOfsException {
	if (regionId <= 0) {
	    return null;
	}
	try {
	    return new RegionConvert().apply(service.getRegion(regionId));
	} catch (final Exception e) {
	    throw processException(e);
	}
    }

    @Override
    @WebMethod(operationName = "getPaysByRegion", action = "getPaysByRegion")
    @WebResult(name = "pays")
    @Cachable(name = "pays", size = Cachable.LARGE)
    public List<PaysWS> getPaysByRegion(
	    @WebParam(name = "region") final int regionId)
	    throws ReferentielOfsException {
	if (regionId <= 0) {
	    return new LinkedList<PaysWS>();
	}
	try {
	    return FluentIterable.from(service.getPaysByRegion(regionId))
		    .transform(new PaysConvert()).toList();
	} catch (final Exception e) {
	    throw processException(e);
	}
    }

    @Override
    @WebMethod(operationName = "getPaysByContinent", action = "getPaysByContinent")
    @WebResult(name = "pays")
    @Cachable(name = "pays", size = Cachable.LARGE)
    public List<PaysWS> getPaysByContinent(
	    @WebParam(name = "continent") final int continentId)
	    throws ReferentielOfsException {
	if (continentId <= 0) {
	    return new LinkedList<PaysWS>();
	}
	try {
	    return FluentIterable.from(service.getPaysByContinent(continentId))
		    .transform(new PaysConvert()).toList();
	} catch (final Exception e) {
	    throw processException(e);
	}
    }

    @Override
    @WebMethod(operationName = "getPays", action = "getPays")
    @WebResult(name = "pays")
    @Cachable(name = "pays", size = Cachable.LARGE)
    public List<PaysWS> getPays() throws ReferentielOfsException {
	try {
	    return FluentIterable.from(service.getPays())
		    .transform(new PaysConvert()).toList();
	} catch (final Exception e) {
	    throw processException(e);
	}
    }

    @Override
    @WebMethod(operationName = "getPaysByIso2", action = "getPaysByIso2")
    @WebResult(name = "pays")
    public PaysWS getPaysByIso2(@WebParam(name = "iso2") final String iso2)
	    throws ReferentielOfsException {
	if (StringUtils.isBlank(iso2)) {
	    return null;
	}
	try {
	    return new PaysConvert().apply(service.getPaysByISO2(iso2));
	} catch (final Exception e) {
	    throw processException(e);
	}
    }

    @Override
    @WebMethod(operationName = "getPaysByIso3", action = "getPaysByIso3")
    @WebResult(name = "pays")
    public PaysWS getPaysByIso3(@WebParam(name = "iso3") final String iso3)
	    throws ReferentielOfsException {
	if (StringUtils.isBlank(iso3)) {
	    return null;
	}
	try {
	    return new PaysConvert().apply(service.getPaysByISO3(iso3));
	} catch (final Exception e) {
	    throw processException(e);
	}
    }

    @Override
    @WebMethod(operationName = "searchPays", action = "searchPays")
    @WebResult(name = "pays")
    public List<PaysWS> searchPays(
	    @WebParam(name = "critere") final String critere)
	    throws ReferentielOfsException {
	if (StringUtils.isBlank(critere)) {
	    return new LinkedList<PaysWS>();
	}
	try {
	    return FluentIterable.from(service.searchPays(critere))
		    .transform(new PaysConvert()).toList();
	} catch (final Exception e) {
	    throw processException(e);
	}
    }

    @Override
    @WebMethod(operationName = "searchPaysRegexp", action = "searchPaysRegexp")
    @WebResult(name = "pays")
    public List<PaysWS> searchPaysRegexp(
	    @WebParam(name = "regexp") final String regexp)
	    throws ReferentielOfsException {
	if (StringUtils.isBlank(regexp)) {
	    return new LinkedList<PaysWS>();
	}
	try {
	    return FluentIterable.from(service.searchPaysRegexp(regexp))
		    .transform(new PaysConvert()).toList();
	} catch (final Exception e) {
	    throw processException(e);
	}
    }

    /**
     * Méthode partagée de traitement des exceptions<br/>
     * Les exceptions sont encapsulées dans une
     * ReferentielPaysTerritoiresException<br/>
     * Sauf si ce sont déjà des ReferentielPaysTerritoiresException
     * 
     * @param e
     *            exception
     * @return ReferentielPaysTerritoiresException exception encapsulée
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

    /**
     * Fonction (closure) de copie du continent en ContinentWS<br/>
     * On ne copie par l'arborescence descendante (regions)
     */
    private static class ContinentConvert implements
	    Function<Continent, ContinentWS> {
	@Override
	public ContinentWS apply(final Continent cin) {
	    if (cin == null) {
		return null;
	    }
	    final ContinentWS cout = new ContinentWS();
	    cout.setId(cin.getId());
	    cout.setNom(cin.getNom());
	    return cout;
	}
    }

    /**
     * Fonction (closure) de copie du Region en RegionWS<br/>
     * On ne copie par l'arborescence descendante (regions)
     */
    private static class RegionConvert implements Function<Region, RegionWS> {
	@Override
	public RegionWS apply(final Region cin) {
	    if (cin == null) {
		return null;
	    }
	    final RegionWS cout = new RegionWS();
	    cout.setId(cin.getId());
	    cout.setNom(cin.getNom());
	    cout.setContinentId(cin.getContinentId());
	    return cout;
	}
    }

    /**
     * Fonction (closure) de copie du Pays en PaysWS
     */
    private static class PaysConvert implements Function<Pays, PaysWS> {
	@Override
	public PaysWS apply(final Pays cin) {
	    if (cin == null) {
		return null;
	    }
	    final PaysWS cout = new PaysWS();
	    cout.setId(cin.getId());
	    cout.setNom(cin.getNom());
	    cout.setNomLong(cin.getNomLong());
	    cout.setIso2(cin.getIso2());
	    cout.setIso3(cin.getIso3());
	    cout.setContinentId(cin.getContinentId());
	    cout.setRegionId(cin.getRegionId());
	    return cout;
	}
    }
}
