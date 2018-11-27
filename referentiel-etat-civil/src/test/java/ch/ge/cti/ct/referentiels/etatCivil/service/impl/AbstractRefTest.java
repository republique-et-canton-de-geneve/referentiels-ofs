package ch.ge.cti.ct.referentiels.etatCivil.service.impl;

import static org.junit.Assert.assertTrue;

import org.apache.commons.lang.StringUtils;
import org.databene.contiperf.junit.ContiPerfRule;
import org.junit.BeforeClass;
import org.junit.Rule;

import ch.ge.cti.ct.referentiels.etatCivil.AbstractReferentielTest;
import ch.ge.cti.ct.referentiels.etatCivil.model.EtatCivil;
import ch.ge.cti.ct.referentiels.ofs.ReferentielOfsException;

public abstract class AbstractRefTest extends AbstractReferentielTest {

    @Rule
    public ContiPerfRule rule = new ContiPerfRule();

    @BeforeClass
    public static void load() throws ReferentielOfsException {
	ReferentielEtatCivilService.INSTANCE.getReferentiel();
    }

    protected void assertEtatCivil(final EtatCivil etatCivil) {
	assertTrue("EtatCivil.id est incorrect", etatCivil.getId() > 0);
	assertTrue("EtatCivil.nom est incorrect",
		StringUtils.isNotBlank(etatCivil.getNom()));
    }

}
