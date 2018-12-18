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
package ch.ge.cti.ct.referentiels.professions.service.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.util.Arrays;
import java.util.Collection;

import org.databene.contiperf.PerfTest;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import ch.ge.cti.ct.referentiels.ofs.ReferentielOfsException;
import ch.ge.cti.ct.referentiels.professions.model.Classe;

@RunWith(value = Parameterized.class)
@PerfTest(invocations = 1000, threads = 20)
public class RefClasseTest extends AbstractRefTest {

    private final int classeId;
    private final String classeNom;
    private final int countGroupes;

    public RefClasseTest(final int classeId, final String classeNom,
	    final int countGroupes) {
	this.classeId = classeId;
	this.classeNom = classeNom;
	this.countGroupes = countGroupes;
    }

    @Parameters
    public static Collection<Object[]> configure() {
	return Arrays
		.asList(new Object[][] {
			/* 0 */{ -1, null, 0 },
			/* 1 */{ 0, null, 0 },
			/* 2 */{
				11,
				"Professions de l'agriculture, de l'�conomie foresti�re, de l'�levage et des soins aux animaux",
				5 },
			/* 3 */{
				21,
				"Professions de la production de denr�es alimentaires, de boissons et de tabacs",
				3 },
			/* 4 */{ 31, "Ing�nieurs", 1 },
			/* 5 */{ 41, "Professions de la construction", 2 },
			/* 6 */{ 51, "Professions commerciales et de la vente",
				1 },
			/* 7 */{
				61,
				"Professions de l'h�tellerie et de la restauration et de l'�conomie domestique",
				2 },
			/* 8 */{
				71,
				"Entrepreneurs, directeurs et fonctionnaires sup�rieurs",
				1 },
			/* 9 */{
				81,
				"Professions des m�dias et professions apparent�es",
				3 },
			/* 10 */{ 91, "Professions du secteur tertiaire spa", 1 },
			/* 11 */{ 99, null, 0 }
		/* */
		});
    }

    @Test
   
    public void test() throws ReferentielOfsException {
	final Classe classe = ReferentielProfessionsService.INSTANCE
		.getClasse(classeId);
	if (classeNom == null) {
	    assertNull("Classe [" + classeId + "] est incorrect", classe);
	} else {
	    assertEquals("Classe [" + classeId + "].id est incorrect",
		    classeId, classe.getId());
	    assertEquals("Classe [" + classeId + "].nom est incorrect",
		    classeNom, classe.getNom());
	    assertEquals("Liste des classe de classe [" + classeId
		    + "] est incorrecte", countGroupes, classe.getGroupe()
		    .size());
	    assertClasse("Classe [" + classeId + "]: ", classe);

	}
    }
}
