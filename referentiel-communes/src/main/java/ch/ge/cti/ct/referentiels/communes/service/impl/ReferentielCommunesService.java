package ch.ge.cti.ct.referentiels.communes.service.impl;

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
import ch.ge.cti.ct.referentiels.ofs.processing.IdFilterPredicate;
import ch.ge.cti.ct.referentiels.ofs.processing.NomComparator;
import ch.ge.cti.ct.referentiels.ofs.processing.NomRegexpMatcherPredicate;
import ch.ge.cti.ct.referentiels.ofs.processing.NomStringMatcherPredicate;

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
    public List<Commune> searchCommune(final String critere)
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
	// liste de 1 canton ou vide (avec test de date de validité du canton)
	final FluentIterable<Canton> cantons = extractCanton(codeCanton,
		dateValid);

	// on utilise un tableau car les variables sont passées par valeur
	return cantons.transformAndConcat(extractDistrictFunction)
		.filter(new DistrictValidPredicate(dateValid))
		.toSortedList(nomComparator);
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
		.toSortedList(nomComparator);
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
		.toSortedList(nomComparator);
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
		.filter(new IdFilterPredicate(idCommune))
		.filter(new CommuneValidPredicate(dateValid)).first().orNull();
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
		.filter(new NomStringMatcherPredicate(critere))
		.toSortedList(nomComparator);
    }

    @Override
    public List<Commune> searchCommuneRegexp(final String regexp)
	    throws ReferentielOfsException {
	LOG.debug("searchCommuneRegexp(regexp='{}')", regexp);
	return searchCommuneRegexp(regexp, new Date());
    }

    @Override
    public List<Commune> searchCommuneRegexp(final String regexp,
	    final Date dateValid) throws ReferentielOfsException {
	LOG.debug("searchCommuneRegexp(regexp='{}', dateValid='{}')", regexp,
		dateValid);
	if (StringUtils.isBlank(regexp)) {
	    return new LinkedList<Commune>();
	}
	return FluentIterable
		.from(ReferentielDataSingleton.instance.getData().getCanton())
		.transformAndConcat(extractDistrictFunction)
		.transformAndConcat(extractCommuneFunction)
		.filter(new CommuneValidPredicate(dateValid))
		.filter(new NomRegexpMatcherPredicate(regexp))
		.toSortedList(nomComparator);
    }

    // ==================================================================================================================================================================
    // ==================================================================================================================================================================

    private final NomComparator nomComparator = new NomComparator();

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
		.transformAndConcat(extractDistrictFunction)
		.filter(new IdFilterPredicate(idDistrict))
		.filter(new DistrictValidPredicate(dateValid));
    }

    /**
     * Comparateur pour le tri des listes des cantons
     * 
     */
    private final Comparator<Canton> cantonComparator = new Comparator<Canton>() {
	@Override
	public int compare(final Canton c0, final Canton c1) {
	    return c0.getCode().compareTo(c1.getCode());
	}
    };

    private static class CantonValidPredicate implements Predicate<Canton> {
	private final Date dateValid;

	/**
	 * Constructeur
	 * 
	 * @param dateValid
	 *            date de validation
	 */
	public CantonValidPredicate(final Date dateValid) {
	    this.dateValid = dateValid;
	}

	@Override
	public boolean apply(final Canton canton) {
	    return DATEVALIDITY.validate(dateValid, canton.getValidFrom(),
		    canton.getValidTo());
	}
    }

    private static class DistrictValidPredicate implements Predicate<District> {
	private final Date dateValid;

	/**
	 * Constructeur
	 * 
	 * @param dateValid
	 *            date de validation
	 */
	public DistrictValidPredicate(final Date dateValid) {
	    this.dateValid = dateValid;
	}

	@Override
	public boolean apply(final District district) {
	    return DATEVALIDITY.validate(dateValid, district.getValidFrom(),
		    district.getValidTo());
	}
    }

    private static class CommuneValidPredicate implements Predicate<Commune> {
	private final Date dateValid;

	/**
	 * Constructeur
	 * 
	 * @param dateValid
	 *            date de validation
	 */
	public CommuneValidPredicate(final Date dateValid) {
	    this.dateValid = dateValid;
	}

	@Override
	public boolean apply(final Commune commune) {
	    return DATEVALIDITY.validate(dateValid, commune.getValidFrom(),
		    commune.getValidTo());
	}
    }

    /** prédicat de vérification de validité d'une date dans une prériode */
    private static final DateValidityPredicate DATEVALIDITY = new DateValidityPredicate();

    private static class DateValidityPredicate {
	/**
	 * Validation de la date dans une période
	 * 
	 * @param dateValid
	 *            date à valider
	 * @param from
	 *            début de période
	 * @param to
	 *            fin de période
	 * @return flag de validité
	 */
	public boolean validate(final Date dateValid, final Date from,
		final Date to) {
	    return (dateValid == null)
		    || ((from == null || from.getTime() <= dateValid.getTime()) && (to == null || to
			    .getTime() >= dateValid.getTime()));
	}
    }

    private final Function<Canton, Iterable<? extends District>> extractDistrictFunction = new Function<Canton, Iterable<? extends District>>() {
	@Override
	public Iterable<? extends District> apply(final Canton canton) {
	    return canton.getDistrict();
	}
    };

    private final Function<District, Iterable<? extends Commune>> extractCommuneFunction = new Function<District, Iterable<? extends Commune>>() {
	@Override
	public Iterable<? extends Commune> apply(final District district) {
	    return district.getCommune();
	}
    };

    @Override
    public List<Commune> getCommunesHistoriquesByCanton(String codeCanton)
	    throws ReferentielOfsException {
		LOG.debug("getCommunesHistoriqueByCanton(codeCanton='{}')",
			codeCanton);
		if (StringUtils.isBlank(codeCanton)) {
		    return new LinkedList<Commune>();
		}
		// liste de 1 canton ou vide
		final FluentIterable<Canton> cantons = extractCanton(codeCanton, new Date());

		final FluentIterable<District> districts = cantons.transformAndConcat(
			extractDistrictFunction);

		List<Commune> sortedList = districts.transformAndConcat(extractCommuneFunction)
			.toSortedList(nomComparator);
		return sortedList;
    }
}
