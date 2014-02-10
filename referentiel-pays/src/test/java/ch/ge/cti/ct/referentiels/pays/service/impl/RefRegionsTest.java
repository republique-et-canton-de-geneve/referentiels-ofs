package ch.ge.cti.ct.referentiels.pays.service.impl;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Test;

import ch.ge.cti.ct.referentiels.ofs.ReferentielOfsException;
import ch.ge.cti.ct.referentiels.pays.model.Region;

public class RefRegionsTest extends AbstractRefTest {

    @Test
    public void test() throws ReferentielOfsException {
	final List<Region> regions = ReferentielPaysTerritoiresService.instance
		.getRegions();
	assertEquals("Ls liste des regions est incorrecte", 32, regions.size());
	for (Region region : regions) {
	    assertRegion(region, -1);
	}
    }

}
