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
package ch.ge.cti.ct.referentiels.professions.interfaces.ws;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.databene.contiperf.PerfTest;
import org.databene.contiperf.Required;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import ch.ge.cti.ct.referentiels.ofs.ReferentielOfsException;
import ch.ge.cti.ct.referentiels.professions.interfaces.ws.model.DivisionWS;

@RunWith(value = Parameterized.class)
@PerfTest(invocations = 1000, threads = 20)
public class RefDivisionSearchStringTest extends AbstractRefWSTest {

    private final String searchString;
    private final int countDivisions;

    public RefDivisionSearchStringTest(final String searchString,
	    final int countDivisions) {
	this.searchString = searchString;
	this.countDivisions = countDivisions;
    }

    @Parameters
    public static Collection<Object[]> configure() {
	return Arrays.asList(new Object[][] {
	/* 0 */{ null, 0 },
	/* 1 */{ "", 0 },
	/* 2 */{ "Professions", 8 },
	/* 3 */{ "professions", 8 },
	/* 4 */{ "Indica", 1 },
	/* 5 */{ "Professions de la sant�", 1 },
	/* 6 */{ "xxxxxxxx", 0 }
	/* */
	});
    }

    @Test
   
    public void test() throws ReferentielOfsException {
	final List<DivisionWS> classes = getWS().searchDivision(searchString);
	if (countDivisions == -1) {
	    assertNull("Divisions [" + searchString + "] est incorrect",
		    classes);
	} else {
	    assertNotNull("Divisions [" + searchString + "] est incorrect",
		    classes);
	    assertEquals("Divisions [" + searchString + "].size est incorrect",
		    countDivisions, classes.size());
	}
    }
}
