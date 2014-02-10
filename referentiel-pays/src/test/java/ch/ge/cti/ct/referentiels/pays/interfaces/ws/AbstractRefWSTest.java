package ch.ge.cti.ct.referentiels.pays.interfaces.ws;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.apache.commons.lang.StringUtils;
import org.databene.contiperf.junit.ContiPerfRule;
import org.junit.BeforeClass;
import org.junit.Rule;

import ch.ge.cti.ct.referentiels.ofs.ReferentielOfsException;
import ch.ge.cti.ct.referentiels.pays.AbstractReferentielTest;
import ch.ge.cti.ct.referentiels.pays.interfaces.ws.model.ContinentWS;
import ch.ge.cti.ct.referentiels.pays.interfaces.ws.model.PaysWS;
import ch.ge.cti.ct.referentiels.pays.interfaces.ws.model.RegionWS;

public class AbstractRefWSTest extends AbstractReferentielTest {

    @Rule
    public ContiPerfRule rule = new ContiPerfRule();

    @BeforeClass
    public static void load() throws ReferentielOfsException {
	// chargement du fichier SDMX
	new ReferentielPaysTerritoiresSEI().searchPays("fr");
    }

    private ReferentielPaysTerritoiresSEI ws = null;

    protected ReferentielPaysTerritoiresSEI getWS()
	    throws ReferentielOfsException {
	if (ws == null) {
	    ws = new ReferentielPaysTerritoiresSEI();
	}
	return ws;
    }

    protected void assertContinent(final ContinentWS continent) {
	assertTrue("Continent.id est incorrect", continent.getId() > 0);
	assertNotNull("Continent.nom est null", continent.getNom());
	assertTrue("Continent.nom est vide",
		StringUtils.isNotBlank(continent.getNom()));
    }

    protected void assertRegion(final RegionWS region, final int continentId) {
	assertTrue("Region.id est incorrect", region.getId() > 0);
	assertNotNull("Region.nom est null", region.getNom());
	assertTrue("Region.nom est vide",
		StringUtils.isNotBlank(region.getNom()));
	if (continentId > 0) {
	    assertEquals("Region.continentId est incorrect", continentId,
		    region.getContinentId());
	}
    }

    protected void assertPays(final PaysWS pays, final int continentId,
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
