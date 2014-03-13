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

import ch.ge.cti.ct.referentiels.ofs.ReferentielOfsException;
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
@WebService(name = "referentiel-pays-JAXWS", serviceName = "referentiel-pays", portName = "referentiel-pays", targetNamespace = "http://ch.ge.cti.ct.referentiels.pays/referentiel-pays")
@WebContext(contextRoot = "/referentiels-ofs/territoires", urlPattern = "/referentiel-pays")
@SOAPBinding(style = Style.DOCUMENT, use = Use.LITERAL)
@Interceptors({ ReferentielStatsIntercept.class,
	ReferentielOfsCacheIntercept.class })
public class ReferentielPaysTerritoiresSEI {

    /** Référence sur l'implémentation */
    private final ReferentielPaysTerritoiresServiceAble service = ReferentielPaysTerritoiresService.instance;

    /** logger SLF4j */
    private static final Logger LOG = LoggerFactory
	    .getLogger(ReferentielPaysTerritoiresSEI.class);

    @WebMethod(operationName = "getContinents", action = "getContinents")
    @WebResult(name = "continent")
    @Cachable(name = "continents", size = Cachable.SMALL)
    public List<ContinentWS> getContinents() throws ReferentielOfsException {
	LOG.debug("getContinents()");
	try {
	    return FluentIterable.from(service.getContinents())
		    .transform(new ContinentConvert()).toList();
	} catch (final Exception e) {
	    throw processException(e);
	}
    }

    @WebMethod(operationName = "getContinent", action = "getContinent")
    @WebResult(name = "continent")
    public ContinentWS getContinent(
	    @WebParam(name = "continent") final int continentId)
	    throws ReferentielOfsException {
	LOG.debug("getContinent(continent='{}')", continentId);
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

    @WebMethod(operationName = "getRegionsByContinent", action = "getRegionsByContinent")
    @WebResult(name = "region")
    @Cachable(name = "regions", size = Cachable.MEDIUM)
    public List<RegionWS> getRegionsByContinent(
	    @WebParam(name = "continent") final int continentId)
	    throws ReferentielOfsException {
	LOG.debug("getRegions(continent='{}')", continentId);
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

    @WebMethod(operationName = "getRegions", action = "getRegions")
    @WebResult(name = "region")
    @Cachable(name = "regions", size = Cachable.MEDIUM)
    public List<RegionWS> getRegions() throws ReferentielOfsException {
	LOG.debug("getRegions()");
	try {
	    return FluentIterable.from(service.getRegions())
		    .transform(new RegionConvert()).toList();
	} catch (final Exception e) {
	    throw processException(e);
	}
    }

    @WebMethod(operationName = "getRegion", action = "getRegion")
    @WebResult(name = "region")
    public RegionWS getRegion(@WebParam(name = "region") final int regionId)
	    throws ReferentielOfsException {
	LOG.debug("getRegions(region='{}')", regionId);
	if (regionId <= 0) {
	    return null;
	}
	try {
	    return new RegionConvert().apply(service.getRegion(regionId));
	} catch (final Exception e) {
	    throw processException(e);
	}
    }

    @WebMethod(operationName = "getPaysByRegion", action = "getPaysByRegion")
    @WebResult(name = "pays")
    @Cachable(name = "pays", size = Cachable.LARGE)
    public List<PaysWS> getPaysByRegion(
	    @WebParam(name = "region") final int regionId)
	    throws ReferentielOfsException {
	LOG.debug("getPaysByRegion(region='{}')", regionId);
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

    @WebMethod(operationName = "getPaysByContinent", action = "getPaysByContinent")
    @WebResult(name = "pays")
    @Cachable(name = "pays", size = Cachable.LARGE)
    public List<PaysWS> getPaysByContinent(
	    @WebParam(name = "continent") final int continentId)
	    throws ReferentielOfsException {
	LOG.debug("getPaysByContinent(continent='{}')", continentId);
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

    @WebMethod(operationName = "getPays", action = "getPays")
    @WebResult(name = "pays")
    @Cachable(name = "pays", size = Cachable.LARGE)
    public List<PaysWS> getPays() throws ReferentielOfsException {
	LOG.debug("getPays()");
	try {
	    return FluentIterable.from(service.getPays())
		    .transform(new PaysConvert()).toList();
	} catch (final Exception e) {
	    throw processException(e);
	}
    }

    @WebMethod(operationName = "getPaysByIso2", action = "getPaysByIso2")
    @WebResult(name = "pays")
    public PaysWS getPaysByIso2(@WebParam(name = "iso2") final String iso2)
	    throws ReferentielOfsException {
	LOG.debug("getPaysByIso2(iso2='{}')", iso2);
	if (StringUtils.isBlank(iso2)) {
	    return null;
	}
	try {
	    return new PaysConvert().apply(service.getPaysByISO2(iso2));
	} catch (final Exception e) {
	    throw processException(e);
	}
    }

    @WebMethod(operationName = "getPaysByIso3", action = "getPaysByIso3")
    @WebResult(name = "pays")
    public PaysWS getPaysByIso3(@WebParam(name = "iso3") final String iso3)
	    throws ReferentielOfsException {
	LOG.debug("getPaysByIso3(iso3='{}')", iso3);
	if (StringUtils.isBlank(iso3)) {
	    return null;
	}
	try {
	    return new PaysConvert().apply(service.getPaysByISO3(iso3));
	} catch (final Exception e) {
	    throw processException(e);
	}
    }

    @WebMethod(operationName = "searchPays", action = "searchPays")
    @WebResult(name = "pays")
    public List<PaysWS> searchPays(
	    @WebParam(name = "critere") final String critere)
	    throws ReferentielOfsException {
	LOG.debug("searchPays({})", critere);
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

    @WebMethod(operationName = "searchPaysRegexp", action = "searchPaysRegexp")
    @WebResult(name = "pays")
    public List<PaysWS> searchPaysRegexp(
	    @WebParam(name = "critere") final String critere)
	    throws ReferentielOfsException {
	LOG.debug("searchPaysRegexp({})", critere);
	if (StringUtils.isBlank(critere)) {
	    return new LinkedList<PaysWS>();
	}
	try {
	    return FluentIterable.from(service.searchPaysRegexp(critere))
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
