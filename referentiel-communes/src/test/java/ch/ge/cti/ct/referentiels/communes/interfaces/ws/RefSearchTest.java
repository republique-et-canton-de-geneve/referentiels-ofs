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
@PerfTest(invocations = 200, threads = 5)
public class RefSearchTest extends AbstractRefWSTest {

    private final String search;
    private final Date dateValid;
    private final int countCommunes;

    @Parameters
    public static Collection<Object[]> configure() {
	return Arrays.asList(new Object[][] {
	/* 0 */{ null, null, 0 },
	/* 1 */{ "", null, 0 },
	/* 2 */{ null, "01.01.1960", 0 },
	/* 3 */{ "", "01.01.1960", 0 },
	/* 4 */{ "Brüttelen", null, 1 },
	/* 5 */{ "Brüttelen", "01.01.1960", 1 },
	/* 6 */{ "Brüttelen", "01.01.1979", 1 },
	/* 7 */{ "Brüttelen", "01.01.2014", 1 },
	/* 8 */{ "brüttelen", null, 1 },
	/* 9 */{ "bruttelen", null, 1 },
	/* 10 */{ "BRUTTELEN", null, 1 },
	/* 11 */{ "Affoltern im Emmental", "01.01.1960", 1 },
	/* 12 */{ "Affoltern im Emmental", "01.01.2014", 1 },
	/* 13 */{ "Weiningen", "01.01.1990", 2 },
	/* 14 */{ "Aar", null, 4 },
	/* 15 */{ "Aar", "01.01.1960", 4 },
	/* 16 */{ "AAR", null, 4 },
	/* 17 */{ "Aesch bei Birmensdorf", "01.01.1960", 1 },
	/* 18 */{ "Aesch bei Birmensdorf", "01.01.1990", 1 },
	/* 19 */{ "Aesch bei Birmensdorf", "01.01.2003", 0 },
	/* 20 */{ "Zénauva", "01.01.1960", 1 },
	/* 21 */{ "Zénauva", "01.01.2003", 0 },
	/* 22 */{ "Zénauva", null, 0 },
	/* 23 */{ "Zenauva", "01.01.1960", 1 },
	/* 24 */{ "Zenauva", "01.01.2003", 0 },
	/* 25 */{ "Zenauva", null, 0 },
	/* 26 */{ "ZeNauvA", "01.01.1960", 1 },
	/* 27 */{ "ZeNauvA", "01.01.2003", 0 },
	/* 28 */{ "ZeNauvA", null, 0 },
	/* 29 */{ "Aesch", null, 5 },
	/* 30 */{ "Aesch ", null, 3 },
	/* 31 */{ "Aesch (", null, 3 },
	/* 32 */{ "Aesch (", "01.01.1960", 2 },
	/* */
	});
    }

    public RefSearchTest(final String search, final String dateTest,
	    final int countCommunes) throws Exception {
	this.search = search;
	this.dateValid = date(dateTest);
	this.countCommunes = countCommunes;
    }

    @Test
    @Required(average = 50, percentile90 = 100, percentile95 = 200)
    public void test() throws ReferentielOfsException {
	List<CommuneWS> communes = null;
	if (dateValid == null) {
	    communes = getWS().searchCommune(search);
	} else {
	    communes = getWS().searchCommuneDate(search, dateValid);
	}
	final String msgPrefix = "searchCommune('" + search + "', "
		+ format(dateValid) + "): ";
	assertNotNull(msgPrefix + " la liste des communes est vide", communes);
	assertValidCommunes(msgPrefix, communes, dateValid, null, -1,
		countCommunes);
    }
}
