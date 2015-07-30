package ch.ge.cti.ct.referentiels.socioprofessionnel.service.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.databene.contiperf.PerfTest;
import org.databene.contiperf.Required;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import ch.ge.cti.ct.referentiels.ofs.ReferentielOfsException;
import ch.ge.cti.ct.referentiels.socioprofessionnel.model.Niveau1;

@RunWith(value = Parameterized.class)
@PerfTest(invocations = 1000, threads = 20)
public class RefNiveau1SearchStringTest extends AbstractRefTest {

    private final String searchString;
    private final int countNiveaux1;

    public RefNiveau1SearchStringTest(final String searchString,
	    final int countNiveau1s) {
	this.searchString = searchString;
	this.countNiveaux1 = countNiveau1s;
    }

    @Parameters
    public static Collection<Object[]> configure() {
	return Arrays.asList(new Object[][] {
	/* 0 */{ null, -1 },
	/* 1 */{ "", -1 },
	/* 2 */{ "Personne", 6 },
	/* 3 */{ "Travailleur", 1 },
	/* 4 */{ "Profession", 3 },
	/* 5 */{ "Manuel-le-s qualifié-e-s ", 1 },
	/* 6 */{ "Manuel-le-s qualifié-e-s :", 1 },
	/* 7 */{ "Manuel-le-s qualifié-e-s : ouvriers/ières", 1 },
	/* 8 */{ "xxxxxxxx", 0 }
	/* */
	});
    }

    @Test
   
    public void test() throws ReferentielOfsException {
	final List<Niveau1> classes = ReferentielSocioprofessionnelService.instance
		.searchNiveaux1(searchString);
	if (countNiveaux1 == -1) {
	    assertNull("Niveau1 [" + searchString + "] est incorrect", classes);
	} else {
	    assertNotNull("Niveau1 [" + searchString + "] est incorrect",
		    classes);
	    assertEquals("Niveau1 [" + searchString + "].size est incorrect",
		    countNiveaux1, classes.size());
	}
    }
}
