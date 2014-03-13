package ch.ge.cti.ct.referentiels.professions.service.impl;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.apache.commons.lang.StringUtils;
import org.databene.contiperf.PerfTest;
import org.databene.contiperf.Required;
import org.junit.Test;

import ch.ge.cti.ct.referentiels.ofs.ReferentielOfsException;
import ch.ge.cti.ct.referentiels.professions.model.Division;

@PerfTest(invocations = 1000, threads = 20)
public class RefTest extends AbstractRefTest {

    @Test
    @Required(percentile90 = 10, percentile95 = 30)
    public void test() throws ReferentielOfsException {
	assertTrue("ReferentielProfessions.id est incorrect",
		StringUtils.isNotBlank(ReferentielProfessionsService.instance
			.getReferentiel().getId()));
	assertNotNull("ReferentielProfessions.date est incorrect",
		ReferentielProfessionsService.instance.getReferentiel()
			.getDate());
	for (final Division division : ReferentielProfessionsService.instance
		.getReferentiel().getDivision()) {
	    assertDivision("Référentiel: ", division);
	}
    }
}
