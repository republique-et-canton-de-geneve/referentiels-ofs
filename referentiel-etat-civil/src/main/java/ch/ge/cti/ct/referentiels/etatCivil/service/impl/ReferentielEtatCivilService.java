package ch.ge.cti.ct.referentiels.etatCivil.service.impl;

import java.util.Comparator;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ch.ge.cti.ct.referentiels.etatCivil.data.ReferentielDataSingleton;
import ch.ge.cti.ct.referentiels.etatCivil.model.EtatCivil;
import ch.ge.cti.ct.referentiels.etatCivil.model.ReferentielEtatCivil;
import ch.ge.cti.ct.referentiels.etatCivil.service.ReferentielEtatCivilServiceAble;
import ch.ge.cti.ct.referentiels.ofs.ReferentielOfsException;

import com.google.common.base.Predicate;
import com.google.common.collect.FluentIterable;

/**
 * Implémentation POJO du service
 * 
 * @author desmazieresj
 * 
 */
public enum ReferentielEtatCivilService implements
	ReferentielEtatCivilServiceAble {
    instance;

    /** logger SLF4j */
    private static final Logger LOG = LoggerFactory
	    .getLogger(ReferentielEtatCivilService.class);

    @Override
    public ReferentielEtatCivil getReferentiel() throws ReferentielOfsException {
	return ReferentielDataSingleton.instance.getData();
    }

    @Override
    public List<EtatCivil> getEtatsCivils() throws ReferentielOfsException {
	LOG.debug("getFormesJuridiques()");
	// on retourne une copie de la liste des continents
	return FluentIterable.from(
		ReferentielDataSingleton.instance.getData().getEtatCivil())
		.toSortedList(new EtatCivilComparator());
    }

    @Override
    public EtatCivil getEtatCivil(final int etatCivilId)
	    throws ReferentielOfsException {
	LOG.debug("getEtatCivil(etatCivilId='{}')", etatCivilId);
	if (etatCivilId <= 0) {
	    return null;
	}
	return FluentIterable
		.from(ReferentielDataSingleton.instance.getData()
			.getEtatCivil()).filter(new Predicate<EtatCivil>() {
		    @Override
		    public boolean apply(final EtatCivil etatCivil) {
			return etatCivil.getId() == etatCivilId;
		    }
		}).first().orNull();
    }

    /**
     * Comparateur pour le tri des listes des regions
     * 
     */
    private class EtatCivilComparator implements Comparator<EtatCivil> {
	@Override
	public int compare(final EtatCivil c0, final EtatCivil c1) {
	    return c0.getNom().compareTo(c1.getNom());
	}
    }
}
