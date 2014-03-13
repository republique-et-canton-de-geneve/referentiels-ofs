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
import ch.ge.cti.ct.referentiels.professions.model.Division;

@RunWith(value = Parameterized.class)
@PerfTest(invocations = 1000, threads = 20)
public class RefDivisionSearchStringTest extends AbstractRefTest {

    private final String searchString;
    private final int countDivisions;

    public RefDivisionSearchStringTest(final String searchString,
	    final int countDivisions) {
	this.searchString = searchString;
	this.countDivisions = countDivisions;
    }

    @Parameters
    public static Collection<Object[]> configure() {
	return Arrays.asList(new Object[][] {
	/* 0 */{ null, -1 },
	/* 1 */{ "", -1 },
	/* 2 */{ "Professions", 8 },
	/* 3 */{ "professions", 8 },
	/* 4 */{ "Indica", 1 },
	/* 5 */{ "Professions de la santé", 1 },
	/* 6 */{ "xxxxxxxx", 0 }
	/* */
	});
    }

    @Test
    @Required(percentile90 = 30, percentile95 = 150)
    public void test() throws ReferentielOfsException {
	final List<Division> classes = ReferentielProfessionsService.instance
		.searchDivisionRegexp(searchString);
	if (countDivisions == -1) {
	    assertNull("Divisions [" + searchString + "] est incorrect",
		    classes);
	} else {
	    assertNotNull("Divisions [" + searchString + "] est incorrect",
		    classes);
	    assertEquals("Divisions [" + searchString + "].size est incorrect",
		    countDivisions, classes.size());
	}
    }
}
