package ch.ge.cti.ct.referentiels.communes.interfaces.ws;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.databene.contiperf.PerfTest;
import org.databene.contiperf.Required;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import ch.ge.cti.ct.referentiels.communes.interfaces.ws.model.DistrictWS;
import ch.ge.cti.ct.referentiels.ofs.ReferentielOfsException;

@RunWith(value = Parameterized.class)
@PerfTest(invocations = 1000, threads = 20)
public class RefDistrictsTest extends AbstractRefWSTest {

    private final String cantonCode;
    private final Date dateValid;
    private final int countDistricts;

    @Parameters
    public static Collection<Object[]> configure() {
	return Arrays.asList(new Object[][] {
	/* 0 */{ null, null, 0 },
	/* 1 */{ "", null, 0 },
	/* 2 */{ "XX", null, 0 },
	/* 3 */{ null, "01.01.2014", 0 },
	/* 4 */{ "", "01.01.2014", 0 },
	/* 5 */{ "XX", "01.01.2014", 0 },
	/* 6 */{ "GE", null, 1 },
	/* 7 */{ "GE", "01.01.1960", 1 },
	/* 8 */{ "GE", "01.01.2014", 1 },
	/* 9 */{ "AI", null, 1 },
	/* 10 */{ "AI", "01.01.1960", 2 },
	/* 11 */{ "AI", "01.01.1997", 1 },
	/* 12 */{ "AI", "01.01.2014", 1 },
	/* 13 */{ "ZH", null, 12 },
	/* 14 */{ "ZH", "01.01.1960", 11 },
	/* 15 */{ "ZH", "01.01.1990", 12 },
	/* 16 */{ "ZH", "01.01.2014", 12 },
	/* 17 */{ "JU", null, 3 },
	/* 18 */{ "JU", "01.01.1960", 0 },
	/* 19 */{ "JU", "01.01.1979", 3 },
	/* 20 */{ "JU", "01.01.2014", 3 } });
    }

    public RefDistrictsTest(final String cantonCode, final String dateTest,
	    final int countDistricts) throws Exception {
	this.cantonCode = cantonCode;
	this.dateValid = date(dateTest);
	this.countDistricts = countDistricts;
    }

    @Test
    @Required(percentile90 = 10, percentile95 = 20)
    public void test() throws ReferentielOfsException {
	List<DistrictWS> districts = null;
	if (dateValid == null) {
	    districts = getWS().getDistricts(cantonCode);
	} else {
	    districts = getWS().getDistrictsByCantonDate(cantonCode, dateValid);
	}
	final String msgPrefix = "getDistricts('" + cantonCode + "', "
		+ format(dateValid) + "): ";
	assertNotNull(msgPrefix + " la liste des districts est vide", districts);
	assertEquals(msgPrefix + "le nombre de cantons est incorrect",
		countDistricts, districts.size());

	assertValidDistricts(msgPrefix, districts, dateValid, cantonCode);
    }
}
