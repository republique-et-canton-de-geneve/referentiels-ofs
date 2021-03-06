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
package ch.ge.cti.ct.referentiels.formeJuridique.interfaces.ws;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.util.Arrays;
import java.util.Collection;

import org.databene.contiperf.PerfTest;
import org.databene.contiperf.Required;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import ch.ge.cti.ct.referentiels.formeJuridique.model.FormeJuridique;
import ch.ge.cti.ct.referentiels.ofs.ReferentielOfsException;

@RunWith(value = Parameterized.class)
@PerfTest(invocations = 1000, threads = 20)
public class RefFormeJuridiqueTest extends AbstractRefWSTest {

    private final int formeJuridiqueId;
    private final String formeJuridiqueNom;
    private final String formeJuridiqueNomCourt;

    @Parameters
    public static Collection<Object[]> configure() {
	return Arrays.asList(new Object[][] {
	/* 0 */{ -1, null, null },
	/* 1 */{ 0, null, null },
	/*  */{ 1, "Raison individuelle", "Raison individuelle" },
	/*  */{ 2, "Soci�t� simple", "Soci�t� simple" },
	/*  */{ 3, "Soci�t� en nom collectif", "Soc. nom collectif" },
	/*  */{ 4, "Soci�t� en commandite", "Soc. en commandite" },
	/*  */{ 5, "Soci�t� en commandite par action", "Soc. comm. action" },
	/*  */{ 6, "Soci�t� anonyme", "SA" },
	/*  */{ 7, "Soci�t� � responsabilit� limit�e (SARL)", "SARL" },
	/*  */{ 40, null, null },
	/* */
	});
    }

    public RefFormeJuridiqueTest(final int formeJuridiqueId,
	    final String formeJuridiqueNom, final String formeJuridiqueNomCourt)
	    throws Exception {
	this.formeJuridiqueId = formeJuridiqueId;
	this.formeJuridiqueNom = formeJuridiqueNom;
	this.formeJuridiqueNomCourt = formeJuridiqueNomCourt;
    }

    @Test
   
    public void test() throws ReferentielOfsException {
	final FormeJuridique formeJuridique = getWS().getFormeJuridique(
		formeJuridiqueId);
	if (formeJuridiqueNom == null) {
	    assertNull("FormeJuridique [" + formeJuridiqueId
		    + "].id est incorrect", formeJuridique);
	} else {
	    assertEquals("FormeJuridique [" + formeJuridiqueId
		    + "].id est incorrect", formeJuridiqueId,
		    formeJuridique.getId());
	    assertEquals("FormeJuridique [" + formeJuridiqueId
		    + "].nom est incorrect", formeJuridiqueNom,
		    formeJuridique.getNom());
	    assertEquals("FormeJuridique [" + formeJuridiqueId
		    + "].nomCourt est incorrect", formeJuridiqueNomCourt,
		    formeJuridique.getNomCourt());
	    assertFormeJuridique(formeJuridique);
	}
    }
}
