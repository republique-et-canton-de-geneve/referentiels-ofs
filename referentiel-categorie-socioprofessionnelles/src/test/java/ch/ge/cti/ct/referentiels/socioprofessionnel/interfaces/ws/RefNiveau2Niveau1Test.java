package ch.ge.cti.ct.referentiels.socioprofessionnel.interfaces.ws;

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
import ch.ge.cti.ct.referentiels.socioprofessionnel.interfaces.ws.model.Niveau2WS;

@RunWith(value = Parameterized.class)
@PerfTest(invocations = 1000, threads = 20)
public class RefNiveau2Niveau1Test extends AbstractRefWSTest {

    private final int niveau1Id;
    private final int countNiveau2WSs;

    public RefNiveau2Niveau1Test(final int niveau1Id, final int countNiveau2WSs) {
	this.niveau1Id = niveau1Id;
	this.countNiveau2WSs = countNiveau2WSs;
    }

    @Parameters
    public static Collection<Object[]> configure() {
	return Arrays.asList(new Object[][] {
	/* 0 */{ -1, -1 },
	/* 1 */{ 0, -1 },
	/* 2 */{ 10, 3 },
	/* 3 */{ 20, 1 },
	/* 4 */{ 30, 1 },
	/* 5 */{ 40, 3 },
	/* 6 */{ 50, 3 },
	/* 7 */{ 60, 3 },
	/* 8 */{ 90, 3 },
	/* 9 */{ 91, 1 },
	/* 10 */{ 97, 1 },
	/* 11 */{ 99, 0 }
	/* */
	});
    }

    @Test
    @Required(percentile90 = 30, percentile95 = 150)
    public void test() throws ReferentielOfsException {
	final List<Niveau2WS> Niveau2WSs = getWS().getNiveaux2ByNiveau1(
		niveau1Id);
	if (countNiveau2WSs == -1) {
	    assertNull("Niveau2WSs [" + niveau1Id + "] est incorrect",
		    Niveau2WSs);
	} else {
	    assertNotNull("Niveau2WSs [" + niveau1Id + "] est incorrect",
		    Niveau2WSs);
	    assertEquals("Niveau2WSs [" + niveau1Id + "].size est incorrect",
		    countNiveau2WSs, Niveau2WSs.size());
	    for (final Niveau2WS Niveau2WS : Niveau2WSs) {
		assertEquals("Niveau2WSs [" + niveau1Id
			+ "].niveau1Id est null", niveau1Id,
			Niveau2WS.getNiveau1Id());
	    }
	}
    }
}
