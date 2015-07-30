package ch.ge.cti.ct.referentiels.professions.service.impl;

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
import ch.ge.cti.ct.referentiels.professions.model.Classe;

@RunWith(value = Parameterized.class)
@PerfTest(invocations = 1000, threads = 20)
public class RefClassesSearchRegexpTest extends AbstractRefTest {

    private final String searchString;
    private final int countClasses;

    public RefClassesSearchRegexpTest(final String searchString,
	    final int countClasses) {
	this.searchString = searchString;
	this.countClasses = countClasses;
    }

    @Parameters
    public static Collection<Object[]> configure() {
	return Arrays.asList(new Object[][] {
	/* 0 */{ null, -1 },
	/* 1 */{ "", -1 },
	/* 2 */{ "rofessions", 31 },
	/* 3 */{ "rofessions", 31 },
	/* 4 */{ "ngénieurs", 1 },
	/* 5 */{ "ngenieurs", 1 },
	/* 6 */{ "xxxxxxxx", 0 }
	/* */
	});
    }

    @Test
   
    public void test() throws ReferentielOfsException {
	final List<Classe> classes = ReferentielProfessionsService.instance
		.searchClasseRegexp(searchString);
	if (countClasses == -1) {
	    assertNull("Classes [" + searchString + "] est incorrect", classes);
	} else {
	    assertNotNull("Classes [" + searchString + "] est incorrect",
		    classes);
	    assertEquals("Classes [" + searchString + "].size est incorrect",
		    countClasses, classes.size());
	}
    }
}
