package ch.ge.cti.ct.referentiels.formeJuridique.service.impl;

import static org.junit.Assert.assertTrue;

import org.apache.commons.lang.StringUtils;
import org.databene.contiperf.junit.ContiPerfRule;
import org.junit.BeforeClass;
import org.junit.Rule;

import ch.ge.cti.ct.referentiels.formeJuridique.AbstractReferentielTest;
import ch.ge.cti.ct.referentiels.formeJuridique.model.FormeJuridique;
import ch.ge.cti.ct.referentiels.ofs.ReferentielOfsException;

public abstract class AbstractRefTest extends AbstractReferentielTest {

    @Rule
    public ContiPerfRule rule = new ContiPerfRule();

    @BeforeClass
    public static void load() throws ReferentielOfsException {
	ReferentielFormesJuridiquesService.INSTANCE.getReferentiel();
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
