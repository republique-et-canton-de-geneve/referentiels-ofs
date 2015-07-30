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
				"Professions de l'agriculture, de l'économie forestière, de l'élevage et des soins aux animaux" },
			/* 3 */{
				21,
				"Professions de la production de denrées alimentaires, de boissons et de tabacs" },
			/* 4 */{ 31, "Ingénieurs" },
			/* 5 */{ 41, "Professions de la construction" },
			/* 6 */{ 51, "Professions commerciales et de la vente" },
			/* 7 */{ 61,
				"Professions de l'hôtellerie et de la restauration et de l'économie domestique" },
			/* 8 */{ 71,
				"Entrepreneurs, directeurs et fonctionnaires supérieurs" },
			/* 9 */{ 81,
				"Professions des médias et professions apparentées" },
			/* 10 */{ 91, "Professions du secteur tertiaire spa" },
			/* 11 */{ 99, null }
		/* */
		});
    }

    @Test
    @Required(percentile90 = 30, percentile95 = 150)
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
