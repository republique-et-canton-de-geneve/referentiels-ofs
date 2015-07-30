package ch.ge.cti.ct.referentiels.professions.service.impl;

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
				"Professions de l'agriculture, de l'économie forestière, de l'élevage et des soins aux animaux",
				5 },
			/* 3 */{
				21,
				"Professions de la production de denrées alimentaires, de boissons et de tabacs",
				3 },
			/* 4 */{ 31, "Ingénieurs", 1 },
			/* 5 */{ 41, "Professions de la construction", 2 },
			/* 6 */{ 51, "Professions commerciales et de la vente",
				1 },
			/* 7 */{
				61,
				"Professions de l'hôtellerie et de la restauration et de l'économie domestique",
				2 },
			/* 8 */{
				71,
				"Entrepreneurs, directeurs et fonctionnaires supérieurs",
				1 },
			/* 9 */{
				81,
				"Professions des médias et professions apparentées",
				3 },
			/* 10 */{ 91, "Professions du secteur tertiaire spa", 1 },
			/* 11 */{ 99, null, 0 }
		/* */
		});
    }

    @Test
   
    public void test() throws ReferentielOfsException {
	final Classe classe = ReferentielProfessionsService.instance
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
