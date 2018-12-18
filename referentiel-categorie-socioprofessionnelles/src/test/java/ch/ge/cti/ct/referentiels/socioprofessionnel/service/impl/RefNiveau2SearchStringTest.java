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
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.databene.contiperf.PerfTest;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import ch.ge.cti.ct.referentiels.ofs.ReferentielOfsException;
import ch.ge.cti.ct.referentiels.socioprofessionnel.model.Niveau2;

@RunWith(value = Parameterized.class)
@PerfTest(invocations = 1000, threads = 20)
public class RefNiveau2SearchStringTest extends AbstractRefTest {

    private final String searchString;
    private final int countNiveau2s;

    public RefNiveau2SearchStringTest(final String searchString,
	    final int countNiveau2s) {
	this.searchString = searchString;
	this.countNiveau2s = countNiveau2s;
    }

    @Parameters
    public static Collection<Object[]> configure() {
	return Arrays.asList(new Object[][] {
	/* 0 */{ null, -1 },
	/* 1 */{ "", -1 },
	/* 2 */{ "Dirigeant", 3 },
	/* 3 */{ "dirigeant", 3 },
	/* 4 */{ "Personnes", 6 },
	/* 5 */{ "p�rsonnes", 6 },
	/* 6 */{ "xxxxxxxx", 0 }
	/* */
	});
    }

    @Test
   
    public void test() throws ReferentielOfsException {
	final List<Niveau2> niveau2s = ReferentielSocioprofessionnelService.INSTANCE
		.searchNiveaux2(searchString);
	if (countNiveau2s == -1) {
	    assertNull("Niveau2s [" + searchString + "] est incorrect",
		    niveau2s);
	} else {
	    assertNotNull("Niveau2s [" + searchString + "] est incorrect",
		    niveau2s);
	    assertEquals("Niveau2s [" + searchString + "].size est incorrect",
		    countNiveau2s, niveau2s.size());
	}
    }
}
