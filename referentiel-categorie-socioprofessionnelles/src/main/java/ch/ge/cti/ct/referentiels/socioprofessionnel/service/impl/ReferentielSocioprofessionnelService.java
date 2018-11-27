package ch.ge.cti.ct.referentiels.socioprofessionnel.service.impl;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ch.ge.cti.ct.referentiels.ofs.ReferentielOfsException;
import ch.ge.cti.ct.referentiels.ofs.processing.IdFilterPredicate;
import ch.ge.cti.ct.referentiels.ofs.processing.NomComparator;
import ch.ge.cti.ct.referentiels.ofs.processing.NomRegexpMatcherPredicate;
import ch.ge.cti.ct.referentiels.ofs.processing.NomStringMatcherPredicate;
import ch.ge.cti.ct.referentiels.socioprofessionnel.data.ReferentielDataSingleton;
import ch.ge.cti.ct.referentiels.socioprofessionnel.model.Niveau1;
import ch.ge.cti.ct.referentiels.socioprofessionnel.model.Niveau2;
import ch.ge.cti.ct.referentiels.socioprofessionnel.model.ReferentielSocioprofessionnel;
import ch.ge.cti.ct.referentiels.socioprofessionnel.service.ReferentielSocioprofessionnelServiceAble;

import com.google.common.base.Function;
import com.google.common.collect.FluentIterable;

/**
 * Implémentation POJO du service
 * 
 * @author desmazieresj
 * 
 */
public enum ReferentielSocioprofessionnelService implements	ReferentielSocioprofessionnelServiceAble {

	INSTANCE;

    /** logger SLF4j */
    private static final Logger LOG = LoggerFactory
	    .getLogger(ReferentielSocioprofessionnelService.class);

    @Override
    public ReferentielSocioprofessionnel getReferentiel()
	    throws ReferentielOfsException {
	return ReferentielDataSingleton.INSTANCE.getData();
    }

    @Override
    public List<Niveau1> getNiveaux1() throws ReferentielOfsException {
	LOG.debug("getNiveaux1()");
	return FluentIterable.from(
		ReferentielDataSingleton.INSTANCE.getData().getNiveau1())
		.toSortedList(nomComparator);
    }

    @Override
    public Niveau1 getNiveau1(final int niveau1Id)
	    throws ReferentielOfsException {
	LOG.debug("getNiveau1(niveau1Id='{}')", niveau1Id);
	if (niveau1Id <= 0) {
	    return null;
	}
	return FluentIterable
		.from(ReferentielDataSingleton.INSTANCE.getData().getNiveau1())
		.filter(new IdFilterPredicate(niveau1Id)).first().orNull();
    }

    @Override
    public List<Niveau1> searchNiveaux1(final String pattern)
	    throws ReferentielOfsException {
	LOG.debug("searchNiveau1(pattern='{}')", pattern);
	if (StringUtils.isBlank(pattern)) {
	    return null;
	}
	return FluentIterable
		.from(ReferentielDataSingleton.INSTANCE.getData().getNiveau1())
		.filter(new NomStringMatcherPredicate(pattern))
		.toSortedList(nomComparator);
    }

    @Override
    public List<Niveau1> searchNiveaux1Regexp(final String regexp)
	    throws ReferentielOfsException {
	LOG.debug("searchNiveau1Regexp(regexp='{}')", regexp);
	if (StringUtils.isBlank(regexp)) {
		return null;
	}
	return FluentIterable
		.from(ReferentielDataSingleton.INSTANCE.getData().getNiveau1())
		.filter(new NomRegexpMatcherPredicate(regexp))
		.toSortedList(nomComparator);
    }

    @Override
    public List<Niveau2> getNiveaux2() throws ReferentielOfsException {
	LOG.debug("getNiveaux2()");
	return FluentIterable
		.from(ReferentielDataSingleton.INSTANCE.getData().getNiveau1())
		.transformAndConcat(extractNiveaux2Function)
		.toSortedList(nomComparator);
    }

    @Override
    public List<Niveau2> getNiveaux2(final int niveau1Id)
	    throws ReferentielOfsException {
	LOG.debug("getNiveaux2(niveau1Id='{}')", niveau1Id);
	if (niveau1Id <= 0) {
		return null;
	}
	return FluentIterable
		.from(ReferentielDataSingleton.INSTANCE.getData().getNiveau1())
		.filter(new IdFilterPredicate(niveau1Id))
		.transformAndConcat(extractNiveaux2Function)
		.toSortedList(nomComparator);
    }

    @Override
    public Niveau2 getNiveau2(final int niveau2Id)
	    throws ReferentielOfsException {
	LOG.debug("getNiveaux2(niveau2Id='{}')", niveau2Id);
	if (niveau2Id <= 0) {
	    return null;
	}
	return FluentIterable
		.from(ReferentielDataSingleton.INSTANCE.getData().getNiveau1())
		.transformAndConcat(extractNiveaux2Function)
		.filter(new IdFilterPredicate(niveau2Id)).first().orNull();
    }

    @Override
    public List<Niveau2> searchNiveaux2(final String pattern)
	    throws ReferentielOfsException {
	LOG.debug("searchNiveau2(pattern='{}')", pattern);
	if (StringUtils.isBlank(pattern)) {
	    return null;
	}
	return FluentIterable
		.from(ReferentielDataSingleton.INSTANCE.getData().getNiveau1())
		.transformAndConcat(extractNiveaux2Function)
		.filter(new NomStringMatcherPredicate(pattern))
		.toSortedList(nomComparator);
    }

    @Override
    public List<Niveau2> searchNiveaux2Regexp(final String regexp)
	    throws ReferentielOfsException {
	LOG.debug("searchNiveau2Regexp(regexp='{}')", regexp);
	if (StringUtils.isBlank(regexp)) {
		return null;
	}
	return FluentIterable
		.from(ReferentielDataSingleton.INSTANCE.getData().getNiveau1())
		.transformAndConcat(extractNiveaux2Function)
		.filter(new NomRegexpMatcherPredicate(regexp))
		.toSortedList(nomComparator);
    }

    // ==================================================================================================================================================================
    // ==================================================================================================================================================================

    private final NomComparator nomComparator = new NomComparator();

    private final Function<Niveau1, Iterable<? extends Niveau2>> extractNiveaux2Function = new Function<Niveau1, Iterable<? extends Niveau2>>() {
	@Override
	public Iterable<? extends Niveau2> apply(final Niveau1 niveau1) {
	    return niveau1.getNiveau2();
	}
    };

}
