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
package ch.ge.cti.ct.referentiels.socioprofessionnel.interfaces.ws;

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
import ch.ge.cti.ct.referentiels.socioprofessionnel.interfaces.ws.model.Niveau1WS;

@RunWith(value = Parameterized.class)
@PerfTest(invocations = 1000, threads = 20)
public class RefNiveau1SearchRegexpTest extends AbstractRefWSTest {

    private final String searchString;
    private final int countNiveau1WSs;

    public RefNiveau1SearchRegexpTest(final String searchString,
	    final int countNiveau1WSs) {
	this.searchString = searchString;
	this.countNiveau1WSs = countNiveau1WSs;
    }

    @Parameters
    public static Collection<Object[]> configure() {
	return Arrays.asList(new Object[][] {
	/* 0 */{ null, 0 },
	/* 1 */{ "", 0 },
	/* 2 */{ "ersonne", 7 },
	/* 3 */{ "ravailleur", 1 },
	/* 4 */{ "rofession", 4 },
	/* 5 */{ "anuel-le-s qualifié-e-s :", 2 },
	/* 6 */{ "^Manuel-le-s qualifié-e-s :", 1 },
	/* 7 */{ "anuel-le-s qualifié-e-s : ouvriers/ières", 1 },
	/* 8 */{ "xxxxxxxx", 0 }
	/* */
	});
    }

    @Test
   
    public void test() throws ReferentielOfsException {
	final List<Niveau1WS> classes = getWS().searchNiveaux1Regexp(
		searchString);
	if (countNiveau1WSs == -1) {
	    assertNull("Niveau1s [" + searchString + "] est incorrect", classes);
	} else {
	    assertNotNull("Niveau1WSs [" + searchString + "] est incorrect",
		    classes);
	    assertEquals(
		    "Niveau1WSs [" + searchString + "].size est incorrect",
		    countNiveau1WSs, classes.size());
	}
    }
}
