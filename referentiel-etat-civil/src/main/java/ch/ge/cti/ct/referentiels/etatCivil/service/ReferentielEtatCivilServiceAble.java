/*
 * Referentiels OFS
 *
 * Copyright (C) 2018 RÃ©publique et canton de GenÃ¨ve
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
package ch.ge.cti.ct.referentiels.etatCivil.service;

import java.util.List;

import ch.ge.cti.ct.referentiels.etatCivil.model.EtatCivil;
import ch.ge.cti.ct.referentiels.etatCivil.model.ReferentielEtatCivil;
import ch.ge.cti.ct.referentiels.ofs.ReferentielOfsException;

/**
 * Définition du contrat du service de référentiel des pays<br/>
 * Le référentiel provient de l'office statistique Suisse dépendant de
 * l'administration fédérale<br/>
 * 
 * @author desmazièresj
 * 
 */
public interface ReferentielEtatCivilServiceAble {

    /**
     * Retourne l'intégralité du référentiel des pays
     * 
     * @return référentiel en entier
     * @throws ReferentielOfsException
     *             erreur de traitement
     */
    ReferentielEtatCivil getReferentiel() throws ReferentielOfsException;

    /**
     * Retourne la liste des états civils<br/>
     * 
     * @return liste des états civils
     * @throws ReferentielOfsException
     *             erreur de traitement
     */
    List<EtatCivil> getEtatsCivils() throws ReferentielOfsException;

    /**
     * Retourne la liste des états civils<br/>
     * 
     * @param etatCivilId
     *            identifiant de la forme juridique
     * @return liste des états civils
     * @throws ReferentielOfsException
     *             erreur de traitement
     */
    EtatCivil getEtatCivil(final int etatCivilId)
	    throws ReferentielOfsException;

}
