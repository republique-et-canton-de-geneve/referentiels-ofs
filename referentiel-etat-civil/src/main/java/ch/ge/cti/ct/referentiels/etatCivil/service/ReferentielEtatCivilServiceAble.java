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
package ch.ge.cti.ct.referentiels.etatCivil.service;

import java.util.List;

import ch.ge.cti.ct.referentiels.etatCivil.model.EtatCivil;
import ch.ge.cti.ct.referentiels.etatCivil.model.ReferentielEtatCivil;
import ch.ge.cti.ct.referentiels.ofs.ReferentielOfsException;

/**
 * D�finition du contrat du service de r�f�rentiel des pays<br/>
 * Le r�f�rentiel provient de l'office statistique Suisse d�pendant de
 * l'administration f�d�rale<br/>
 * 
 * @author desmazi�resj
 * 
 */
public interface ReferentielEtatCivilServiceAble {

    /**
     * Retourne l'int�gralit� du r�f�rentiel des pays
     * 
     * @return r�f�rentiel en entier
     * @throws ReferentielOfsException
     *             erreur de traitement
     */
    ReferentielEtatCivil getReferentiel() throws ReferentielOfsException;

    /**
     * Retourne la liste des �tats civils<br/>
     * 
     * @return liste des �tats civils
     * @throws ReferentielOfsException
     *             erreur de traitement
     */
    List<EtatCivil> getEtatsCivils() throws ReferentielOfsException;

    /**
     * Retourne la liste des �tats civils<br/>
     * 
     * @param etatCivilId
     *            identifiant de la forme juridique
     * @return liste des �tats civils
     * @throws ReferentielOfsException
     *             erreur de traitement
     */
    EtatCivil getEtatCivil(final int etatCivilId)
	    throws ReferentielOfsException;

}
