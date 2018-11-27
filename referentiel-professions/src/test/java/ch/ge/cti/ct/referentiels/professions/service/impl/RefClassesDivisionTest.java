package ch.ge.cti.ct.referentiels.professions.service.impl;

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
import ch.ge.cti.ct.referentiels.professions.model.Classe;

@RunWith(value = Parameterized.class)
@PerfTest(invocations = 1000, threads = 20)
public class RefClassesDivisionTest extends AbstractRefTest {

    private final int divisionId;
    private final int countClasses;

    public RefClassesDivisionTest(final int divisionId, final int countClasses) {
	this.divisionId = divisionId;
	this.countClasses = countClasses;
    }

    @Parameters
    public static Collection<Object[]> configure() {
	return Arrays.asList(new Object[][] {
	/* 0 */{ -1, -1 },
	/* 1 */{ 0, -1 },
	/* 2 */{ 1, 1 },
	/* 3 */{ 2, 9 },
	/* 4 */{ 3, 6 },
	/* 5 */{ 4, 2 },
	/* 6 */{ 5, 4 },
	/* 7 */{ 6, 2 },
	/* 8 */{ 7, 5 },
	/* 9 */{ 8, 7 },
	/* 10 */{ 9, 3 },
	/* 11 */{ 99, 0 }
	/* */
	});
    }

    @Test
   
    public void test() throws ReferentielOfsException {
	final List<Classe> classes = ReferentielProfessionsService.INSTANCE
		.getClasses(divisionId);
	if (countClasses == -1) {
	    assertNull("Classes [" + divisionId + "] est incorrect", classes);
	} else {
	    assertNotNull("Classes [" + divisionId + "] est incorrect", classes);
	    assertEquals("Classes [" + divisionId + "].size est incorrect",
		    countClasses, classes.size());
	    for (final Classe classe : classes) {
		assertEquals(
			"Classes [" + divisionId + "].divisionId est null",
			divisionId, classe.getDivisionId());
	    }
	}
    }
}
