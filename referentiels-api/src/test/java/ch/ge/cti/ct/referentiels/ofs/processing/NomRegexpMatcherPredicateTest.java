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

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class NomRegexpMatcherPredicateTest {

    @Test
    public void testApply() {
	NomRegexpMatcherPredicate predicate = null;

	predicate = new NomRegexpMatcherPredicate(null);
	assertFalse(predicate.apply(new _ComplexType(0, null)));
	assertFalse(predicate.apply(new _ComplexType(0, "")));

	predicate = new NomRegexpMatcherPredicate("");
	assertFalse(predicate.apply(new _ComplexType(0, null)));
	assertFalse(predicate.apply(new _ComplexType(0, "")));

	predicate = new NomRegexpMatcherPredicate("a");
	assertFalse(predicate.apply(new _ComplexType(0, null)));
	assertFalse(predicate.apply(new _ComplexType(0, "")));
	assertTrue(predicate.apply(new _ComplexType(0, "a")));
	assertTrue(predicate.apply(new _ComplexType(0, "abcdef")));
	assertTrue(predicate.apply(new _ComplexType(0, "bcadef")));
	assertTrue(predicate.apply(new _ComplexType(0, "BCADEF")));
	assertTrue(predicate.apply(new _ComplexType(0, "bc�def")));

	predicate = new NomRegexpMatcherPredicate("a.c");
	assertFalse(predicate.apply(new _ComplexType(0, null)));
	assertFalse(predicate.apply(new _ComplexType(0, "")));
	assertFalse(predicate.apply(new _ComplexType(0, "a")));
	assertTrue(predicate.apply(new _ComplexType(0, "abcdef")));
	assertTrue(predicate.apply(new _ComplexType(0, "cdabcef")));
	assertTrue(predicate.apply(new _ComplexType(0, "ABC")));
	assertFalse(predicate.apply(new _ComplexType(0, "bcadef")));
    }

}
