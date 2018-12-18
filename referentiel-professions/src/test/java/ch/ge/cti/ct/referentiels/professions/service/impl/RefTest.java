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
package ch.ge.cti.ct.referentiels.professions.service.impl;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.apache.commons.lang.StringUtils;
import org.databene.contiperf.PerfTest;
import org.junit.Test;

import ch.ge.cti.ct.referentiels.ofs.ReferentielOfsException;
import ch.ge.cti.ct.referentiels.professions.model.Division;

@PerfTest(invocations = 1000, threads = 20)
public class RefTest extends AbstractRefTest {

    @Test
   
    public void test() throws ReferentielOfsException {
	assertTrue("ReferentielProfessions.id est incorrect",
		StringUtils.isNotBlank(ReferentielProfessionsService.INSTANCE
			.getReferentiel().getId()));
	assertNotNull("ReferentielProfessions.date est incorrect",
		ReferentielProfessionsService.INSTANCE.getReferentiel()
			.getDate());
	for (final Division division : ReferentielProfessionsService.INSTANCE
		.getReferentiel().getDivision()) {
	    assertDivision("Référentiel: ", division);
	}
    }
}
