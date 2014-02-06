package ch.ge.cti.ct.referentiels.communes.service.impl;

import static org.junit.Assert.assertNotNull;

import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import ch.ge.cti.ct.referentiels.communes.model.Commune;
import ch.ge.cti.ct.referentiels.communes.model.District;
import ch.ge.cti.ct.referentiels.ofs.ReferentielOfsException;

@RunWith(value = Parameterized.class)
public class RefCommunesCantonTest extends AbstractRefTest {

    private final String cantonCode;
    private final Date dateValid;
    private final int countCommunes;

    @Parameters
    public static Collection<Object[]> configure() {
	return Arrays.asList(new Object[][] {
	/* 0 */{ null, null, 0 },
	/* 1 */{ null, "01.01.1960", 0 },
	/* 2 */{ null, "01.01.2014", 0 },
	/* 3 */{ "", null, 0 },
	/* 4 */{ "", "01.01.1960", 0 },
	/* 5 */{ "", "01.01.2014", 0 },
	/* 6 */{ "XX", null, 0 },
	/* 7 */{ "XX", "01.01.1960", 0 },
	/* 8 */{ "XX", "01.01.2014", 0 },
	/* 9 */{ "GE", null, 45 },
	/* 10 */{ "GE", "01.01.1960", 45 },
	/* 11 */{ "GE", "01.01.2014", 45 },
	/* 12 */{ "AI", null, 6 },
	/* 13 */{ "AI", "01.01.1960", 6 },
	/* 14 */{ "AI", "31.12.1996", 6 },
	/* 15 */{ "AI", "01.01.1997", 6 },
	/* 16 */{ "AI", "01.01.2014", 6 },
	/* 17 */{ "JU", null, 57 },
	/* 18 */{ "JU", "01.01.1960", 0 },
	/* 19 */{ "JU", "01.01.1979", 82 },
	/* 20 */{ "JU", "01.01.1984", 82 },
	/* 21 */{ "JU", "01.01.2009", 64 },
	/* 22 */{ "JU", "01.01.2013", 57 },
	/* 23 */{ "JU", "01.01.2014", 57 },
	/* 24 */{ "ZH", null, 170 },
	/* 25 */{ "ZH", "01.01.1970", 171 },
	/* 26 */{ "ZH", "16.03.1974", 171 },
	/* 27 */{ "ZH", "01.01.1976", 171 },
	/* 28 */{ "ZH", "15.11.1976", 171 },
	/* 29 */{ "ZH", "10.03.1977", 171 },
	/* 30 */{ "ZH", "01.01.1986", 171 },
	/* 31 */{ "ZH", "01.01.1990", 171 },
	/* 32 */{ "ZH", "01.01.1999", 171 },
	/* 33 */{ "ZH", "01.01.2003", 171 },
	/* 34 */{ "ZH", "01.01.2014", 170 }
	/* */
	});
    }

    public RefCommunesCantonTest(final String cantonCode,
	    final String dateTest, final int countCommunes) throws Exception {
	this.cantonCode = cantonCode;
	this.dateValid = date(dateTest);
	this.countCommunes = countCommunes;
    }

    @Test
    public void testGetCommunes() throws ReferentielOfsException {
	List<Commune> communes = null;
	if (dateValid == null) {
	    communes = ReferentielCommunesService.instance
		    .getCommunesByCanton(cantonCode);
	} else {
	    communes = ReferentielCommunesService.instance.getCommunesByCanton(
		    cantonCode, dateValid);
	}
	final String msgPrefix = "getCommunesByCanton('" + cantonCode + "', "
		+ format(dateValid) + "): ";
	assertNotNull(msgPrefix + " la liste des communes est vide", communes);
	assertValidCommunes(msgPrefix, communes, dateValid, cantonCode, -1,
		countCommunes);
    }

    // @Test
    public void dump() throws ReferentielOfsException {
	final String KT = "ZH";
	if (dateValid == null) {
	    System.out
		    .println("----------------------------------------------------------------------------------------------------------");
	    for (District d : ReferentielCommunesService.instance.getCanton(KT)
		    .getDistrict()) {
		for (Commune c : d.getCommune()) {
		    System.out.println(c.getId() + "\t" + KT + "\t"
			    + format(c.getValidFrom()) + "\t"
			    + format(c.getValidTo()));
		}
	    }
	    System.out
		    .println("----------------------------------------------------------------------------------------------------------");
	}
    }
}
