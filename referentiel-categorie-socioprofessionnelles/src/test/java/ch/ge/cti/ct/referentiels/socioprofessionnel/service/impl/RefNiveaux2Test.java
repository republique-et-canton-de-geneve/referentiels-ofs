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

import java.util.List;

import org.databene.contiperf.PerfTest;
import org.junit.Test;

import ch.ge.cti.ct.referentiels.ofs.ReferentielOfsException;
import ch.ge.cti.ct.referentiels.socioprofessionnel.model.Niveau2;

@PerfTest(invocations = 1000, threads = 20)
public class RefNiveaux2Test extends AbstractRefTest {

    @Test
   
    public void test() throws ReferentielOfsException {
	final List<Niveau2> niveaux2s = ReferentielSocioprofessionnelService.INSTANCE
		.getNiveaux2();
	assertEquals("La liste des groupes est incorrecte", 31,
		niveaux2s.size());
	for (final Niveau2 niveau2 : niveaux2s) {
	    assertNiveau2("Référentiel: ", niveau2);
	}
    }
}
