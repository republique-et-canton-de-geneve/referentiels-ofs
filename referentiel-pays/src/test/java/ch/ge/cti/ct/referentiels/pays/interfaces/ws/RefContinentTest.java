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
package ch.ge.cti.ct.referentiels.pays.interfaces.ws;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.util.Arrays;
import java.util.Collection;

import org.databene.contiperf.PerfTest;
import org.databene.contiperf.Required;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import ch.ge.cti.ct.referentiels.ofs.ReferentielOfsException;
import ch.ge.cti.ct.referentiels.pays.interfaces.ws.model.ContinentWS;

@RunWith(value = Parameterized.class)
@PerfTest(invocations = 1000, threads = 20)
public class RefContinentTest extends AbstractRefWSTest {

    private final int continentId;
    private final String continentName;

    @Parameters
    public static Collection<Object[]> configure() {
	return Arrays.asList(new Object[][] {
	/* 0 */{ -1, null },
	/* 1 */{ 0, null },
	/* 2 */{ 1, "Europe" },
	/* 3 */{ 2, "Afrique" },
	/* 4 */{ 3, "Amérique" },
	/* 5 */{ 4, "Asie" },
	/* 6 */{ 5, "Océanie" },
	/* 7 */{ 6, "Antarctique" },
	/* 8 */{ 7, null }
	/* */
	});
    }

    public RefContinentTest(final int continentId, final String continentName)
	    throws Exception {
	this.continentId = continentId;
	this.continentName = continentName;
    }

    @Test
   
    public void test() throws ReferentielOfsException {
	final ContinentWS continent = getWS().getContinent(continentId);
	if (continentName == null) {
	    assertNull("Continent [" + continentId + "].id est incorrect",
		    continent);
	} else {
	    assertEquals("Continent [" + continentId + "].id est incorrect",
		    continentId, continent.getId());
	    assertEquals("Continent [" + continentId + "].nom est incorrect",
		    continentName, continent.getNom());
	    assertContinent(continent);
	}
    }
}
