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

import ch.ge.cti.ct.referentiels.communes.interfaces.ws.model.CantonWS;
import ch.ge.cti.ct.referentiels.ofs.ReferentielOfsException;

@RunWith(value = Parameterized.class)
@PerfTest(invocations = 1000, threads = 20)
public class RefCantonsTest extends AbstractRefWSTest {

    private final Date dateValid;
    private final int countCantons;
    private final Boolean existsJU;

    @Parameters
    public static Collection<Object[]> configure() {
	return Arrays.asList(new Object[][] {
	/* 0 */{ null, 26, true },
	/* 1 */{ "01.01.1960", 25, false },
	/* 2 */{ "01.01.1979", 26, true }
	/* */
	});
    }

    public RefCantonsTest(final String dateTest, final int countCantons,
	    final Boolean existsJU) throws Exception {
	this.dateValid = date(dateTest);
	this.countCantons = countCantons;
	this.existsJU = existsJU;
    }

    @Test
   
    public void testGetCantons() throws ReferentielOfsException {
	List<CantonWS> cantons = null;
	if (dateValid == null) {
	    cantons = getWS().getCantons();
	} else {
	    cantons = getWS().getCantonsDate(dateValid);
	}
	final String msgPrefix = "getCantons(" + format(dateValid) + "): ";
	assertNotNull(msgPrefix + " la liste des cantons est vide", cantons);
	assertEquals(msgPrefix + " le nombre de cantons est incorrect",
		countCantons, cantons.size());
	boolean foundJU = false;
	for (CantonWS c : cantons) {
	    foundJU |= c.getCode().equals("JU");
	}
	assertEquals(msgPrefix
		+ " la présence du canton [JU] dans la liste est incorrecte",
		existsJU, foundJU);

	assertValidCantons(msgPrefix, cantons, dateValid);
    }
}
