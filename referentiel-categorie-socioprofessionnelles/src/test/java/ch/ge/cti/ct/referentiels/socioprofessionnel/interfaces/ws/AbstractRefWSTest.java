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
package ch.ge.cti.ct.referentiels.socioprofessionnel.interfaces.ws;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.apache.commons.lang.StringUtils;
import org.databene.contiperf.junit.ContiPerfRule;
import org.junit.BeforeClass;
import org.junit.Rule;

import ch.ge.cti.ct.referentiels.ofs.ReferentielOfsException;
import ch.ge.cti.ct.referentiels.socioprofessionnel.AbstractReferentielTest;
import ch.ge.cti.ct.referentiels.socioprofessionnel.interfaces.ws.model.Niveau1WS;
import ch.ge.cti.ct.referentiels.socioprofessionnel.interfaces.ws.model.Niveau2WS;

public abstract class AbstractRefWSTest extends AbstractReferentielTest {

    @Rule
    public ContiPerfRule rule = new ContiPerfRule();

    @BeforeClass
    public static void load() throws ReferentielOfsException {
	// chargement du fichier SDMX
	new ReferentielSocioprofessionnelSEI().getNiveaux1();
    }

    private ReferentielSocioprofessionnelWS ws = null;

    protected ReferentielSocioprofessionnelWS getWS()
	    throws ReferentielOfsException {
	if (ws == null) {
	    ws = new ReferentielSocioprofessionnelSEI();
	}
	return ws;
    }

    public void assertNiveau1WS(final String message, final Niveau1WS niveau1WS) {
	assertNotNull(message + " Niveau1WS est null", niveau1WS);
	assertTrue(message + ": Niveau1WS.id est incorrect",
		niveau1WS.getId() > 0);
	assertTrue(message + ": Niveau1WS.nom est incorrect",
		StringUtils.isNotBlank(niveau1WS.getNom()));
    }

    public void assertNiveau2WS(final String message, final Niveau2WS niveau2WS) {
	assertNotNull(message + " Niveau2WS est null", niveau2WS);
	assertTrue(message + ": Niveau2WS.id est incorrect",
		niveau2WS.getId() > 0);
	assertTrue(message + ": Niveau2WS.nom est incorrect",
		StringUtils.isNotBlank(niveau2WS.getNom()));
	assertTrue(message + ": Niveau2WS.Niveau1WSId est incorrect",
		niveau2WS.getNiveau1Id() > 0);
    }
}
