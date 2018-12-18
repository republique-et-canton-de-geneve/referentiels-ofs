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
package ch.ge.cti.ct.referentiels.professions.service.impl;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.databene.contiperf.PerfTest;
import org.junit.Test;

import ch.ge.cti.ct.referentiels.ofs.ReferentielOfsException;
import ch.ge.cti.ct.referentiels.professions.model.Division;

@PerfTest(invocations = 1000, threads = 20)
public class RefDivisionsTest extends AbstractRefTest {

    @Test
   
    public void test() throws ReferentielOfsException {
	final List<Division> divisions = ReferentielProfessionsService.INSTANCE
		.getDivisions();
	assertEquals("La liste des divisions est incorrecte", 9,
		divisions.size());
	for (final Division division : divisions) {
	    assertDivision("R�f�rentiel: ", division);
	}
    }
}
