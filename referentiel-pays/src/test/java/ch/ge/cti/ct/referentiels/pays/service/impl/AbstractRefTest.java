package ch.ge.cti.ct.referentiels.pays.service.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.apache.commons.lang.StringUtils;
import org.databene.contiperf.junit.ContiPerfRule;
import org.junit.BeforeClass;
import org.junit.Rule;

import ch.ge.cti.ct.referentiels.ofs.ReferentielOfsException;
import ch.ge.cti.ct.referentiels.pays.AbstractReferentielTest;
import ch.ge.cti.ct.referentiels.pays.model.Continent;
import ch.ge.cti.ct.referentiels.pays.model.Pays;
import ch.ge.cti.ct.referentiels.pays.model.Region;

public class AbstractRefTest extends AbstractReferentielTest {

    @Rule
    public ContiPerfRule rule = new ContiPerfRule();

    @BeforeClass
    public static void load() throws ReferentielOfsException {
	ReferentielPaysTerritoiresService.instance.getReferentiel();
    }

    protected void assertContinent(final Continent continent) {
	assertTrue("Continent.id est incorrect", continent.getId() > 0);
	assertNotNull("Continent.nom est null", continent.getNom());
	assertTrue("Continent.nom est vide",
		StringUtils.isNotBlank(continent.getNom()));
	assertNotNull("Continent.pays est null", continent.getRegion());
	assertTrue("Continent.pays est vide", continent.getRegion().size() > 0);
	for (Region region : continent.getRegion()) {
	    assertRegion(region, continent.getId());
	}
    }

    protected void assertRegion(final Region region, final int continentId) {
	assertTrue("Region.id est incorrect", region.getId() > 0);
	assertNotNull("Region.nom est null", region.getNom());
	assertTrue("Region.nom est vide",
		StringUtils.isNotBlank(region.getNom()));
	if (continentId > 0) {
	    assertEquals("Region.continentId est incorrect", continentId,
		    region.getContinentId());
	}
	assertNotNull("Region.pays est null", region.getPays());
	assertTrue("Region.pays est vide", region.getPays().size() > 0);
	for (Pays pays : region.getPays()) {
	    assertPays(pays, continentId, region.getId());
	}
    }

    protected void assertPays(final Pays pays, final int continentId,
	    final int regionId) {
	assertTrue("Pays.id est incorrect", pays.getId() > 0);
	assertNotNull("Pays.nom est null", pays.getNom());
	assertTrue("Pays.nom est vide", StringUtils.isNotBlank(pays.getNom()));
	assertNotNull("Pays.nomLong est null", pays.getNomLong());
	assertTrue("Pays.nomLong est vide",
		StringUtils.isNotBlank(pays.getNomLong()));
	if (continentId > 0) {
	    assertEquals("Pays.continentId est incorrect", continentId,
		    pays.getContinentId());
	}
	if (regionId > 0) {
	    assertEquals("Pays.regionId est incorrect", regionId,
		    pays.getRegionId());
	}
    }
}