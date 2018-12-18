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
package ch.ge.cti.ct.referentiels.etatCivil.interfaces.ws;

import static org.junit.Assert.assertTrue;

import org.apache.commons.lang.StringUtils;
import org.databene.contiperf.junit.ContiPerfRule;
import org.junit.BeforeClass;
import org.junit.Rule;

import ch.ge.cti.ct.referentiels.etatCivil.AbstractReferentielTest;
import ch.ge.cti.ct.referentiels.etatCivil.model.EtatCivil;
import ch.ge.cti.ct.referentiels.ofs.ReferentielOfsException;

public abstract class AbstractRefWSTest extends AbstractReferentielTest {

    @Rule
    public ContiPerfRule rule = new ContiPerfRule();

    @BeforeClass
    public static void load() throws ReferentielOfsException {
	new ReferentielEtatCivilSEI().getEtatsCivils();
    }

    private ReferentielEtatCivilWS ws = null;

    protected ReferentielEtatCivilWS getWS() throws ReferentielOfsException {
	if (ws == null) {
	    ws = new ReferentielEtatCivilSEI();
	}
	return ws;
    }

    protected void assertEtatCivil(final EtatCivil etatCivil) {
	assertTrue("EtatCivil.id est incorrect", etatCivil.getId() > 0);
	assertTrue("EtatCivil.nom est incorrect",
		StringUtils.isNotBlank(etatCivil.getNom()));
    }

}
