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

import java.util.regex.Pattern;

import ch.ge.cti.ct.referentiels.ofs.model.IComplexType;

import com.google.common.base.Predicate;
import com.google.common.base.Strings;

/**
 * Implémentation du prédicat de filtrage par regexp sur le nom
 * 
 * @author DESMAZIERESJ
 * 
 */
public class NomRegexpMatcherPredicate extends AbstractMatcherPredicate
	implements Predicate<IComplexType> {

    private final Pattern regexp;

    /**
     * Constructeur
     * 
     * @param regexp
     *            expression régulière au format String
     */
    public NomRegexpMatcherPredicate(final String regexp) {
	this.regexp = Strings.emptyToNull(regexp) == null ? null : Pattern
		.compile(normalize(Strings.nullToEmpty(regexp).trim()));
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
	if (regexp == null) {
	    return false;
	}
	return regexp.matcher(
		normalize(Strings.nullToEmpty(compleType.getNom()))).find();
    }

}