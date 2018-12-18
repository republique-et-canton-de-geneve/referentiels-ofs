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
package ch.ge.cti.ct.referentiels.pays.service.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.util.Arrays;
import java.util.Collection;

import org.databene.contiperf.PerfTest;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import ch.ge.cti.ct.referentiels.ofs.ReferentielOfsException;
import ch.ge.cti.ct.referentiels.pays.model.Continent;

@RunWith(value = Parameterized.class)
@PerfTest(invocations = 1000, threads = 20)
public class RefContinentTest extends AbstractRefTest {

    private final int continentId;
    private final String continentName;
    private final int countRegions;

    @Parameters
    public static Collection<Object[]> configure() {
	return Arrays.asList(new Object[][] {
	/* 0 */{ -1, null, 0 },
	/* 1 */{ 0, null, 0 },
	/* 2 */{ 1, "Europe", 7 },
	/* 3 */{ 2, "Afrique", 7 },
	/* 4 */{ 3, "Amérique", 6 },
	/* 5 */{ 4, "Asie", 7 },
	/* 6 */{ 5, "Océanie", 4 },
	/* 7 */{ 6, "Antarctique", 1 },
	/* 8 */{ 7, null, 0 }
	/* */
	});
    }

    public RefContinentTest(final int continentId, final String continentName,
	    final int countRegions) throws Exception {
	this.continentId = continentId;
	this.countRegions = countRegions;
	this.continentName = continentName;
    }

    @Test
   
    public void test() throws ReferentielOfsException {
	final Continent continent = ReferentielPaysTerritoiresService.INSTANCE
		.getContinent(continentId);
	if (continentName == null) {
	    assertNull("Continent [" + continentId + "].id est incorrect",
		    continent);
	} else {
	    assertEquals("Continent [" + continentId + "].id est incorrect",
		    continentId, continent.getId());
	    assertEquals("Continent [" + continentId + "].nom est incorrect",
		    continentName, continent.getNom());
	    assertEquals("Liste des régions du continent [" + continentId
		    + "] est incorrecte", countRegions, continent.getRegion()
		    .size());
	    assertContinent(continent);
	}
    }
}
