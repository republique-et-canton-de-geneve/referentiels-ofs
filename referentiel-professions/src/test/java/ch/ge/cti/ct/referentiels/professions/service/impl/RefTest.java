package ch.ge.cti.ct.referentiels.professions.service.impl;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.apache.commons.lang.StringUtils;
import org.databene.contiperf.PerfTest;
import org.junit.Test;

import ch.ge.cti.ct.referentiels.ofs.ReferentielOfsException;
import ch.ge.cti.ct.referentiels.professions.model.Division;

@PerfTest(invocations = 1000, threads = 20)
public class RefTest extends AbstractRefTest {

    @Test
   
    public void test() throws ReferentielOfsException {
	assertTrue("ReferentielProfessions.id est incorrect",
		StringUtils.isNotBlank(ReferentielProfessionsService.INSTANCE
			.getReferentiel().getId()));
	assertNotNull("ReferentielProfessions.date est incorrect",
		ReferentielProfessionsService.INSTANCE.getReferentiel()
			.getDate());
	for (final Division division : ReferentielProfessionsService.INSTANCE
		.getReferentiel().getDivision()) {
	    assertDivision("Référentiel: ", division);
	}
    }
}
