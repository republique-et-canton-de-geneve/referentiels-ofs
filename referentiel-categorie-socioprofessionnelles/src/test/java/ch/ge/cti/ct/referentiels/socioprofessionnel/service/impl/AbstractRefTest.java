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

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.apache.commons.lang.StringUtils;
import org.databene.contiperf.junit.ContiPerfRule;
import org.junit.BeforeClass;
import org.junit.Rule;

import ch.ge.cti.ct.referentiels.ofs.ReferentielOfsException;
import ch.ge.cti.ct.referentiels.socioprofessionnel.AbstractReferentielTest;
import ch.ge.cti.ct.referentiels.socioprofessionnel.model.Niveau1;
import ch.ge.cti.ct.referentiels.socioprofessionnel.model.Niveau2;

public abstract class AbstractRefTest extends AbstractReferentielTest {

    @Rule
    public ContiPerfRule rule = new ContiPerfRule();

    @BeforeClass
    public static void load() throws ReferentielOfsException {
	ReferentielSocioprofessionnelService.INSTANCE.getReferentiel();
    }

    public void assertNiveau1(final String message, final Niveau1 niveau1) {
	assertNotNull(message + " niveau1 est null", niveau1);
	assertTrue(message + ": niveau1.id est incorrect", niveau1.getId() > 0);
	assertTrue(message + ": niveau1.nom est incorrect",
		StringUtils.isNotBlank(niveau1.getNom()));
	assertTrue(message + ": niveau1.niveau2 est incorrect", niveau1
		.getNiveau2().size() > 0);
	for (final Niveau2 niveau2 : niveau1.getNiveau2()) {
	    assertNiveau2(message, niveau2);
	}
    }

    public void assertNiveau2(final String message, final Niveau2 niveau2) {
	assertNotNull(message + " niveau2 est null", niveau2);
	assertTrue(message + ": niveau2.id est incorrect", niveau2.getId() > 0);
	assertTrue(message + ": niveau2.nom est incorrect",
		StringUtils.isNotBlank(niveau2.getNom()));
	assertTrue(message + ": niveau2.niveau1Id est incorrect",
		niveau2.getNiveau1Id() > 0);
    }
}
