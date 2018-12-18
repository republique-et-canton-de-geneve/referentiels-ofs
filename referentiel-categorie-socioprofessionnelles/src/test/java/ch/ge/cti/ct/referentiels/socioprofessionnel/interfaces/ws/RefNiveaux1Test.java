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

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.databene.contiperf.PerfTest;
import org.databene.contiperf.Required;
import org.junit.Test;

import ch.ge.cti.ct.referentiels.ofs.ReferentielOfsException;
import ch.ge.cti.ct.referentiels.socioprofessionnel.interfaces.ws.model.Niveau1WS;

@PerfTest(invocations = 1000, threads = 20)
public class RefNiveaux1Test extends AbstractRefWSTest {

    @Test
   
    public void test() throws ReferentielOfsException {
	final List<Niveau1WS> niveaux1 = getWS().getNiveaux1();
	assertEquals("La liste des niveaux1 est incorrecte", 17,
		niveaux1.size());
	for (final Niveau1WS niveau1WS : niveaux1) {
	    assertNiveau1WS("R�f�rentiel: ", niveau1WS);
	}
    }
}
