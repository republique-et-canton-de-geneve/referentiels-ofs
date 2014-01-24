package ch.ge.cti.ct.referentiels.pays.service.impl;

import ch.ge.cti.ct.referentiels.pays.AbstractReferentielPaysTerritoiresServiceAbleTest;
import ch.ge.cti.ct.referentiels.pays.service.ReferentielPaysTerritoiresServiceAble;

public class ReferentielPaysTerritoiresServiceTest extends
		AbstractReferentielPaysTerritoiresServiceAbleTest {

	@Override
	protected ReferentielPaysTerritoiresServiceAble getService() {
		return ReferentielPaysTerritoiresService.instance;
	}
}
