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

import ch.ge.cti.ct.referentiels.communes.interfaces.ws.model.DistrictWS;
import ch.ge.cti.ct.referentiels.ofs.ReferentielOfsException;

@RunWith(value = Parameterized.class)
@PerfTest(invocations = 1000, threads = 20)
public class RefDistrictTest extends AbstractRefWSTest {

    private final int idDistrict;
    private final Date dateValid;
    private final String codeCanton;
    private final boolean exists;

    @Parameters
    public static Collection<Object[]> configure() {
	return Arrays.asList(new Object[][] {
	/* 0 */{ -1, null, false, null },
	/* 1 */{ 0, null, false, null },
	/* 2 */{ 9999, null, false, null },
	/* 3 */{ -1, "01.01.1960", false, null },
	/* 4 */{ 0, "01.01.1960", false, null },
	/* 5 */{ 9999, "01.01.1960", false, null },
	/* 6 */{ 2500, null, true, "GE" },
	/* 7 */{ 2500, "01.01.1960", true, "GE" },
	/* 8 */{ 2500, "01.01.2014", true, "GE" },
	/* 9 */{ 1600, null, true, "AI" },
	/* 10 */{ 1600, "01.01.1960", false, null },
	/* 11 */{ 1600, "01.01.1997", true, "AI" },
	/* 12 */{ 1600, "01.01.2014", true, "AI" },
	/* 13 */{ 1601, null, false, null },
	/* 14 */{ 1601, "01.01.1960", true, "AI" },
	/* 15 */{ 1601, "01.01.1997", false, null },
	/* 16 */{ 1601, "01.01.2014", false, null },
	/* 17 */{ 1602, null, false, null },
	/* 18 */{ 1602, "01.01.1960", true, "AI" },
	/* 19 */{ 1602, "01.01.1997", false, null },
	/* 20 */{ 1602, "01.01.2014", false, null },
	/* 21 */{ 2601, null, true, "JU" },
	/* 22 */{ 2601, "01.01.1960", false, null },
	/* 23 */{ 2601, "01.01.1979", true, "JU" },
	/* 24 */{ 2601, "01.01.2014", true, "JU" },
	/* 25 */{ 101, null, true, "ZH" },
	/* 26 */{ 101, "01.01.1960", true, "ZH" },
	/* 27 */{ 101, "01.01.2014", true, "ZH" },
	/* 28 */{ 102, "01.01.1960", true, "ZH" },
	/* 29 */{ 102, "01.01.2014", true, "ZH" },
	/* 30 */{ 103, "01.01.1960", true, "ZH" },
	/* 31 */{ 103, "01.01.2014", true, "ZH" },
	/* 32 */{ 104, "01.01.1960", true, "ZH" },
	/* 33 */{ 104, "01.01.2014", true, "ZH" },
	/* 34 */{ 105, "01.01.1960", true, "ZH" },
	/* 35 */{ 105, "01.01.2014", true, "ZH" },
	/* 36 */{ 106, "01.01.1960", true, "ZH" },
	/* 37 */{ 106, "01.01.2014", true, "ZH" },
	/* 38 */{ 107, "01.01.1960", true, "ZH" },
	/* 39 */{ 107, "01.01.2014", true, "ZH" },
	/* 40 */{ 108, "01.01.1960", true, "ZH" },
	/* 41 */{ 108, "01.01.2014", true, "ZH" },
	/* 42 */{ 109, "01.01.1960", true, "ZH" },
	/* 43 */{ 109, "01.01.2014", true, "ZH" },
	/* 44 */{ 110, "01.01.1960", true, "ZH" },
	/* 45 */{ 110, "01.01.2014", true, "ZH" },
	/* 46 */{ 111, null, true, "ZH" },
	/* 47 */{ 111, "01.01.1960", true, "ZH" },
	/* 48 */{ 111, "01.01.1990", true, "ZH" },
	/* 49 */{ 111, "01.01.2014", true, "ZH" },
	/* 50 */{ 112, null, true, "ZH" }
	/* */
	});
    }

    public RefDistrictTest(final int idDistrict, final String dateTest,
	    final boolean exists, final String codeCanton) throws Exception {
	this.idDistrict = idDistrict;
	this.dateValid = date(dateTest);
	this.codeCanton = codeCanton;
	this.exists = exists;
    }

    @Test
    @Required(percentile90 = 30, percentile95 = 150)
    public void test() throws ReferentielOfsException {
	DistrictWS district = null;
	if (dateValid == null) {
	    district = getWS().getDistrict(idDistrict);
	} else {
	    district = getWS().getDistrictDate(idDistrict, dateValid);
	}
	final String msgPrefix = "getDistrict('" + idDistrict + "',"
		+ format(dateValid) + "): ";
	assertEquals(msgPrefix + " non trouvé", exists, district != null);
	if (exists) {
	    assertEquals(msgPrefix + " code incorrect", idDistrict,
		    district.getId());
	    assertValidDistrict(msgPrefix, district, dateValid, codeCanton);
	}
    }
}
