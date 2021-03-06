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
package ch.ge.cti.ct.referentiels.professions.interfaces.ws;

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

import ch.ge.cti.ct.referentiels.ofs.ReferentielOfsException;
import ch.ge.cti.ct.referentiels.professions.interfaces.ws.model.ClasseWS;

@RunWith(value = Parameterized.class)
@PerfTest(invocations = 1000, threads = 20)
public class RefClasseTest extends AbstractRefWSTest {

    private final int classeId;
    private final String classeNom;

    public RefClasseTest(final int classeId, final String classeNom) {
	this.classeId = classeId;
	this.classeNom = classeNom;
    }

    @Parameters
    public static Collection<Object[]> configure() {
	return Arrays
		.asList(new Object[][] {
			/* 0 */{ -1, null },
			/* 1 */{ 0, null },
			/* 2 */{
				11,
				"Professions de l'agriculture, de l'�conomie foresti�re, de l'�levage et des soins aux animaux" },
			/* 3 */{
				21,
				"Professions de la production de denr�es alimentaires, de boissons et de tabacs" },
			/* 4 */{ 31, "Ing�nieurs" },
			/* 5 */{ 41, "Professions de la construction" },
			/* 6 */{ 51, "Professions commerciales et de la vente" },
			/* 7 */{ 61,
				"Professions de l'h�tellerie et de la restauration et de l'�conomie domestique" },
			/* 8 */{ 71,
				"Entrepreneurs, directeurs et fonctionnaires sup�rieurs" },
			/* 9 */{ 81,
				"Professions des m�dias et professions apparent�es" },
			/* 10 */{ 91, "Professions du secteur tertiaire spa" },
			/* 11 */{ 99, null }
		/* */
		});
    }

    @Test
   
    public void test() throws ReferentielOfsException {
	final ClasseWS classe = getWS().getClasse(classeId);
	if (classeNom == null) {
	    assertNull("ClasseWS [" + classeId + "] est incorrect", classe);
	} else {
	    assertEquals("ClasseWS [" + classeId + "].id est incorrect",
		    classeId, classe.getId());
	    assertEquals("ClasseWS [" + classeId + "].nom est incorrect",
		    classeNom, classe.getNom());
	    assertClasse("ClasseWS [" + classeId + "]: ", classe);
	}
    }
}
