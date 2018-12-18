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
package ch.ge.cti.ct.referentiels.socioprofessionnel.service.impl;

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
import ch.ge.cti.ct.referentiels.socioprofessionnel.model.Niveau2;

@RunWith(value = Parameterized.class)
@PerfTest(invocations = 1000, threads = 20)
public class RefNiveau2Test extends AbstractRefTest {

    private final int niveau2Id;
    private final String niveau2Nom;

    public RefNiveau2Test(final int niveau2Id, final String niveau2Nom) {
	this.niveau2Id = niveau2Id;
	this.niveau2Nom = niveau2Nom;
    }

    @Parameters
    public static Collection<Object[]> configure() {
	return Arrays.asList(new Object[][] {
	/* 0 */{ -1, null },
	/* 1 */{ 0, null },
	/* 2 */{ 101, "Dirigeant-e-s du secteur privé" },
	/* 3 */{ 102, "Dirigeant-e-s du secteur public" },
	/* 4 */{ 201, "Professions libérales et assimilées (secteur privé)" },
	/* 5 */{ 301, "Autres indépendant-e-s (secteur privé)" },
	/* 6 */{ 99, null }
	/* */
	});
    }

    @Test
   
    public void test() throws ReferentielOfsException {
	final Niveau2 niveau2 = ReferentielSocioprofessionnelService.INSTANCE
		.getNiveau2(niveau2Id);
	if (niveau2Nom == null) {
	    assertNull("Niveau2 [" + niveau2Id + "] est incorrect", niveau2);
	} else {
	    assertEquals("Niveau2 [" + niveau2Id + "].id est incorrect",
		    niveau2Id, niveau2.getId());
	    assertEquals("Niveau2 [" + niveau2Id + "].nom est incorrect",
		    niveau2Nom, niveau2.getNom());
	    assertNiveau2("Niveau2 [" + niveau2Id + "]: ", niveau2);

	}
    }
}
