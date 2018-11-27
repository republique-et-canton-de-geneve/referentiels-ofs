package ch.ge.cti.ct.referentiels.pays.service.impl;

import java.util.LinkedList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ch.ge.cti.ct.referentiels.ofs.ReferentielOfsException;
import ch.ge.cti.ct.referentiels.ofs.processing.IdFilterPredicate;
import ch.ge.cti.ct.referentiels.ofs.processing.NomComparator;
import ch.ge.cti.ct.referentiels.ofs.processing.NomRegexpMatcherPredicate;
import ch.ge.cti.ct.referentiels.ofs.processing.NomStringMatcherPredicate;
import ch.ge.cti.ct.referentiels.pays.data.ReferentielDataSingleton;
import ch.ge.cti.ct.referentiels.pays.model.Continent;
import ch.ge.cti.ct.referentiels.pays.model.Pays;
import ch.ge.cti.ct.referentiels.pays.model.ReferentielPaysTerritoires;
import ch.ge.cti.ct.referentiels.pays.model.Region;
import ch.ge.cti.ct.referentiels.pays.service.ReferentielPaysTerritoiresServiceAble;

import com.google.common.base.Function;
import com.google.common.base.Predicate;
import com.google.common.collect.FluentIterable;

/**
 * Implémentation POJO du service
 * 
 * @author desmazieresj
 * 
 */
public enum ReferentielPaysTerritoiresService implements ReferentielPaysTerritoiresServiceAble {

	INSTANCE;

    /** logger SLF4j */
    private static final Logger LOG = LoggerFactory
	    .getLogger(ReferentielPaysTerritoiresService.class);

    @Override
    public ReferentielPaysTerritoires getReferentiel()
	    throws ReferentielOfsException {
	return ReferentielDataSingleton.INSTANCE.getData();
    }

    @Override
    public List<Continent> getContinents() throws ReferentielOfsException {
	LOG.debug("getContinents()");
	// on retourne une copie de la liste des continents
	return FluentIterable.from(
		ReferentielDataSingleton.INSTANCE.getData().getContinent())
		.toSortedList(nomComparator);
    }

    @Override
    public Continent getContinent(final int continentId)
	    throws ReferentielOfsException {
	LOG.debug("getContinent(continentId='{}')", continentId);
	if (continentId <= 0) {
	    return null;
	}
	return extractContinent(continentId).first().orNull();
    }

    @Override
    public List<Region> getRegions(final int continentId)
	    throws ReferentielOfsException {
	LOG.debug("getRegions(continentId='{}')", continentId);
	if (continentId <= 0) {
	    return new LinkedList<Region>();
	}
	final FluentIterable<Continent> continents = extractContinent(continentId);

	return continents.transformAndConcat(extractRegionFunction)
		.toSortedList(nomComparator);
    }

    @Override
    public Region getRegion(final int regionId) throws ReferentielOfsException {
	LOG.debug("getRegions(regionId='{}')", regionId);
	if (regionId <= 0) {
	    return null;
	}
	return extractRegion(regionId).first().orNull();
    }

    @Override
    public List<Pays> getPaysByRegion(final int regionId)
	    throws ReferentielOfsException {
	LOG.debug("getPaysByRegion(regionId='{}')", regionId);
	if (regionId <= 0) {
	    return new LinkedList<Pays>();
	}

	final FluentIterable<Region> regions = extractRegion(regionId);

	// extraction de la liste des pays
	return regions.transformAndConcat(extractPaysFunction).toSortedList(
		nomComparator);
    }

    @Override
    public List<Pays> getPaysByContinent(final int continentId)
	    throws ReferentielOfsException {
	LOG.debug("getPaysByContinent(continentId='{}')", continentId);
	if (continentId <= 0) {
	    return new LinkedList<Pays>();
	}
	final FluentIterable<Continent> continents = extractContinent(continentId);

	final FluentIterable<Region> regions = continents
		.transformAndConcat(extractRegionFunction);

	return regions.transformAndConcat(extractPaysFunction).toSortedList(
		nomComparator);
    }

    @Override
    public List<Region> getRegions() throws ReferentielOfsException {
	LOG.debug("getRegions()");
	return FluentIterable
		.from(ReferentielDataSingleton.INSTANCE.getData()
			.getContinent())
		.transformAndConcat(extractRegionFunction)
		.toSortedList(nomComparator);
    }

    @Override
    public List<Pays> getPays() throws ReferentielOfsException {
	LOG.debug("getPays()");
	return FluentIterable
		.from(ReferentielDataSingleton.INSTANCE.getData()
			.getContinent())
		.transformAndConcat(extractRegionFunction)
		.transformAndConcat(extractPaysFunction)
		.toSortedList(nomComparator);
    }

    @Override
    public Pays getPaysByISO2(final String iso2) throws ReferentielOfsException {
	LOG.debug("getPaysByISO2(iso2='{}')", iso2);
	if (StringUtils.isBlank(iso2)) {
	    return null;
	}
	return FluentIterable
		.from(ReferentielDataSingleton.INSTANCE.getData()
			.getContinent())
		.transformAndConcat(extractRegionFunction)
		.transformAndConcat(extractPaysFunction)
		.filter(new Predicate<Pays>() {
		    @Override
		    public boolean apply(final Pays pays) {
			return pays.getIso2().equals(iso2);
		    }
		}).first().orNull();
    }

    @Override
    public Pays getPaysByISO3(final String iso3) throws ReferentielOfsException {
	LOG.debug("getPaysByISO3(iso3='{}')", iso3);
	if (StringUtils.isBlank(iso3)) {
	    return null;
	}
	return FluentIterable
		.from(ReferentielDataSingleton.INSTANCE.getData()
			.getContinent())
		.transformAndConcat(extractRegionFunction)
		.transformAndConcat(extractPaysFunction)
		.filter(new Predicate<Pays>() {
		    @Override
		    public boolean apply(final Pays pays) {
			return pays.getIso3().equals(iso3);
		    }
		}).first().orNull();
    }

    @Override
    public List<Pays> searchPays(final String critere)
	    throws ReferentielOfsException {
	LOG.debug("searchPays(critere='{}')", critere);
	if (StringUtils.isBlank(critere)) {
	    return new LinkedList<Pays>();
	}
	return FluentIterable
		.from(ReferentielDataSingleton.INSTANCE.getData()
			.getContinent())
		.transformAndConcat(extractRegionFunction)
		.transformAndConcat(extractPaysFunction)
		.filter(new NomStringMatcherPredicate(critere))
		.toSortedList(nomComparator);
    }

    @Override
    public List<Pays> searchPaysRegexp(final String critere)
	    throws ReferentielOfsException {
	LOG.debug("searchPays(critere='{}')", critere);
	if (StringUtils.isBlank(critere)) {
	    return new LinkedList<Pays>();
	}
	return FluentIterable
		.from(ReferentielDataSingleton.INSTANCE.getData()
			.getContinent())
		.transformAndConcat(extractRegionFunction)
		.transformAndConcat(extractPaysFunction)
		.filter(new NomRegexpMatcherPredicate(critere))
		.toSortedList(nomComparator);
    }

    // ==================================================================================================================================================================
    // ==================================================================================================================================================================

    private final NomComparator nomComparator = new NomComparator();

    /**
     * Extrait le continent du référentiel
     * 
     * @param continentId
     *            identifiant du continent
     * @return un itérateur sur le continent (1 élément ou vide)
     * @throws ReferentielOfsException
     *             exception d'accès au référentiel
     */
    private FluentIterable<Continent> extractContinent(final int continentId)
	    throws ReferentielOfsException {
	return FluentIterable.from(
		ReferentielDataSingleton.INSTANCE.getData().getContinent())
		.filter(new IdFilterPredicate(continentId));
    }

    /**
     * Extrait le region du référentiel
     * 
     * @param regionId
     *            identifiant du region
     * @return un itérateur sur le region (1 élément ou vide)
     * @throws ReferentielOfsException
     *             exception d'accès au référentiel
     */
    private FluentIterable<Region> extractRegion(final int regionId)
	    throws ReferentielOfsException {
	return FluentIterable
		.from(ReferentielDataSingleton.INSTANCE.getData()
			.getContinent())
		.transformAndConcat(extractRegionFunction)
		.filter(new IdFilterPredicate(regionId));
    }

    private final Function<Continent, Iterable<? extends Region>> extractRegionFunction = new Function<Continent, Iterable<? extends Region>>() {
	@Override
	public Iterable<? extends Region> apply(final Continent continent) {
	    return continent.getRegion();
	}
    };

    private final Function<Region, Iterable<? extends Pays>> extractPaysFunction = new Function<Region, Iterable<? extends Pays>>() {
	@Override
	public Iterable<? extends Pays> apply(final Region region) {
	    return region.getPays();
	}
    };

}
