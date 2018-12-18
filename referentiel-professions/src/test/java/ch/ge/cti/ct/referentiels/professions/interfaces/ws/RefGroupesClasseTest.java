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
import ch.ge.cti.ct.referentiels.professions.interfaces.ws.model.GroupeWS;

@RunWith(value = Parameterized.class)
@PerfTest(invocations = 1000, threads = 20)
public class RefGroupesClasseTest extends AbstractRefWSTest {

    private final int classeId;
    private final int countGroupe;

    public RefGroupesClasseTest(final int classeId, final int countGroupe) {
	this.classeId = classeId;
	this.countGroupe = countGroupe;
    }

    @Parameters
    public static Collection<Object[]> configure() {
	return Arrays.asList(new Object[][] {
	/* 0 */{ -1, 0 },
	/* 1 */{ 0, 0 },
	/* 2 */{ 11, 5 },
	/* 3 */{ 21, 3 },
	/* 4 */{ 31, 1 },
	/* 5 */{ 41, 2 },
	/* 6 */{ 51, 1 },
	/* 7 */{ 61, 2 },
	/* 8 */{ 71, 1 },
	/* 9 */{ 81, 3 },
	/* 10 */{ 91, 1 },
	/* 11 */{ 99, 0 }
	/* */
	});
    }

    @Test
   
    public void test() throws ReferentielOfsException {
	final List<GroupeWS> groupes = getWS().getGroupesByClasse(classeId);
	if (countGroupe == -1) {
	    assertNull("Groupes [" + classeId + "] est incorrect", groupes);
	} else {
	    assertNotNull("Groupes [" + classeId + "] est incorrect", groupes);
	    assertEquals("Groupes [" + classeId + "].size est incorrect",
		    countGroupe, groupes.size());
	    for (final GroupeWS groupe : groupes) {
		assertEquals("Groupes [" + classeId + "].classeId est null",
			classeId, groupe.getClasseId());
	    }
	}
    }
}
