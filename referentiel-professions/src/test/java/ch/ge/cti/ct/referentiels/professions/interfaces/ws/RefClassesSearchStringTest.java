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
import ch.ge.cti.ct.referentiels.professions.interfaces.ws.model.ClasseWS;

@RunWith(value = Parameterized.class)
@PerfTest(invocations = 1000, threads = 20)
public class RefClassesSearchStringTest extends AbstractRefWSTest {

    private final String searchString;
    private final int countClasses;

    public RefClassesSearchStringTest(final String searchString,
	    final int countClasses) {
	this.searchString = searchString;
	this.countClasses = countClasses;
    }

    @Parameters
    public static Collection<Object[]> configure() {
	return Arrays.asList(new Object[][] {
	/* 0 */{ null, 0 },
	/* 1 */{ "", 0 },
	/* 2 */{ "Professions", 30 },
	/* 3 */{ "professions", 30 },
	/* 4 */{ "Ingénieurs", 1 },
	/* 5 */{ "Ingenieurs", 1 },
	/* 6 */{ "xxxxxxxx", 0 }
	/* */
	});
    }

    @Test
    @Required(percentile90 = 30, percentile95 = 150)
    public void test() throws ReferentielOfsException {
	final List<ClasseWS> classes = getWS().searchClasse(searchString);
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
