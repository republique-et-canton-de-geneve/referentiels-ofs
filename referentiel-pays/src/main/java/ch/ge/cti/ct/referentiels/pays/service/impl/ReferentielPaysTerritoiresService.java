package ch.ge.cti.ct.referentiels.pays.service.impl;

import java.text.Normalizer;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ch.ge.cti.ct.referentiels.ofs.ReferentielOfsException;
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
 * Impl�mentation POJO du service
 * 
 * @author desmazieresj
 * 
 */
public enum ReferentielPaysTerritoiresService implements
	ReferentielPaysTerritoiresServiceAble {
    instance;

    /** logger SLF4j */
    private static final Logger LOG = LoggerFactory
	    .getLogger(ReferentielPaysTerritoiresService.class);

    @Override
    public ReferentielPaysTerritoires getReferentiel()
	    throws ReferentielOfsException {
	return ReferentielDataSingleton.instance.getData();
    }

    @Override
    public List<Continent> getContinents() throws ReferentielOfsException {
	LOG.debug("getContinents()");
	// on retourne une copie de la liste des continents
	return FluentIterable.from(
		ReferentielDataSingleton.instance.getData().getContinent())
		.toSortedList(new ContinentComparator());
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

	return continents.transformAndConcat(new ExtractRegionFunction())
		.toSortedList(new RegionComparator());
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
	return regions.transformAndConcat(new ExtractPaysFunction())
		.toSortedList(new PaysComparator());
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
		.transformAndConcat(new ExtractRegionFunction());

	return regions.transformAndConcat(new ExtractPaysFunction())
		.toSortedList(new PaysComparator());
    }

    @Override
    public List<Region> getRegions() throws ReferentielOfsException {
	LOG.debug("getRegions()");
	return FluentIterable
		.from(ReferentielDataSingleton.instance.getData()
			.getContinent())
		.transformAndConcat(new ExtractRegionFunction())
		.toSortedList(new RegionComparator());
    }

    @Override
    public List<Pays> getPays() throws ReferentielOfsException {
	LOG.debug("getPays()");
	return FluentIterable
		.from(ReferentielDataSingleton.instance.getData()
			.getContinent())
		.transformAndConcat(new ExtractRegionFunction())
		.transformAndConcat(new ExtractPaysFunction())
		.toSortedList(new PaysComparator());
    }

    @Override
    public Pays getPaysByISO2(final String iso2) throws ReferentielOfsException {
	LOG.debug("getPaysByISO2(iso2='{}')", iso2);
	if (StringUtils.isBlank(iso2)) {
	    return null;
	}
	return FluentIterable
		.from(ReferentielDataSingleton.instance.getData()
			.getContinent())
		.transformAndConcat(new ExtractRegionFunction())
		.transformAndConcat(new ExtractPaysFunction())
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
		.from(ReferentielDataSingleton.instance.getData()
			.getContinent())
		.transformAndConcat(new ExtractRegionFunction())
		.transformAndConcat(new ExtractPaysFunction())
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
	final String critereN = normalize(critere.trim());
	return FluentIterable
		.from(ReferentielDataSingleton.instance.getData()
			.getContinent())
		.transformAndConcat(new ExtractRegionFunction())
		.transformAndConcat(new ExtractPaysFunction())
		.filter(new PaysNameStringMatcherPredicate(critereN))
		.toSortedList(new PaysComparator());
    }

    @Override
    public List<Pays> searchPaysRegexp(final String critere)
	    throws ReferentielOfsException {
	LOG.debug("searchPays(critere='{}')", critere);
	if (StringUtils.isBlank(critere)) {
	    return new LinkedList<Pays>();
	}
	final Pattern critereN = Pattern.compile(normalize(critere.trim()));
	return FluentIterable
		.from(ReferentielDataSingleton.instance.getData()
			.getContinent())
		.transformAndConcat(new ExtractRegionFunction())
		.transformAndConcat(new ExtractPaysFunction())
		.filter(new PaysNameRegexpMatcherPredicate(critereN))
		.toSortedList(new PaysComparator());
    }

    // ==================================================================================================================================================================
    // ==================================================================================================================================================================

    /**
     * Extrait le continent du r�f�rentiel
     * 
     * @param continentId
     *            identifiant du continent
     * @return un it�rateur sur le continent (1 �l�ment ou vide)
     * @throws ReferentielOfsException
     *             exception d'acc�s au r�f�rentiel
     */
    private FluentIterable<Continent> extractContinent(final int continentId)
	    throws ReferentielOfsException {
	return FluentIterable.from(
		ReferentielDataSingleton.instance.getData().getContinent())
		.filter(new Predicate<Continent>() {
		    @Override
		    public boolean apply(final Continent continent) {
			return continent.getId() == continentId;
		    }
		});
    }

    /**
     * Extrait le region du r�f�rentiel
     * 
     * @param regionId
     *            identifiant du region
     * @return un it�rateur sur le region (1 �l�ment ou vide)
     * @throws ReferentielOfsException
     *             exception d'acc�s au r�f�rentiel
     */
    private FluentIterable<Region> extractRegion(final int regionId)
	    throws ReferentielOfsException {
	return FluentIterable
		.from(ReferentielDataSingleton.instance.getData()
			.getContinent())
		.transformAndConcat(new ExtractRegionFunction())
		.filter(new Predicate<Region>() {
		    @Override
		    public boolean apply(final Region region) {
			return region.getId() == regionId;
		    }
		});
    }

    private static final Pattern NORMALIZER_REGEX = Pattern
	    .compile("[^\\p{ASCII}]");
    private static final String NORMALIZER_REPLACE = "";

    /**
     * la comparaison se fait sans tenir compte des accents et caract�res
     * sp�ciaux
     */
    protected String normalize(final String value) {
	return NORMALIZER_REGEX
		.matcher(Normalizer.normalize(value, Normalizer.Form.NFD))
		.replaceAll(NORMALIZER_REPLACE).toLowerCase();
    }

    /**
     * Matcher exact sur le nom
     * 
     */
    private class PaysNameStringMatcherPredicate implements Predicate<Pays> {
	private final String matcher;

	public PaysNameStringMatcherPredicate(final String matcher) {
	    this.matcher = matcher;
	}

	@Override
	public boolean apply(final Pays pays) {
	    return matcher.equals(normalize(pays.getNom().substring(0,
		    Math.min(pays.getNom().length(), matcher.length()))));
	}
    }

    /**
     * Matcher par regexp sur le nom
     * 
     */
    private class PaysNameRegexpMatcherPredicate implements Predicate<Pays> {
	private final Pattern regexp;

	public PaysNameRegexpMatcherPredicate(final Pattern regexp) {
	    this.regexp = regexp;
	}

	@Override
	public boolean apply(final Pays pays) {
	    return regexp.matcher(normalize(pays.getNom())).find();
	}
    }

    /**
     * Comparateur pour le tri des listes des continents
     * 
     */
    private class ContinentComparator implements Comparator<Continent> {
	@Override
	public int compare(final Continent c0, final Continent c1) {
	    return c0.getId() - c1.getId();
	}
    }

    /**
     * Comparateur pour le tri des listes des regions
     * 
     */
    private class RegionComparator implements Comparator<Region> {
	@Override
	public int compare(final Region c0, final Region c1) {
	    return c0.getNom().compareTo(c1.getNom());
	}
    }

    /**
     * Comparateur pour le tri des listes des communes
     * 
     */
    private class PaysComparator implements Comparator<Pays> {
	@Override
	public int compare(final Pays c0, final Pays c1) {
	    return c0.getNom().compareTo(c1.getNom());
	    // certains pays n'ont pas de code iso
	    // return c0.getIso2().compareTo(c1.getIso2());
	}
    }

    private class ExtractRegionFunction implements
	    Function<Continent, Iterable<? extends Region>> {
	@Override
	public Iterable<? extends Region> apply(final Continent continent) {
	    return continent.getRegion();
	}
    }

    private class ExtractPaysFunction implements
	    Function<Region, Iterable<? extends Pays>> {
	@Override
	public Iterable<? extends Pays> apply(final Region region) {
	    return region.getPays();
	}
    }
}
