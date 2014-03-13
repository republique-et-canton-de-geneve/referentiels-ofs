package ch.ge.cti.ct.referentiels.formeJuridique.interfaces.ws;

import static org.junit.Assert.assertTrue;

import org.apache.commons.lang.StringUtils;
import org.databene.contiperf.junit.ContiPerfRule;
import org.junit.BeforeClass;
import org.junit.Rule;

import ch.ge.cti.ct.referentiels.formeJuridique.AbstractReferentielTest;
import ch.ge.cti.ct.referentiels.formeJuridique.model.FormeJuridique;
import ch.ge.cti.ct.referentiels.ofs.ReferentielOfsException;

public class AbstractRefWSTest extends AbstractReferentielTest {

    @Rule
    public ContiPerfRule rule = new ContiPerfRule();

    @BeforeClass
    public static void load() throws ReferentielOfsException {
	new ReferentielFormesJuridiquesSEI().getFormesJuridiques();
    }

    private ReferentielFormesJuridiquesSEI ws = null;

    protected ReferentielFormesJuridiquesSEI getWS()
	    throws ReferentielOfsException {
	if (ws == null) {
	    ws = new ReferentielFormesJuridiquesSEI();
	    // on force le chargement pour ne pas impacter les mesures de perf
	    ws.getFormesJuridiques();
	}
	return ws;
    }

    protected void assertFormeJuridique(final FormeJuridique formeJuridique) {
	assertTrue("FormeJuridique.id est incorrect",
		formeJuridique.getId() > 0);
	assertTrue("FormeJuridique.nom est incorrect",
		StringUtils.isNotBlank(formeJuridique.getNom()));
	assertTrue("FormeJuridique.nomCourt est incorrect",
		StringUtils.isNotBlank(formeJuridique.getNomCourt()));
    }

}
