package ch.ge.cti.ct.referentiels.formeJuridique.service.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.util.Arrays;
import java.util.Collection;

import org.databene.contiperf.PerfTest;
import org.databene.contiperf.Required;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import ch.ge.cti.ct.referentiels.formeJuridique.model.FormeJuridique;
import ch.ge.cti.ct.referentiels.ofs.ReferentielOfsException;

@RunWith(value = Parameterized.class)
@PerfTest(invocations = 1000, threads = 20)
public class RefFormeJuridiqueTest extends AbstractRefTest {

    private final int formeJuridiqueId;
    private final String formeJuridiqueNom;
    private final String formeJuridiqueNomCourt;

    @Parameters
    public static Collection<Object[]> configure() {
	return Arrays.asList(new Object[][] {
	/* 0 */{ -1, null, null },
	/* 1 */{ 0, null, null },
	/*  */{ 1, "Raison individuelle", "Raison individuelle" },
	/*  */{ 2, "Société simple", "Société simple" },
	/*  */{ 3, "Société en nom collectif", "Soc. nom collectif" },
	/*  */{ 4, "Société en commandite", "Soc. en commandite" },
	/*  */{ 5, "Société en commandite par action", "Soc. comm. action" },
	/*  */{ 6, "Société anonyme", "SA" },
	/*  */{ 7, "Société à responsabilité limitée (SARL)", "SARL" },
	/*  */{ 40, null, null },
	/* */
	});
    }

    public RefFormeJuridiqueTest(final int formeJuridiqueId,
	    final String formeJuridiqueNom, final String formeJuridiqueNomCourt)
	    throws Exception {
	this.formeJuridiqueId = formeJuridiqueId;
	this.formeJuridiqueNom = formeJuridiqueNom;
	this.formeJuridiqueNomCourt = formeJuridiqueNomCourt;
    }

    @Test
    @Required(percentile90 = 1, percentile95 = 1)
    public void test() throws ReferentielOfsException {
	final FormeJuridique formeJuridique = ReferentielFormesJuridiquesService.instance
		.getFormeJuridique(formeJuridiqueId);
	if (formeJuridiqueNom == null) {
	    assertNull("FormeJuridique [" + formeJuridiqueId
		    + "].id est incorrect", formeJuridique);
	} else {
	    assertEquals("FormeJuridique [" + formeJuridiqueId
		    + "].id est incorrect", formeJuridiqueId,
		    formeJuridique.getId());
	    assertEquals("FormeJuridique [" + formeJuridiqueId
		    + "].nom est incorrect", formeJuridiqueNom,
		    formeJuridique.getNom());
	    assertEquals("FormeJuridique [" + formeJuridiqueId
		    + "].nomCourt est incorrect", formeJuridiqueNomCourt,
		    formeJuridique.getNomCourt());
	    assertFormeJuridique(formeJuridique);
	}
    }
}
