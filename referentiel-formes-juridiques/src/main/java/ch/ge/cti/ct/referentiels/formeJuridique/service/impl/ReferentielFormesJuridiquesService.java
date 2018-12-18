/*
 * Referentiels OFS
 *
 * Copyright (C) 2018 République et canton de Genève
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package ch.ge.cti.ct.referentiels.formeJuridique.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ch.ge.cti.ct.referentiels.formeJuridique.data.ReferentielDataSingleton;
import ch.ge.cti.ct.referentiels.formeJuridique.model.FormeJuridique;
import ch.ge.cti.ct.referentiels.formeJuridique.model.ReferentielFormesJuridiques;
import ch.ge.cti.ct.referentiels.formeJuridique.service.ReferentielFormesJuridiquesServiceAble;
import ch.ge.cti.ct.referentiels.ofs.ReferentielOfsException;
import ch.ge.cti.ct.referentiels.ofs.processing.NomComparator;

import com.google.common.base.Predicate;
import com.google.common.collect.FluentIterable;

/**
 * Impl�mentation POJO du service
 * 
 * @author desmazieresj
 * 
 */
public enum ReferentielFormesJuridiquesService implements
	ReferentielFormesJuridiquesServiceAble {

	INSTANCE;

    /** logger SLF4j */
    private static final Logger LOG = LoggerFactory
	    .getLogger(ReferentielFormesJuridiquesService.class);

    @Override
    public ReferentielFormesJuridiques getReferentiel()
	    throws ReferentielOfsException {
	return ReferentielDataSingleton.INSTANCE.getData();
    }

    @Override
    public List<FormeJuridique> getFormesJuridiques()
	    throws ReferentielOfsException {
	LOG.debug("getFormesJuridiques()");
	// on retourne une copie de la liste des continents
	return FluentIterable
		.from(ReferentielDataSingleton.INSTANCE.getData()
			.getFormeJuridique()).toSortedList(nomComparator);
    }

    @Override
    public FormeJuridique getFormeJuridique(final int formeJuridiqueId)
	    throws ReferentielOfsException {
	LOG.debug("getFormeJuridique(formeJuridiqueId='{}')", formeJuridiqueId);
	if (formeJuridiqueId <= 0) {
	    return null;
	}
	return FluentIterable
		.from(ReferentielDataSingleton.INSTANCE.getData()
			.getFormeJuridique())
		.filter(new Predicate<FormeJuridique>() {
		    @Override
		    public boolean apply(final FormeJuridique formeJuridique) {
			return formeJuridique.getId() == formeJuridiqueId;
		    }
		}).first().orNull();
    }

    private final NomComparator nomComparator = new NomComparator();
}
