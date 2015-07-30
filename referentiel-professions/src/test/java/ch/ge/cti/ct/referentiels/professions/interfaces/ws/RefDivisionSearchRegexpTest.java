package ch.ge.cti.ct.referentiels.professions.interfaces.ws;

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
import ch.ge.cti.ct.referentiels.professions.interfaces.ws.model.DivisionWS;

@RunWith(value = Parameterized.class)
@PerfTest(invocations = 1000, threads = 20)
public class RefDivisionSearchRegexpTest extends AbstractRefWSTest {

    private final String searchString;
    private final int countDivisions;

    public RefDivisionSearchRegexpTest(final String searchString,
	    final int countDivisions) {
	this.searchString = searchString;
	this.countDivisions = countDivisions;
    }

    @Parameters
    public static Collection<Object[]> configure() {
	return Arrays.asList(new Object[][] {
	/* 0 */{ null, 0 },
	/* 1 */{ "", 0 },
	/* 2 */{ "rofessions", 8 },
	/* 3 */{ "rofessions", 8 },
	/* 4 */{ "ndica", 1 },
	/* 5 */{ "santé", 1 },
	/* 6 */{ "xxxxxxxx", 0 }
	/* */
	});
    }

    @Test
   
    public void test() throws ReferentielOfsException {
	final List<DivisionWS> classes = getWS().searchDivisionRegexp(
		searchString);
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
