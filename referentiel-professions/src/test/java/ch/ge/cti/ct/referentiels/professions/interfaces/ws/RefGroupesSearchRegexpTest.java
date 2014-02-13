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
import ch.ge.cti.ct.referentiels.professions.interfaces.ws.model.GroupeWS;

@RunWith(value = Parameterized.class)
@PerfTest(invocations = 1000, threads = 20)
public class RefGroupesSearchRegexpTest extends AbstractRefWSTest {

    private final String searchString;
    private final int countGroupes;

    public RefGroupesSearchRegexpTest(final String searchString,
	    final int countGroupes) {
	this.searchString = searchString;
	this.countGroupes = countGroupes;
    }

    @Parameters
    public static Collection<Object[]> configure() {
	return Arrays.asList(new Object[][] {
	/* 0 */{ null, 0 },
	/* 1 */{ "", 0 },
	/* 2 */{ "rofessions", 64 },
	/* 3 */{ "rofessions", 64 },
	/* 4 */{ "ngénieurs", 1 },
	/* 5 */{ "ngenieurs", 1 },
	/* 6 */{ "xxxxxxxx", 0 }
	/* */
	});
    }

    @Test
    @Required(percentile90 = 5, percentile95 = 20)
    public void test() throws ReferentielOfsException {
	final List<GroupeWS> groupes = getWS().searchGroupeRegexp(searchString);
	if (countGroupes == -1) {
	    assertNull("Groupes [" + searchString + "] est incorrect", groupes);
	} else {
	    assertNotNull("Groupes [" + searchString + "] est incorrect",
		    groupes);
	    assertEquals("Groupes [" + searchString + "].size est incorrect",
		    countGroupes, groupes.size());
	}
    }
}
