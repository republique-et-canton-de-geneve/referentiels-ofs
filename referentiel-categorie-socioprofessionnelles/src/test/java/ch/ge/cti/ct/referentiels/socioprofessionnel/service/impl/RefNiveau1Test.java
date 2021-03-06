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
import ch.ge.cti.ct.referentiels.socioprofessionnel.model.Niveau1;

@RunWith(value = Parameterized.class)
@PerfTest(invocations = 1000, threads = 20)
public class RefNiveau1Test extends AbstractRefTest {

    private final int niveau1Id;
    private final String niveau1Nom;
    private final int countNiveau2;

    public RefNiveau1Test(final int niveau1Id, final String niveau1Nom,
	    final int countNiveau2) {
	this.niveau1Id = niveau1Id;
	this.niveau1Nom = niveau1Nom;
	this.countNiveau2 = countNiveau2;
    }

    @Parameters
    public static Collection<Object[]> configure() {
	return Arrays
		.asList(new Object[][] {
			/* 0 */{ -1, null, 0 },
			/* 1 */{ 0, null, 0 },
			/* 2 */{ 10, "Dirigeant-e-s", 3 },
			/* 3 */{ 20, "Professions lib�rales et assimil�es", 1 },
			/* 4 */{ 30, "Autres ind�pendant-e-s", 1 },
			/* 5 */{ 60,
				"Non-manuel-le-s qualifi�-e-s�: employ�-e-s", 3 },
			/* 6 */{
				91,
				"Personnes actives occup�es non attribuables (information manquante ou non plausible)",
				1 },
			/* 7 */{ 97, "Autres personnes non actives", 1 },
			/* 8 */{ 98, "Enfants de moins de 15 ans", 1 },
			/* 9 */{ 100, null, 0 }
		/* */
		});
    }

    @Test
   
    public void test() throws ReferentielOfsException {
	final Niveau1 niveau1 = ReferentielSocioprofessionnelService.INSTANCE
		.getNiveau1(niveau1Id);
	if (niveau1Nom == null) {
	    assertNull("Niveau1 [" + niveau1Id + "] est incorrect", niveau1);
	} else {
	    assertEquals("Niveau1 [" + niveau1Id + "].id est incorrect",
		    niveau1Id, niveau1.getId());
	    assertEquals("Niveau1 [" + niveau1Id + "].nom est incorrect",
		    niveau1Nom, niveau1.getNom());
	    assertEquals("Liste des classe de niveau1 [" + niveau1Id
		    + "] est incorrecte", countNiveau2, niveau1.getNiveau2()
		    .size());
	    assertNiveau1("Niveau1 [" + niveau1Id + "]: ", niveau1);

	}
    }
}
