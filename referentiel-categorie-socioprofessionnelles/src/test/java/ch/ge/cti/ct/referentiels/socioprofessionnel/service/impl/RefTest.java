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

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.apache.commons.lang.StringUtils;
import org.databene.contiperf.PerfTest;
import org.junit.Test;

import ch.ge.cti.ct.referentiels.ofs.ReferentielOfsException;
import ch.ge.cti.ct.referentiels.socioprofessionnel.model.Niveau1;

@PerfTest(invocations = 1000, threads = 20)
public class RefTest extends AbstractRefTest {

    @Test
   
    public void test() throws ReferentielOfsException {
	assertTrue(
		"ReferentielSocioprofessionnel.id est incorrect",
		StringUtils
			.isNotBlank(ReferentielSocioprofessionnelService.INSTANCE
				.getReferentiel().getId()));
	assertNotNull("ReferentielSocioprofessionnel.date est incorrect",
		ReferentielSocioprofessionnelService.INSTANCE.getReferentiel()
			.getDate());
	for (final Niveau1 niveau1 : ReferentielSocioprofessionnelService.INSTANCE
		.getReferentiel().getNiveau1()) {
	    assertNiveau1("Référentiel: ", niveau1);
	}
    }
}
