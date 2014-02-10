package ch.ge.cti.ct.referentiels.pays.service.impl;

import static org.junit.Assert.assertEquals;

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
import ch.ge.cti.ct.referentiels.pays.model.Pays;

@RunWith(value = Parameterized.class)
@PerfTest(invocations = 1000, threads = 20)
public class RefPaysContinentTest extends AbstractRefTest {

    private final int continentId;
    private final int countPays;

    @Parameters
    public static Collection<Object[]> configure() {
	return Arrays.asList(new Object[][] {
	/* 0 */{ 0, 0 },
	/* 1 */{ 1, 64 },
	/* 2 */{ 2, 64 },
	/* 3 */{ 3, 56 },
	/* 4 */{ 4, 53 },
	/* 5 */{ 5, 32 },
	/* 6 */{ 6, 3 },
	/* 7 */{ 7, 0 },
	/* */
	});
    }

    public RefPaysContinentTest(final int continentId, final int countPays)
	    throws Exception {
	this.continentId = continentId;
	this.countPays = countPays;
    }

    @Test
    @Required(percentile90 = 1, percentile95 = 1)
    public void test() throws ReferentielOfsException {
	final List<Pays> payss = ReferentielPaysTerritoiresService.instance
		.getPaysByContinent(continentId);

	assertEquals("Liste des pays du continent [" + continentId
		+ "] est incorrecte", countPays, payss.size());
	for (Pays pays : payss) {
	    assertPays(pays, continentId, -1);
	}
    }
}
