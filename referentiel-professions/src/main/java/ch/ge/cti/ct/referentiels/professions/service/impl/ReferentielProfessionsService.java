package ch.ge.cti.ct.referentiels.professions.service.impl;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ch.ge.cti.ct.referentiels.ofs.ReferentielOfsException;
import ch.ge.cti.ct.referentiels.ofs.processing.IdFilterPredicate;
import ch.ge.cti.ct.referentiels.ofs.processing.NomComparator;
import ch.ge.cti.ct.referentiels.ofs.processing.NomRegexpMatcherPredicate;
import ch.ge.cti.ct.referentiels.ofs.processing.NomStringMatcherPredicate;
import ch.ge.cti.ct.referentiels.professions.data.ReferentielDataSingleton;
import ch.ge.cti.ct.referentiels.professions.model.Classe;
import ch.ge.cti.ct.referentiels.professions.model.Division;
import ch.ge.cti.ct.referentiels.professions.model.Genre;
import ch.ge.cti.ct.referentiels.professions.model.Groupe;
import ch.ge.cti.ct.referentiels.professions.model.ReferentielProfessions;
import ch.ge.cti.ct.referentiels.professions.service.ReferentielProfessionsServiceAble;

import com.google.common.base.Function;
import com.google.common.collect.FluentIterable;

/**
 * Implémentation POJO du service
 * 
 * @author desmazieresj
 * 
 */
public enum ReferentielProfessionsService implements
	ReferentielProfessionsServiceAble {
    instance;

    /** logger SLF4j */
    private static final Logger LOG = LoggerFactory
	    .getLogger(ReferentielProfessionsService.class);

    @Override
    public ReferentielProfessions getReferentiel()
	    throws ReferentielOfsException {
	return ReferentielDataSingleton.instance.getData();
    }

    @Override
    public List<Division> getDivisions() throws ReferentielOfsException {
	LOG.debug("getDivisions()");
	return FluentIterable.from(
		ReferentielDataSingleton.instance.getData().getDivision())
		.toSortedList(nomComparator);
    }

    @Override
    public Division getDivision(final int divisionId)
	    throws ReferentielOfsException {
	LOG.debug("getDivision(divisionId='{}')", divisionId);
	if (divisionId <= 0) {
	    return null;
	}
	return FluentIterable
		.from(ReferentielDataSingleton.instance.getData().getDivision())
		.filter(new IdFilterPredicate(divisionId)).first().orNull();
    }

    @Override
    public List<Division> searchDivision(final String pattern)
	    throws ReferentielOfsException {
	LOG.debug("searchDivision(pattern='{}')", pattern);
	if (StringUtils.isBlank(pattern)) {
	    return null;
	}
	return FluentIterable
		.from(ReferentielDataSingleton.instance.getData().getDivision())
		.filter(new NomStringMatcherPredicate(pattern))
		.toSortedList(nomComparator);
    }

    @Override
    public List<Division> searchDivisionRegexp(final String regexp)
	    throws ReferentielOfsException {
	LOG.debug("searchDivisionRegexp(regexp='{}')", regexp);
	if (StringUtils.isBlank(regexp)) {
	    return null;
	}
	return FluentIterable
		.from(ReferentielDataSingleton.instance.getData().getDivision())
		.filter(new NomRegexpMatcherPredicate(regexp))
		.toSortedList(nomComparator);
    }

    @Override
    public List<Classe> getClasses() throws ReferentielOfsException {
	LOG.debug("getClasses()");
	return FluentIterable
		.from(ReferentielDataSingleton.instance.getData().getDivision())
		.transformAndConcat(extractClassesFunction)
		.toSortedList(nomComparator);
    }

    @Override
    public List<Classe> getClasses(final int divisionId)
	    throws ReferentielOfsException {
	LOG.debug("getClasses(divisionId='{}')", divisionId);
	if (divisionId <= 0) {
	    return null;
	}
	return FluentIterable
		.from(ReferentielDataSingleton.instance.getData().getDivision())
		.filter(new IdFilterPredicate(divisionId))
		.transformAndConcat(extractClassesFunction)
		.toSortedList(nomComparator);
    }

    @Override
    public Classe getClasse(final int classeId) throws ReferentielOfsException {
	LOG.debug("getClasses(classeId='{}')", classeId);
	if (classeId <= 0) {
	    return null;
	}
	return FluentIterable
		.from(ReferentielDataSingleton.instance.getData().getDivision())
		.transformAndConcat(extractClassesFunction)
		.filter(new IdFilterPredicate(classeId)).first().orNull();
    }

    @Override
    public List<Classe> searchClasse(final String pattern)
	    throws ReferentielOfsException {
	LOG.debug("searchClasse(pattern='{}')", pattern);
	if (StringUtils.isBlank(pattern)) {
	    return null;
	}
	return FluentIterable
		.from(ReferentielDataSingleton.instance.getData().getDivision())
		.transformAndConcat(extractClassesFunction)
		.filter(new NomStringMatcherPredicate(pattern))
		.toSortedList(nomComparator);
    }

    @Override
    public List<Classe> searchClasseRegexp(final String regexp)
	    throws ReferentielOfsException {
	LOG.debug("searchClasseRegexp(regexp='{}')", regexp);
	if (StringUtils.isBlank(regexp)) {
	    return null;
	}
	return FluentIterable
		.from(ReferentielDataSingleton.instance.getData().getDivision())
		.transformAndConcat(extractClassesFunction)
		.filter(new NomRegexpMatcherPredicate(regexp))
		.toSortedList(nomComparator);
    }

    @Override
    public List<Groupe> getGroupes() throws ReferentielOfsException {
	LOG.debug("getGroupes()");
	return FluentIterable
		.from(ReferentielDataSingleton.instance.getData().getDivision())
		.transformAndConcat(extractClassesFunction)
		.transformAndConcat(extractGroupesFunction)
		.toSortedList(nomComparator);
    }

    @Override
    public List<Groupe> getGroupes(final int classeId)
	    throws ReferentielOfsException {
	LOG.debug("getGroupes(classeId='{}')", classeId);
	if (classeId <= 0) {
	    return null;
	}
	return FluentIterable
		.from(ReferentielDataSingleton.instance.getData().getDivision())
		.transformAndConcat(extractClassesFunction)
		.filter(new IdFilterPredicate(classeId))
		.transformAndConcat(extractGroupesFunction)
		.toSortedList(nomComparator);
    }

    @Override
    public Groupe getGroupe(final int groupeId) throws ReferentielOfsException {
	LOG.debug("getGroupe(groupeId='{}')", groupeId);
	if (groupeId <= 0) {
	    return null;
	}
	return FluentIterable
		.from(ReferentielDataSingleton.instance.getData().getDivision())
		.transformAndConcat(extractClassesFunction)
		.transformAndConcat(extractGroupesFunction)
		.filter(new IdFilterPredicate(groupeId)).first().orNull();
    };

    @Override
    public List<Groupe> searchGroupe(final String pattern)
	    throws ReferentielOfsException {
	LOG.debug("searchGroupe(pattern='{}')", pattern);
	if (StringUtils.isBlank(pattern)) {
	    return null;
	}
	return FluentIterable
		.from(ReferentielDataSingleton.instance.getData().getDivision())
		.transformAndConcat(extractClassesFunction)
		.transformAndConcat(extractGroupesFunction)
		.filter(new NomStringMatcherPredicate(pattern))
		.toSortedList(nomComparator);
    }

    @Override
    public List<Groupe> searchGroupeRegexp(final String regexp)
	    throws ReferentielOfsException {
	LOG.debug("searchGroupeRegexp(regexp='{}')", regexp);
	if (StringUtils.isBlank(regexp)) {
	    return null;
	}
	return FluentIterable
		.from(ReferentielDataSingleton.instance.getData().getDivision())
		.transformAndConcat(extractClassesFunction)
		.transformAndConcat(extractGroupesFunction)
		.filter(new NomRegexpMatcherPredicate(regexp))
		.toSortedList(nomComparator);
    }

    @Override
    public List<Genre> getGenres() throws ReferentielOfsException {
	LOG.debug("getGenres()");
	return FluentIterable
		.from(ReferentielDataSingleton.instance.getData().getDivision())
		.transformAndConcat(extractClassesFunction)
		.transformAndConcat(extractGroupesFunction)
		.transformAndConcat(extractGenresFunction)
		.toSortedList(nomComparator);
    }

    @Override
    public List<Genre> getGenres(final int groupeId)
	    throws ReferentielOfsException {
	LOG.debug("getGenres(groupeId='{}')", groupeId);
	if (groupeId <= 0) {
	    return null;
	}
	return FluentIterable
		.from(ReferentielDataSingleton.instance.getData().getDivision())
		.transformAndConcat(extractClassesFunction)
		.transformAndConcat(extractGroupesFunction)
		.filter(new IdFilterPredicate(groupeId))
		.transformAndConcat(extractGenresFunction)
		.toSortedList(nomComparator);
    }

    @Override
    public Genre getGenre(final int genreId) throws ReferentielOfsException {
	LOG.debug("getGenre(genreId='{}')", genreId);
	if (genreId <= 0) {
	    return null;
	}
	return FluentIterable
		.from(ReferentielDataSingleton.instance.getData().getDivision())
		.transformAndConcat(extractClassesFunction)
		.transformAndConcat(extractGroupesFunction)
		.transformAndConcat(extractGenresFunction)
		.filter(new IdFilterPredicate(genreId)).first().orNull();
    }

    @Override
    public List<Genre> searchGenre(final String pattern)
	    throws ReferentielOfsException {
	LOG.debug("searchGenre(pattern='{}')", pattern);
	if (StringUtils.isBlank(pattern)) {
	    return null;
	}
	return FluentIterable
		.from(ReferentielDataSingleton.instance.getData().getDivision())
		.transformAndConcat(extractClassesFunction)
		.transformAndConcat(extractGroupesFunction)
		.transformAndConcat(extractGenresFunction)
		.filter(new NomStringMatcherPredicate(pattern))
		.toSortedList(nomComparator);
    }

    @Override
    public List<Genre> searchGenreRegexp(final String regexp)
	    throws ReferentielOfsException {
	LOG.debug("searchGenreRegexp(regexp='{}')", regexp);
	if (StringUtils.isBlank(regexp)) {
	    return null;
	}
	return FluentIterable
		.from(ReferentielDataSingleton.instance.getData().getDivision())
		.transformAndConcat(extractClassesFunction)
		.transformAndConcat(extractGroupesFunction)
		.transformAndConcat(extractGenresFunction)
		.filter(new NomRegexpMatcherPredicate(regexp))
		.toSortedList(nomComparator);
    }

    // ==================================================================================================================================================================
    // ==================================================================================================================================================================

    private final NomComparator nomComparator = new NomComparator();

    private final Function<Division, Iterable<? extends Classe>> extractClassesFunction = new Function<Division, Iterable<? extends Classe>>() {
	@Override
	public Iterable<? extends Classe> apply(final Division division) {
	    return division.getClasse();
	}
    };

    private final Function<Classe, Iterable<? extends Groupe>> extractGroupesFunction = new Function<Classe, Iterable<? extends Groupe>>() {
	@Override
	public Iterable<? extends Groupe> apply(final Classe classe) {
	    return classe.getGroupe();
	}
    };

    private final Function<Groupe, Iterable<? extends Genre>> extractGenresFunction = new Function<Groupe, Iterable<? extends Genre>>() {
	@Override
	public Iterable<? extends Genre> apply(final Groupe group) {
	    return group.getGenre();
	}
    };

}
