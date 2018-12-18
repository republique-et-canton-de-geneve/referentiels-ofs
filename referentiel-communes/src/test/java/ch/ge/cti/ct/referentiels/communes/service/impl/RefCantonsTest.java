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
package ch.ge.cti.ct.referentiels.communes.service.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.databene.contiperf.PerfTest;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import ch.ge.cti.ct.referentiels.communes.model.Canton;
import ch.ge.cti.ct.referentiels.ofs.ReferentielOfsException;

@RunWith(value = Parameterized.class)
@PerfTest(invocations = 1000, threads = 20)
public class RefCantonsTest extends AbstractRefTest {

    private final Date dateValid;
    private final int countCantons;
    private final Boolean existsJU;

    @Parameters
    public static Collection<Object[]> configure() {
	return Arrays.asList(new Object[][] {
	/* 0 */{ null, 26, true },
	/* 1 */{ "01.01.1960", 25, false },
	/* 2 */{ "01.01.1979", 26, true }
	/* */
	});
    }

    public RefCantonsTest(final String dateTest, final int countCantons,
	    final Boolean existsJU) throws Exception {
	this.dateValid = date(dateTest);
	this.countCantons = countCantons;
	this.existsJU = existsJU;
    }

    @Test
   
    public void testGetCantons() throws ReferentielOfsException {
	List<Canton> cantons = null;
	if (dateValid == null) {
	    cantons = ReferentielCommunesService.INSTANCE.getCantons();
	} else {
	    cantons = ReferentielCommunesService.INSTANCE.getCantons(dateValid);
	}
	final String msgPrefix = "getCantons(" + format(dateValid) + "): ";
	assertNotNull(msgPrefix + " la liste des cantons est vide", cantons);
	assertEquals(msgPrefix + " le nombre de cantons est incorrect",
		countCantons, cantons.size());
	boolean foundJU = false;
	for (Canton c : cantons) {
	    foundJU |= c.getCode().equals("JU");
	}
	assertEquals(msgPrefix
		+ " la pr�sence du canton [JU] dans la liste est incorrecte",
		existsJU, foundJU);

	assertValidCantons(msgPrefix, cantons, dateValid);
    }
}
