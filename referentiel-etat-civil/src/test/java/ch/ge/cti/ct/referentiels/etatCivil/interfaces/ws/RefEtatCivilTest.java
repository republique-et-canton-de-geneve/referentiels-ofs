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
package ch.ge.cti.ct.referentiels.etatCivil.interfaces.ws;

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

import ch.ge.cti.ct.referentiels.etatCivil.model.EtatCivil;
import ch.ge.cti.ct.referentiels.ofs.ReferentielOfsException;

@RunWith(value = Parameterized.class)
@PerfTest(invocations = 1000, threads = 20)
public class RefEtatCivilTest extends AbstractRefWSTest {

    private final int etatCivilId;
    private final String etatCivilNom;

    @Parameters
    public static Collection<Object[]> configure() {
	return Arrays.asList(new Object[][] {
	/* 0 */{ -1, null },
	/* 1 */{ 0, null },
	/*  */{ 1, "Célibataire" },
	/*  */{ 2, "Marié" },
	/*  */{ 3, "Veuf" },
	/*  */{ 4, "Divorcé" },
	/*  */{ 5, "Non marié" },
	/*  */{ 6, "Lié par un partenariat enregistré" },
	/*  */{ 7, "Partenariat dissous" },
	/*  */{ 8, null },
	/* */
	});
    }

    public RefEtatCivilTest(final int etatCivilId, final String etatCivilNom)
	    throws Exception {
	this.etatCivilId = etatCivilId;
	this.etatCivilNom = etatCivilNom;
    }

    @Test
   
    public void test() throws ReferentielOfsException {
	final EtatCivil etatCivil = getWS().getEtatCivil(etatCivilId);
	if (etatCivilNom == null) {
	    assertNull("EtatCivil [" + etatCivilId + "].id est incorrect",
		    etatCivil);
	} else {
	    assertEquals("EtatCivil [" + etatCivilId + "].id est incorrect",
		    etatCivilId, etatCivil.getId());
	    assertEquals("EtatCivil [" + etatCivilId + "].nom est incorrect",
		    etatCivilNom, etatCivil.getNom());
	    assertEtatCivil(etatCivil);
	}
    }
}
