package ch.ge.cti.ct.referentiels.socioprofessionnel.service.impl;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.apache.commons.lang.StringUtils;
import org.databene.contiperf.PerfTest;
import org.databene.contiperf.Required;
import org.junit.Test;

import ch.ge.cti.ct.referentiels.ofs.ReferentielOfsException;
import ch.ge.cti.ct.referentiels.socioprofessionnel.model.Niveau1;

@PerfTest(invocations = 1000, threads = 20)
public class RefTest extends AbstractRefTest {

    @Test
   
    public void test() throws ReferentielOfsException {
	assertTrue(
		"ReferentielSocioprofessionnel.id est incorrect",
		StringUtils
			.isNotBlank(ReferentielSocioprofessionnelService.instance
				.getReferentiel().getId()));
	assertNotNull("ReferentielSocioprofessionnel.date est incorrect",
		ReferentielSocioprofessionnelService.instance.getReferentiel()
			.getDate());
	for (final Niveau1 niveau1 : ReferentielSocioprofessionnelService.instance
		.getReferentiel().getNiveau1()) {
	    assertNiveau1("Référentiel: ", niveau1);
	}
    }
}
