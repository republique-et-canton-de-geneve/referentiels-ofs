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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import org.junit.Test;

import ch.ge.cti.ct.referentiels.ofs.model.IComplexType;

public class AbstractMatcherPredicateTest {

    private final AbstractMatcherPredicate predicate = new AbstractMatcherPredicate() {
	@Override
	public boolean apply(final IComplexType arg0) {
	    return false;
	}
    };

    @Test
    public void testNormalize() {
	assertNull(predicate.normalize(null));
	assertEquals("", predicate.normalize(""));
	assertEquals("a", predicate.normalize("a"));
	assertEquals("abcdefgh", predicate.normalize("abcdefgh"));
	assertEquals(
		"abcdefghijklmnopqrstuvwxyz 1234567890,;:.-_+-/*[]{}()\\<>'~`^",
		predicate
			.normalize("abcdefghijklmnopqrstuvwxyz 1234567890,;:.-_+-/*[]{}()\\<>'~`^"));
	assertEquals("aaaaaeeeeooooouuu",
		predicate.normalize("a����e���o����u��"));
    }

}
