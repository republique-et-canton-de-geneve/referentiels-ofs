package ch.ge.cti.ct.referentiels.communes.service.impl;

import java.text.Normalizer;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ch.ge.cti.ct.referentiels.communes.data.ReferentielDataSingleton;
import ch.ge.cti.ct.referentiels.communes.model.Canton;
import ch.ge.cti.ct.referentiels.communes.model.Commune;
import ch.ge.cti.ct.referentiels.communes.model.District;
import ch.ge.cti.ct.referentiels.communes.model.ReferentielCommunes;
import ch.ge.cti.ct.referentiels.communes.service.ReferentielCommunesServiceAble;
import ch.ge.cti.ct.referentiels.ofs.ReferentielOfsException;

import com.google.common.base.Function;
import com.google.common.base.Predicate;
import com.google.common.collect.FluentIterable;

/**
 * Implémentation POJO du service
 * 
 * @author desmazieresj
 * 
 */
public enum ReferentielCommunesService implements
	ReferentielCommunesServiceAble {
    instance;

    /** logger SLF4j */
    private static final Logger LOG = LoggerFactory
	    .getLogger(ReferentielCommunesService.class);

    @Override
    public ReferentielCommunes getReferentiel() throws ReferentielOfsException {
	return ReferentielDataSingleton.instance.getData();
    }

    @Override
    public List<Canton> getCantons() throws ReferentielOfsException {
	LOG.debug("getCantons()");
	return getCantons(new Date());
    }

    @Override
    public Canton getCanton(final String codeCanton)
	    throws ReferentielOfsException {
	LOG.debug("getCanton(codeCanton='{}')", codeCanton);
	if (StringUtils.isBlank(codeCanton)) {
	    return null;
	}
	return extractCanton(codeCanton, null).first().orNull();
    }

    @Override
    public List<District> getDistricts(final String codeCanton)
	    throws ReferentielOfsException {
	LOG.debug("getDistricts(codeCanton='{}')", codeCanton);
	return getDistricts(codeCanton, new Date());
    }

    @Override
    public District getDistrict(final int idDistrict)
	    throws ReferentielOfsException {
	LOG.debug("getDistricts(idDistrict='{}')", idDistrict);
	if (idDistrict <= 0) {
	    return null;
	}
	return extractDistrict(idDistrict, null).first().orNull();
    }

    @Override
    public List<Commune> getCommunesByDistrict(final int idDistrict)
	    throws ReferentielOfsException {
	LOG.debug("getCommunesByDistrict(idDistrict='{}')", idDistrict);
	return getCommunesByDistrict(idDistrict, new Date());
    }

    @Override
    public List<Commune> getCommunesByCanton(final String codeCanton)
	    throws ReferentielOfsException {
	LOG.debug("getCommunesByCanton(codeCanton='{}')", codeCanton);
	return getCommunesByCanton(codeCanton, new Date());
    }

    @Override
    public Commune getCommune(final int idCommune)
	    throws ReferentielOfsException {
	LOG.debug("getCommune(idCommune='{}')", idCommune);
	if (idCommune <= 0) {
	    return null;
	}
	return FluentIterable
		.from(ReferentielDataSingleton.instance.getData().getCanton())
		.transformAndConcat(new ExtractDistrictFunction())
		.transformAndConcat(new ExtractCommuneFunction())
		.filter(new Predicate<Commune>() {
		    @Override
		    public boolean apply(final Commune commune) {
			return commune.getId() == idCommune;
		    }
		}).first().orNull();
    }

    @Override
    public List<Commune> searchCommune(String critere)
	    throws ReferentielOfsException {
	LOG.debug("searchCommune(critere='{}')", critere);
	return searchCommune(critere, new Date());
    }

    // ==================================================================================================================================================================
    // ==================================================================================================================================================================

    @Override
    public List<Canton> getCantons(final Date dateValid)
	    throws ReferentielOfsException {
	LOG.debug("getCantons(dateValid='{}')", dateValid);
	// on retourne une copie de la liste des cantons
	return FluentIterable
		.from(ReferentielDataSingleton.instance.getData().getCanton())
		.filter(new CantonValidPredicate(dateValid))
		.toSortedList(new CantonComparator());
    }

    @Override
    public List<District> getDistricts(final String codeCanton,
	    final Date dateValid) throws ReferentielOfsException {
	LOG.debug("getDistricts(codeCanton='{}', dateValid='{}')", codeCanton,
		dateValid);
	if (StringUtils.isBlank(codeCanton)) {
	    return null;
	}
	// liste de 1 canton ou vide (avec test de date de validité du canton)
	final FluentIterable<Canton> cantons = extractCanton(codeCanton,
		dateValid);

	return FluentIterable.from(cantons.toList())
		.transformAndConcat(new ExtractDistrictFunction())
		.filter(new DistrictValidPredicate(dateValid))
		.toSortedList(new DistrictComparator());
    }

    @Override
    public List<Commune> getCommunesByDistrict(final int idDistrict,
	    final Date dateValid) throws ReferentielOfsException {
	LOG.debug("getCommunesByDistrict(idDistrict='{}', dateValid='{}')",
		idDistrict, dateValid);
	if (idDistrict <= 0) {
	    return null;
	}

	// liste de 1 district ou vide
	final FluentIterable<District> districts = extractDistrict(idDistrict,
		dateValid);

	// extraction de la liste des communes
	return FluentIterable.from(districts.toList())
		.filter(new DistrictValidPredicate(dateValid))
		.transformAndConcat(new ExtractCommuneFunction())
		.filter(new CommuneValidPredicate(dateValid))
		.toSortedList(new CommuneComparator());
    }

    @Override
    public List<Commune> getCommunesByCanton(final String codeCanton,
	    final Date dateValid) throws ReferentielOfsException {
	LOG.debug("getCommunesByCanton(codeCanton='{}', dateValid='{}')",
		codeCanton, dateValid);
	if (StringUtils.isBlank(codeCanton)) {
	    return null;
	}
	// liste de 1 canton ou vide
	final FluentIterable<Canton> cantons = extractCanton(codeCanton,
		dateValid);

	final FluentIterable<District> districts = FluentIterable
		.from(cantons.toList())
		.transformAndConcat(new ExtractDistrictFunction())
		.filter(new DistrictValidPredicate(dateValid));

	return FluentIterable.from(districts.toList())
		.transformAndConcat(new ExtractCommuneFunction())
		.filter(new CommuneValidPredicate(dateValid))
		.toSortedList(new CommuneComparator());
    }

    @Override
    public List<Commune> searchCommune(final String critere,
	    final Date dateValid) throws ReferentielOfsException {
	LOG.debug("searchCommune(critere='{}', dateValid='{}')", critere,
		dateValid);
	if (StringUtils.isBlank(critere)) {
	    return null;
	}
	return FluentIterable
		.from(ReferentielDataSingleton.instance.getData().getCanton())
		.transformAndConcat(new ExtractDistrictFunction())
		.transformAndConcat(new ExtractCommuneFunction())
		.filter(new CommuneValidPredicate(dateValid))
		.filter(new CommuneNameMatcherPredicate(critere))
		.toSortedList(new CommuneComparator());

    }

    // ==================================================================================================================================================================
    // ==================================================================================================================================================================

    /**
     * Extrait le canton du référentiel
     * 
     * @param codeCanton
     *            identifiant du canton
     * @return un itérateur sur le canton (1 élément ou vide)
     * @throws ReferentielOfsException
     *             exception d'accès au référentiel
     */
    private FluentIterable<Canton> extractCanton(final String codeCanton,
	    final Date dateValid) throws ReferentielOfsException {
	return FluentIterable
		.from(ReferentielDataSingleton.instance.getData().getCanton())
		.filter(new Predicate<Canton>() {
		    @Override
		    public boolean apply(final Canton canton) {
			return canton.getCode().equals(codeCanton);
		    }
		}).filter(new CantonValidPredicate(dateValid));
    }

    /**
     * Extrait le district du référentiel
     * 
     * @param idDistrict
     *            identifiant du district
     * @return un itérateur sur le district (1 élément ou vide)
     * @throws ReferentielOfsException
     *             exception d'accès au référentiel
     */
    private FluentIterable<District> extractDistrict(final int idDistrict,
	    final Date dateValid) throws ReferentielOfsException {
	return FluentIterable
		.from(ReferentielDataSingleton.instance.getData().getCanton())
		.filter(new CantonValidPredicate(dateValid))
		.transformAndConcat(new ExtractDistrictFunction())
		.filter(new Predicate<District>() {
		    @Override
		    public boolean apply(final District district) {
			return district.getId() == idDistrict;
		    }
		}).filter(new DistrictValidPredicate(dateValid));
    }

    /**
     * Comparateur pour le tri des listes des cantons
     * 
     */
    private class CantonComparator implements Comparator<Canton> {
	@Override
	public int compare(final Canton c0, final Canton c1) {
	    return c0.getCode().compareTo(c1.getCode());
	}
    }

    /**
     * Comparateur pour le tri des listes des districts
     * 
     */
    private class DistrictComparator implements Comparator<District> {
	@Override
	public int compare(final District c0, final District c1) {
	    return c0.getNom().compareTo(c1.getNom());
	}
    }

    /**
     * Comparateur pour le tri des listes des communes
     * 
     */
    private class CommuneComparator implements Comparator<Commune> {
	@Override
	public int compare(final Commune c0, final Commune c1) {
	    return c0.getNom().compareTo(c1.getNom());
	}
    }

    private class CantonValidPredicate implements Predicate<Canton> {
	private final Date dateValid;

	public CantonValidPredicate(final Date dateValid) {
	    this.dateValid = dateValid;
	}

	@Override
	public boolean apply(final Canton canton) {
	    return (dateValid == null)
		    || ((canton.getValidFrom() == null || canton.getValidFrom()
			    .getTime() <= dateValid.getTime()) && (canton
			    .getValidTo() == null || canton.getValidTo()
			    .getTime() >= dateValid.getTime()));
	}
    }

    private class DistrictValidPredicate implements Predicate<District> {
	private final Date dateValid;

	public DistrictValidPredicate(final Date dateValid) {
	    this.dateValid = dateValid;
	}

	@Override
	public boolean apply(final District district) {
	    return (dateValid == null)
		    || ((district.getValidFrom() == null || district
			    .getValidFrom().getTime() <= dateValid.getTime()) && (district
			    .getValidTo() == null || district.getValidTo()
			    .getTime() >= dateValid.getTime()));
	}
    }

    private class CommuneValidPredicate implements Predicate<Commune> {
	private final Date dateValid;

	public CommuneValidPredicate(final Date dateValid) {
	    this.dateValid = dateValid;
	}

	@Override
	public boolean apply(final Commune commune) {
	    return (dateValid == null)
		    || ((commune.getValidFrom() == null || commune
			    .getValidFrom().getTime() <= dateValid.getTime()) && (commune
			    .getValidTo() == null || commune.getValidTo()
			    .getTime() >= dateValid.getTime()));
	}
    }

    private class CommuneNameMatcherPredicate implements Predicate<Commune> {
	private static final String NORMALIZER_SEARCH = "[^\\p{ASCII}]";
	private static final String NORMALIZER_REPLACE = "";
	private final String matcher;

	public CommuneNameMatcherPredicate(final String matcher) {
	    this.matcher = matcher;
	}

	@Override
	public boolean apply(final Commune commune) {
	    return normalize(matcher).equals(
		    normalize(commune.getNom().substring(
			    0,
			    Math.min(commune.getNom().length(),
				    matcher.length()))));
	}

	/**
	 * la comparaison se fait sans tenir compte des accents et caractères
	 * spéciaux
	 */
	private String normalize(final String value) {
	    return Normalizer.normalize(value, Normalizer.Form.NFD)
		    .replaceAll(NORMALIZER_SEARCH, NORMALIZER_REPLACE)
		    .toLowerCase();
	}
    }

    private class ExtractDistrictFunction implements
	    Function<Canton, Iterable<? extends District>> {
	@Override
	public Iterable<? extends District> apply(final Canton canton) {
	    return canton.getDistrict();
	}
    }

    private class ExtractCommuneFunction implements
	    Function<District, Iterable<? extends Commune>> {
	@Override
	public Iterable<? extends Commune> apply(final District district) {
	    return district.getCommune();
	}
    }
}
