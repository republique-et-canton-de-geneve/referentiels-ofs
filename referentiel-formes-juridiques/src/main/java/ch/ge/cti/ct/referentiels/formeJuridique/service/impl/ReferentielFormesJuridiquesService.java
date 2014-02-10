package ch.ge.cti.ct.referentiels.formeJuridique.service.impl;

import java.util.Comparator;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ch.ge.cti.ct.referentiels.formeJuridique.data.ReferentielDataSingleton;
import ch.ge.cti.ct.referentiels.formeJuridique.model.FormeJuridique;
import ch.ge.cti.ct.referentiels.formeJuridique.model.ReferentielFormesJuridiques;
import ch.ge.cti.ct.referentiels.formeJuridique.service.ReferentielFormesJuridiquesServiceAble;
import ch.ge.cti.ct.referentiels.ofs.ReferentielOfsException;

import com.google.common.base.Predicate;
import com.google.common.collect.FluentIterable;

/**
 * Implémentation POJO du service
 * 
 * @author desmazieresj
 * 
 */
public enum ReferentielFormesJuridiquesService implements
	ReferentielFormesJuridiquesServiceAble {
    instance;

    /** logger SLF4j */
    private static final Logger LOG = LoggerFactory
	    .getLogger(ReferentielFormesJuridiquesService.class);

    @Override
    public ReferentielFormesJuridiques getReferentiel()
	    throws ReferentielOfsException {
	return ReferentielDataSingleton.instance.getData();
    }

    @Override
    public List<FormeJuridique> getFormesJuridiques()
	    throws ReferentielOfsException {
	LOG.debug("getFormesJuridiques()");
	// on retourne une copie de la liste des continents
	return FluentIterable
		.from(ReferentielDataSingleton.instance.getData()
			.getFormeJuridique()).toSortedList(
			new FormeJuridiqueComparator());
    }

    @Override
    public FormeJuridique getFormeJuridique(final int formeJuridiqueId)
	    throws ReferentielOfsException {
	LOG.debug("getFormeJuridique(formeJuridiqueId='{}')", formeJuridiqueId);
	if (formeJuridiqueId <= 0) {
	    return null;
	}
	return FluentIterable
		.from(ReferentielDataSingleton.instance.getData()
			.getFormeJuridique())
		.filter(new Predicate<FormeJuridique>() {
		    @Override
		    public boolean apply(final FormeJuridique formeJuridique) {
			return formeJuridique.getId() == formeJuridiqueId;
		    }
		}).first().orNull();
    }

    /**
     * Comparateur pour le tri des listes des regions
     * 
     */
    private class FormeJuridiqueComparator implements
	    Comparator<FormeJuridique> {
	@Override
	public int compare(final FormeJuridique c0, final FormeJuridique c1) {
	    return c0.getNom().compareTo(c1.getNom());
	}
    }
}
