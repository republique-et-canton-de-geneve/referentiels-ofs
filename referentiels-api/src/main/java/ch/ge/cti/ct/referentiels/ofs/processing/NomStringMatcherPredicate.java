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
package ch.ge.cti.ct.referentiels.ofs.processing;

import ch.ge.cti.ct.referentiels.ofs.model.IComplexType;

import com.google.common.base.Predicate;
import com.google.common.base.Strings;

/**
 * Comparateur de nom de classe IComplexType<br/>
 * La comparaison se fait sur la longueur du pattern, permettant une recherche
 * des nom "commençant par"<br/>
 * La comparaison de fait sur la chaîne "normalisé" afin de permettre une
 * comparaison sans caractères spéciaux et accentués
 * 
 * @author DESMAZIERESJ
 * 
 */
public class NomStringMatcherPredicate extends AbstractMatcherPredicate
	implements Predicate<IComplexType> {

    private final String matcher;

    /**
     * Constructeur
     * 
     * @param matcher
     *            chaîne de comparaison
     */
    public NomStringMatcherPredicate(final String matcher) {
	this.matcher = Strings.isNullOrEmpty(matcher) ? null
		: normalize(matcher);
    }

    /**
     * Méthode d'exécution du prédicat
     * 
     * @param entité
     *            à tester
     * @return résultat de la comparaison
     */
    @Override
    public boolean apply(final IComplexType compleType) {
	if (matcher == null && Strings.emptyToNull(compleType.getNom()) == null) {
	    return true;
	}
	if (matcher == null ^ Strings.emptyToNull(compleType.getNom()) == null) {
	    return false;
	}
	return matcher.equals(normalize(compleType.getNom()).substring(0,
		Math.min(compleType.getNom().length(), matcher.length())));
    }
}