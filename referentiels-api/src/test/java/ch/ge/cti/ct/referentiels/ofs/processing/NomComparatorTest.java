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

public class NomComparatorTest {

    private final NomComparator comparator = new NomComparator();

    @Test
    public void testCompare() {
	assertTrue(comparator.compare(new _ComplexType(0, null),
		new _ComplexType(0, null)) == 0);
	assertTrue(comparator.compare(new _ComplexType(0, ""),
		new _ComplexType(0, "")) == 0);
	assertTrue(comparator.compare(new _ComplexType(0, "a"),
		new _ComplexType(0, "a")) == 0);
	assertFalse(comparator.compare(new _ComplexType(0, "a"),
		new _ComplexType(0, "A")) == 0);
	assertFalse(comparator.compare(new _ComplexType(0, null),
		new _ComplexType(0, "")) == 0);
	assertFalse(comparator.compare(new _ComplexType(0, null),
		new _ComplexType(0, "a")) == 0);
	assertFalse(comparator.compare(new _ComplexType(0, ""),
		new _ComplexType(0, null)) == 0);
	assertFalse(comparator.compare(new _ComplexType(0, "a"),
		new _ComplexType(0, null)) == 0);
    }

}
