package ch.ge.cti.ct.referentiels.communes.interfaces.ws;

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

import ch.ge.cti.ct.referentiels.communes.interfaces.ws.model.CommuneWS;
import ch.ge.cti.ct.referentiels.ofs.ReferentielOfsException;

@RunWith(value = Parameterized.class)
@PerfTest(invocations = 1000, threads = 20)
public class RefCommunesDistrictTest extends AbstractRefWSTest {

    private final String cantonCode;
    private final int idDistrict;
    private final Date dateValid;
    private final int countCommunes;

    @Parameters
    public static Collection<Object[]> configure() {
	return Arrays.asList(new Object[][] {
	/* 0 */{ null, -1, null, 0 },
	/* 1 */{ null, 0, null, 0 },
	/* 2 */{ null, 9999, null, 0 },
	/* 3 */{ null, -1, "01.01.1960", 0 },
	/* 4 */{ null, 0, "01.01.1960", 0 },
	/* 5 */{ null, 9999, "01.01.1960", 0 },
	/* 6 */{ "GE", 2500, null, 45 },
	/* 7 */{ "GE", 2500, "01.01.1960", 45 },
	/* 8 */{ "GE", 2500, "01.01.2014", 45 },
	/* 9 */{ "AI", 1600, null, 6 },
	/* 10 */{ "AI", 1600, "01.01.1960", 0 },
	/* 11 */{ "AI", 1600, "01.01.1997", 6 },
	/* 12 */{ "AI", 1600, "01.01.2014", 6 },
	/* 13 */{ "AI", 1601, null, 0 },
	/* 14 */{ "AI", 1601, "01.01.1960", 5 },
	/* 15 */{ "AI", 1601, "01.01.1997", 0 },
	/* 16 */{ "AI", 1601, "01.01.2014", 0 },
	/* 17 */{ "AI", 1602, null, 0 },
	/* 18 */{ "AI", 1602, "01.01.1960", 1 },
	/* 19 */{ "AI", 1602, "01.01.1997", 0 },
	/* 20 */{ "AI", 1602, "01.01.2014", 0 },
	/* 21 */{ "JU", 2601, null, 22 },
	/* 22 */{ "JU", 2601, "01.01.1960", 0 },
	/* 23 */{ "JU", 2601, "01.01.1979", 27 },
	/* 24 */{ "JU", 2601, "01.01.1984", 27 },
	/* 25 */{ "JU", 2601, "01.01.2013", 22 },
	/* 26 */{ "JU", 2601, "01.01.2014", 22 },
	/* 27 */{ "ZH", 101, null, 14 },
	/* 28 */{ "ZH", 101, "01.01.1960", 14 },
	/* 29 */{ "ZH", 101, "15.11.1976", 14 },
	/* 30 */{ "ZH", 101, "01.01.2014", 14 },
	/* 31 */{ "ZH", 102, "01.01.1960", 24 },
	/* 32 */{ "ZH", 102, "01.01.1970", 24 },
	/* 33 */{ "ZH", 102, "01.01.2013", 24 },
	/* 34 */{ "ZH", 102, "01.01.2014", 24 },
	/* 35 */{ "ZH", 111, null, 11 },
	/* 36 */{ "ZH", 111, "01.01.1960", 13 },
	/* 37 */{ "ZH", 111, "01.01.1990", 11 },
	/* 38 */{ "ZH", 111, "01.01.2003", 11 },
	/* 39 */{ "ZH", 111, "01.01.2014", 11 },
	/* 40 */{ "ZH", 112, null, 1 }
	/* */
	});
    }

    public RefCommunesDistrictTest(final String cantonCode,
	    final int idDistrict, final String dateTest, final int countCommunes)
	    throws Exception {
	this.cantonCode = cantonCode;
	this.idDistrict = idDistrict;
	this.dateValid = date(dateTest);
	this.countCommunes = countCommunes;
    }

    @Test
    @Required(percentile90 = 15, percentile95 = 30)
    public void test() throws ReferentielOfsException {
	List<CommuneWS> communes = null;
	if (dateValid == null) {
	    communes = getWS().getCommunesByDistrict(idDistrict);
	} else {
	    communes = getWS().getCommunesByDistrictDate(idDistrict, dateValid);
	}
	final String msgPrefix = "getCommunesByDistrict('" + idDistrict + "', "
		+ format(dateValid) + "): ";
	assertNotNull(msgPrefix + " la liste des communes est vide", communes);

	assertValidCommunes(msgPrefix, communes, dateValid, cantonCode, -1,
		countCommunes);
    }
}
