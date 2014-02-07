package ch.ge.cti.ct.referentiels.communes.service.impl;

import java.text.Normalizer;
import java.util.Comparator;
import java.util.Date;
import java.util.LinkedList;
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
 * Impl�mentation POJO du service
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
	return getDistrict(idDistrict, new Date());
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
	return getCommune(idCommune, new Date());
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
		.toSortedList(cantonComparator);
    }

    @Override
    public Canton getCanton(final String codeCanton, final Date dateValid)
	    throws ReferentielOfsException {
	LOG.debug("getCanton(codeCanton='{}', dateValid='{}')", codeCanton,
		dateValid);
	if (StringUtils.isBlank(codeCanton)) {
	    return null;
	}
	return extractCanton(codeCanton, dateValid).first().orNull();
    }

    @Override
    public List<District> getDistricts(final String codeCanton,
	    final Date dateValid) throws ReferentielOfsException {
	LOG.debug("getDistricts(codeCanton='{}', dateValid='{}')", codeCanton,
		dateValid);
	if (StringUtils.isBlank(codeCanton)) {
	    return new LinkedList<District>();
	}
	// liste de 1 canton ou vide (avec test de date de validit� du canton)
	final FluentIterable<Canton> cantons = extractCanton(codeCanton,
		dateValid);

	// on utilise un tableau car les variables sont pass�es par valeur
	return cantons.transformAndConcat(extractDistrictFunction)
		.filter(new DistrictValidPredicate(dateValid))
		.toSortedList(districtComparator);
    }

    @Override
    public District getDistrict(final int idDistrict, final Date dateValid)
	    throws ReferentielOfsException {
	LOG.debug("getDistricts(idDistrict='{}', dateValid='{}')", idDistrict,
		dateValid);
	if (idDistrict <= 0) {
	    return null;
	}
	return extractDistrict(idDistrict, dateValid).first().orNull();
    }

    @Override
    public List<Commune> getCommunesByDistrict(final int idDistrict,
	    final Date dateValid) throws ReferentielOfsException {
	LOG.debug("getCommunesByDistrict(idDistrict='{}', dateValid='{}')",
		idDistrict, dateValid);
	if (idDistrict <= 0) {
	    return new LinkedList<Commune>();
	}

	// extraction de la liste des communes
	return extractDistrict(idDistrict, dateValid)
		.filter(new DistrictValidPredicate(dateValid))
		.transformAndConcat(extractCommuneFunction)
		.filter(new CommuneValidPredicate(dateValid))
		.toSortedList(communeComparator);
    }

    @Override
    public List<Commune> getCommunesByCanton(final String codeCanton,
	    final Date dateValid) throws ReferentielOfsException {
	LOG.debug("getCommunesByCanton(codeCanton='{}', dateValid='{}')",
		codeCanton, dateValid);
	if (StringUtils.isBlank(codeCanton)) {
	    return new LinkedList<Commune>();
	}
	// liste de 1 canton ou vide
	final FluentIterable<Canton> cantons = extractCanton(codeCanton,
		dateValid);

	final FluentIterable<District> districts = cantons.transformAndConcat(
		extractDistrictFunction).filter(
		new DistrictValidPredicate(dateValid));

	return districts.transformAndConcat(extractCommuneFunction)
		.filter(new CommuneValidPredicate(dateValid))
		.toSortedList(communeComparator);
    }

    @Override
    public Commune getCommune(final int idCommune, final Date dateValid)
	    throws ReferentielOfsException {
	LOG.debug("getCommune(idCommune='{}', dateValid='{}')", idCommune,
		dateValid);
	if (idCommune <= 0) {
	    return null;
	}
	return FluentIterable
		.from(ReferentielDataSingleton.instance.getData().getCanton())
		.transformAndConcat(extractDistrictFunction)
		.transformAndConcat(extractCommuneFunction)
		.filter(new Predicate<Commune>() {
		    @Override
		    public boolean apply(final Commune commune) {
			return commune.getId() == idCommune;
		    }
		}).filter(new CommuneValidPredicate(dateValid)).first()
		.orNull();
    }

    @Override
    public List<Commune> searchCommune(final String critere,
	    final Date dateValid) throws ReferentielOfsException {
	LOG.debug("searchCommune(critere='{}', dateValid='{}')", critere,
		dateValid);
	if (StringUtils.isBlank(critere)) {
	    return new LinkedList<Commune>();
	}
	return FluentIterable
		.from(ReferentielDataSingleton.instance.getData().getCanton())
		.transformAndConcat(extractDistrictFunction)
		.transformAndConcat(extractCommuneFunction)
		.filter(new CommuneValidPredicate(dateValid))
		.filter(new CommuneNameMatcherPredicate(critere))
		.toSortedList(communeComparator);

    }

    // ==================================================================================================================================================================
    // ==================================================================================================================================================================

    /**
     * Extrait le canton du r�f�rentiel
     * 
     * @param codeCanton
     *            identifiant du canton
     * @return un it�rateur sur le canton (1 �l�ment ou vide)
     * @throws ReferentielOfsException
     *             exception d'acc�s au r�f�rentiel
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
     * Extrait le district du r�f�rentiel
     * 
     * @param idDistrict
     *            identifiant du district
     * @return un it�rateur sur le district (1 �l�ment ou vide)
     * @throws ReferentielOfsException
     *             exception d'acc�s au r�f�rentiel
     */
    private FluentIterable<District> extractDistrict(final int idDistrict,
	    final Date dateValid) throws ReferentielOfsException {
	return FluentIterable
		.from(ReferentielDataSingleton.instance.getData().getCanton())
		.filter(new CantonValidPredicate(dateValid))
		.transformAndConcat(extractDistrictFunction)
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
    private Comparator<Canton> cantonComparator = new Comparator<Canton>() {
	@Override
	public int compare(final Canton c0, final Canton c1) {
	    return c0.getCode().compareTo(c1.getCode());
	}
    };

    /**
     * Comparateur pour le tri des listes des districts
     * 
     */
    private Comparator<District> districtComparator = new Comparator<District>() {
	@Override
	public int compare(final District c0, final District c1) {
	    return c0.getNom().compareTo(c1.getNom());
	}
    };

    /**
     * Comparateur pour le tri des listes des communes
     * 
     */
    private Comparator<Commune> communeComparator = new Comparator<Commune>() {
	@Override
	public int compare(final Commune c0, final Commune c1) {
	    return c0.getNom().compareTo(c1.getNom());
	}
    };

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
	    this.matcher = matcher.trim();
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
	 * la comparaison se fait sans tenir compte des accents et caract�res
	 * sp�ciaux
	 */
	private String normalize(final String value) {
	    return Normalizer.normalize(value, Normalizer.Form.NFD)
		    .replaceAll(NORMALIZER_SEARCH, NORMALIZER_REPLACE)
		    .toLowerCase();
	}
    }

    private Function<Canton, Iterable<? extends District>> extractDistrictFunction = new Function<Canton, Iterable<? extends District>>() {
	@Override
	public Iterable<? extends District> apply(final Canton canton) {
	    return canton.getDistrict();
	}
    };

    private Function<District, Iterable<? extends Commune>> extractCommuneFunction = new Function<District, Iterable<? extends Commune>>() {
	@Override
	public Iterable<? extends Commune> apply(final District district) {
	    return district.getCommune();
	}
    };
}
