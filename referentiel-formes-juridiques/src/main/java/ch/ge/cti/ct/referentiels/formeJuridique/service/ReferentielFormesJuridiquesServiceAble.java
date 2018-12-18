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
package ch.ge.cti.ct.referentiels.formeJuridique.service;

import java.util.List;

import ch.ge.cti.ct.referentiels.formeJuridique.model.FormeJuridique;
import ch.ge.cti.ct.referentiels.formeJuridique.model.ReferentielFormesJuridiques;
import ch.ge.cti.ct.referentiels.ofs.ReferentielOfsException;

/**
 * Définition du contrat du service de référentiel des pays<br/>
 * Le référentiel provient de l'office statistique Suisse dépendant de
 * l'administration fédérale<br/>
 * 
 * @author desmazièresj
 * 
 */
public interface ReferentielFormesJuridiquesServiceAble {

    /**
     * Retourne l'intégralité du référentiel des pays
     * 
     * @return référentiel en entier
     * @throws ReferentielOfsException
     *             erreur de traitement
     */
    ReferentielFormesJuridiques getReferentiel() throws ReferentielOfsException;

    /**
     * Retourne la liste des formes juridiques<br/>
     * 
     * @return liste des formes juridiques
     * @throws ReferentielOfsException
     *             erreur de traitement
     */
    List<FormeJuridique> getFormesJuridiques() throws ReferentielOfsException;

    /**
     * Retourne la liste des formes juridiques<br/>
     * 
     * @param formeJuridiqueId
     *            identifiant de la forme juridique
     * @return liste des formes juridiques
     * @throws ReferentielOfsException
     *             erreur de traitement
     */
    FormeJuridique getFormeJuridique(final int formeJuridiqueId)
	    throws ReferentielOfsException;

}
