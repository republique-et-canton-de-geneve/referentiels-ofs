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
package ch.ge.cti.ct.referentiels.etatCivil.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ch.ge.cti.ct.referentiels.etatCivil.data.ReferentielDataSingleton;
import ch.ge.cti.ct.referentiels.etatCivil.model.EtatCivil;
import ch.ge.cti.ct.referentiels.etatCivil.model.ReferentielEtatCivil;
import ch.ge.cti.ct.referentiels.etatCivil.service.ReferentielEtatCivilServiceAble;
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
public enum ReferentielEtatCivilService implements
	ReferentielEtatCivilServiceAble {

	INSTANCE;

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
		.toSortedList(nomComparator);
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

    private final NomComparator nomComparator = new NomComparator();
}
