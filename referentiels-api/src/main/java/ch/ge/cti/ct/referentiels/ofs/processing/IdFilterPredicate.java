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
package ch.ge.cti.ct.referentiels.ofs.processing;

import ch.ge.cti.ct.referentiels.ofs.model.IComplexType;

import com.google.common.base.Predicate;

/**
 * Impl�mentation du pr�dicat de filtrage sur l'id
 * 
 * @author DESMAZIERESJ
 * 
 */
public class IdFilterPredicate implements Predicate<IComplexType> {
    private final int id;

    /**
     * Constructeur
     * 
     * @param id
     *            identifiant
     */
    public IdFilterPredicate(final int id) {
	this.id = id;
    }

    /**
     * M�thode d'ex�cution du pr�dicat
     * 
     * @param donn�es
     *            � tester par le pr�dicat
     * @return flag d'identit� des identifiants
     */
    @Override
    public boolean apply(final IComplexType ct) {
	return ct.getId() == id;
    }
}