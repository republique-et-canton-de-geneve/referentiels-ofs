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
import com.google.common.base.Strings;

/**
 * Comparateur de nom de classe IComplexType<br/>
 * La comparaison se fait sur la longueur du pattern, permettant une recherche
 * des nom "commen�ant par"<br/>
 * La comparaison de fait sur la cha�ne "normalis�" afin de permettre une
 * comparaison sans caract�res sp�ciaux et accentu�s
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
     *            cha�ne de comparaison
     */
    public NomStringMatcherPredicate(final String matcher) {
	this.matcher = Strings.isNullOrEmpty(matcher) ? null
		: normalize(matcher);
    }

    /**
     * M�thode d'ex�cution du pr�dicat
     * 
     * @param entit�
     *            � tester
     * @return r�sultat de la comparaison
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