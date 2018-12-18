/*
 * Referentiels OFS
 *
 * Copyright (C) 2018 République et canton de Genève
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
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

public abstract class AbstractRefWSTest extends AbstractReferentielTest {

    @Rule
    public ContiPerfRule rule = new ContiPerfRule();

    @BeforeClass
    public static void load() throws ReferentielOfsException {
	// chargement du fichier SDMX
	new ReferentielPaysTerritoiresSEI().searchPays("fr");
    }

    private ReferentielPaysTerritoiresWS ws = null;

    protected ReferentielPaysTerritoiresWS getWS()
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
