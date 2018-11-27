package ch.ge.cti.ct.referentiels.socioprofessionnel.service.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.databene.contiperf.PerfTest;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import ch.ge.cti.ct.referentiels.ofs.ReferentielOfsException;
import ch.ge.cti.ct.referentiels.socioprofessionnel.model.Niveau1;

@RunWith(value = Parameterized.class)
@PerfTest(invocations = 1000, threads = 20)
public class RefNiveau1SearchRegexpTest extends AbstractRefTest {

    private final String searchString;
    private final int countNiveau1s;

    public RefNiveau1SearchRegexpTest(final String searchString,
	    final int countNiveau1s) {
	this.searchString = searchString;
	this.countNiveau1s = countNiveau1s;
    }

    @Parameters
    public static Collection<Object[]> configure() {
	return Arrays.asList(new Object[][] {
	/* 0 */{ null, -1 },
	/* 1 */{ "", -1 },
	/* 2 */{ "ersonne", 7 },
	/* 3 */{ "ravailleur", 1 },
	/* 4 */{ "rofession", 4 },
	/* 5 */{ "anuel-le-s qualifié-e-s :", 2 },
	/* 6 */{ "^Manuel-le-s qualifié-e-s :", 1 },
	/* 7 */{ "anuel-le-s qualifié-e-s : ouvriers/ières", 1 },
	/* 8 */{ "xxxxxxxx", 0 }
	/* */
	});
    }

    @Test
   
    public void test() throws ReferentielOfsException {
	final List<Niveau1> classes = ReferentielSocioprofessionnelService.INSTANCE
		.searchNiveaux1Regexp(searchString);
	if (countNiveau1s == -1) {
	    assertNull("Niveau1s [" + searchString + "] est incorrect", classes);
	} else {
	    assertNotNull("Niveau1s [" + searchString + "] est incorrect",
		    classes);
	    assertEquals("Niveau1s [" + searchString + "].size est incorrect",
		    countNiveau1s, classes.size());
	}
    }
}
