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
package ch.ge.cti.ct.referentiels.pays.interfaces.ws;

import ch.ge.cti.ct.referentiels.pays.AbstractReferentielTest;
import org.databene.contiperf.junit.ContiPerfRule;
import org.junit.Rule;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class AbstractRefWSTest extends AbstractReferentielTest {

	protected static final Logger LOGGER = LoggerFactory.getLogger(AbstractRefWSTest.class);

    @Rule
    public ContiPerfRule rule = new ContiPerfRule();

    private ReferentielPaysTerritoiresWS ws = null;

    protected ReferentielPaysTerritoiresWS getWS() {
	  if (ws == null) {
	    ws = new ReferentielPaysTerritoiresSEI();
	  }
	  return ws;
    }

}
