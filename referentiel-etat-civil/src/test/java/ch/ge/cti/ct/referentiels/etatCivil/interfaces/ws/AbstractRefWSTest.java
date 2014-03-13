package ch.ge.cti.ct.referentiels.etatCivil.interfaces.ws;

import static org.junit.Assert.assertTrue;

import org.apache.commons.lang.StringUtils;
import org.databene.contiperf.junit.ContiPerfRule;
import org.junit.BeforeClass;
import org.junit.Rule;

import ch.ge.cti.ct.referentiels.etatCivil.AbstractReferentielTest;
import ch.ge.cti.ct.referentiels.etatCivil.model.EtatCivil;
import ch.ge.cti.ct.referentiels.ofs.ReferentielOfsException;

public class AbstractRefWSTest extends AbstractReferentielTest {

    @Rule
    public ContiPerfRule rule = new ContiPerfRule();

    @BeforeClass
    public static void load() throws ReferentielOfsException {
	new ReferentielEtatCivilSEI().getEtatsCivils();
    }

    private ReferentielEtatCivilSEI ws = null;

    protected ReferentielEtatCivilSEI getWS() throws ReferentielOfsException {
	if (ws == null) {
	    ws = new ReferentielEtatCivilSEI();
	    // on force le chargement pour ne pas impacter les mesures de perf
	    ws.getEtatsCivils();
	}
	return ws;
    }

    protected void assertEtatCivil(final EtatCivil etatCivil) {
	assertTrue("EtatCivil.id est incorrect", etatCivil.getId() > 0);
	assertTrue("EtatCivil.nom est incorrect",
		StringUtils.isNotBlank(etatCivil.getNom()));
    }

}
