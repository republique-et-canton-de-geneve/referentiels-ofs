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
package ch.ge.cti.ct.referentiels.pays.interfaces.ws;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.databene.contiperf.PerfTest;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import ch.ge.cti.ct.referentiels.ofs.ReferentielOfsException;
import ch.ge.cti.ct.referentiels.pays.model.Pays;
import ch.ge.cti.ct.referentiels.pays.service.impl.ReferentielPaysTerritoiresService;

@RunWith(value = Parameterized.class)
@PerfTest(invocations = 200, threads = 5)
public class RefPaysSearchTest extends AbstractRefWSTest {

    private final String critere;
    private final int paysCount;
    private final int continentId;
    private final int regionId;
    private final String paysNom;
    private final String iso2;

    @Parameters
    public static Collection<Object[]> configure() {
	return Arrays.asList(new Object[][] {
		/* 0 */{ null, 0, 0, 0, null, null },
		/* 1 */{ "", 0, 0, 0, null, null },
		/* 2 */{ "France", 1, 1, 14, "France", "FR" },
		/* 3 */{ "france", 1, 1, 14, "France", "FR" },
		/* 4 */{ "FRANCE", 1, 1, 14, "France", "FR" },
		/* 5 */{ "FRA", 1, 1, 14, "France", "FR" },
		/* 6 */{ "FR", 1, 1, 14, "France", "FR" },
		/* 7 */{ "Iles F�ro�", 1, 1, 11, "Iles F�ro�", "FO" },
		/* 8 */{ "Iles F�roe", 1, 1, 11, "Iles F�ro�", "FO" },
		/* 9 */{ "Iles F�roe", 1, 1, 11, "Iles F�ro�", "FO" },
		/* 10 */{ "Iles Feroe", 1, 1, 11, "Iles F�ro�", "FO" },
		/* 11 */{ "Iles", 16, 0, 0, null, null },
		/* 12 */{ "Iles ", 15, 0, 0, null, null },
		/* 13 */{ "�les ", 15, 0, 0, null, null },
		/* 14 */{ "yougoslavie", 0, 0, 0, null, null },
		/* 15 */{ "Province de Vo�vodine", 1, 1, 17,
			"Province de Vo�vodine", "" },
		/* 16 */{ "Province de Voivodine", 1, 1, 17,
			"Province de Vo�vodine", "" },
	/* */
	});
    }

    public RefPaysSearchTest(final String critere, final int paysCount,
	    final int continentId, final int regionId, final String paysNom,
	    final String iso2) throws Exception {
	this.critere = critere;
	this.paysCount = paysCount;
	this.continentId = continentId;
	this.regionId = regionId;
	this.paysNom = paysNom;
	this.iso2 = iso2;
    }

    @Test
   
    public void test() throws ReferentielOfsException {
	final List<Pays> payss = ReferentielPaysTerritoiresService.INSTANCE
		.searchPays(critere);

	assertEquals("Pays[" + critere + "].size est incorrect", paysCount,
		payss.size());
	for (final Pays pays : payss) {
	    if (continentId > 0) {
		assertEquals("Pays[" + critere + "].continentId est incorrect",
			continentId, pays.getContinentId());
	    }
	    if (regionId > 0) {
		assertEquals("Pays[" + critere + "].regionId est incorrect",
			regionId, pays.getRegionId());
	    }
	    if (paysNom != null) {
		assertEquals("Pays[" + critere + "].nom est incorrect",
			paysNom, pays.getNom());
	    }
	    if (iso2 != null) {
		assertEquals("Pays[" + critere + "].iso2 est incorrect", iso2,
			pays.getIso2());
	    }
	}
    }
}
