package ch.ge.cti.ct.referentiels.communes.interfaces.ws;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.Collection;
import java.util.Date;

import org.databene.contiperf.PerfTest;
import org.databene.contiperf.Required;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import ch.ge.cti.ct.referentiels.communes.interfaces.ws.model.CantonWS;
import ch.ge.cti.ct.referentiels.ofs.ReferentielOfsException;

@RunWith(value = Parameterized.class)
@PerfTest(invocations = 1000, threads = 20)
public class RefCantonTest extends AbstractRefWSTest {

    private final String codeCanton;
    private final Date dateValid;
    private final Boolean exists;

    @Parameters
    public static Collection<Object[]> configure() {
	return Arrays.asList(new Object[][] {
	/* 0 */{ null, null, false },
	/* 1 */{ "", null, false },
	/* 2 */{ "XX", null, false },
	/* 3 */{ null, "01.01.1960", false },
	/* 4 */{ "", "01.01.1960", false },
	/* 5 */{ "XX", "01.01.1960", false },
	/* 6 */{ "BS", null, true },
	/* 7 */{ "BS", "01.01.1960", true },
	/* 8 */{ "BS", "01.01.2014", true },
	/* 9 */{ "GE", null, true },
	/* 10 */{ "GE", "01.01.1960", true },
	/* 11 */{ "GE", "01.01.2014", true },
	/* 12 */{ "JU", null, true },
	/* 13 */{ "JU", "01.01.1960", false },
	/* 15 */{ "JU", "01.01.1979", true },
	/* 16 */{ "JU", "01.01.2014", true },
	/* 17 */{ "AI", null, true },
	/* 18 */{ "AI", "01.01.1960", true },
	/* 19 */{ "AI", "01.01.1997", true },
	/* 20 */{ "AI", "01.01.2014", true },
	/* 21 */{ "ZH", null, true },
	/* 22 */{ "ZH", "01.01.1960", true },
	/* 23 */{ "ZH", "01.01.1990", true },
	/* 24 */{ "ZH", "01.01.2014", true }
	/* */
	});
    }

    public RefCantonTest(final String codeCanton, final String dateTest,
	    final Boolean exists) throws Exception {
	this.codeCanton = codeCanton;
	this.dateValid = date(dateTest);
	this.exists = exists;
    }

    @Test
    @Required(percentile90 = 15, percentile95 = 30)
    public void testGetCanton() throws ReferentielOfsException {
	CantonWS canton = null;
	if (dateValid == null) {
	    canton = getWS().getCanton(codeCanton);
	} else {
	    canton = getWS().getCantonDate(codeCanton, dateValid);
	}
	final String msgPrefix = "getCanton('" + codeCanton + "',"
		+ format(dateValid) + "): ";
	assertEquals(msgPrefix + " non trouvé", exists, canton != null);
	if (exists) {
	    assertEquals(msgPrefix + " code incorrect", codeCanton,
		    canton.getCode());
	    assertValidCanton(msgPrefix, canton, dateValid);
	}
    }
}
