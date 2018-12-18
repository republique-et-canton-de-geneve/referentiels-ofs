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
package ch.ge.cti.ct.referentiels.communes.interfaces.ws;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.Date;
import java.util.List;

import org.databene.contiperf.junit.ContiPerfRule;
import org.junit.BeforeClass;
import org.junit.Rule;

import ch.ge.cti.ct.referentiels.communes.AbstractReferentielTest;
import ch.ge.cti.ct.referentiels.communes.interfaces.ws.model.CantonWS;
import ch.ge.cti.ct.referentiels.communes.interfaces.ws.model.CommuneWS;
import ch.ge.cti.ct.referentiels.communes.interfaces.ws.model.DistrictWS;
import ch.ge.cti.ct.referentiels.ofs.ReferentielOfsException;

public abstract class AbstractRefWSTest extends AbstractReferentielTest {

    @Rule
    public ContiPerfRule rule = new ContiPerfRule();

    @BeforeClass
    public static void load() throws ReferentielOfsException {
	new ReferentielCommunesSEI().getCommune(1);
    }

    private ReferentielCommunesSEI ws = null;

    protected ReferentielCommunesSEI getWS() throws ReferentielOfsException {
	if (ws == null) {
	    ws = new ReferentielCommunesSEI();
	}
	return ws;
    }

    protected void assertValidCommunes(final String message,
	    final List<CommuneWS> communes, final Date dateValid,
	    final String cantonCode, final int idDistrict,
	    final int countCommunes) {
	final Date valid = dateValid == null ? new Date() : dateValid;
	assertEquals(message + "le nombre de communes est incorrect",
		countCommunes, communes.size());
	for (final CommuneWS c : communes) {
	    assertTrue(
		    message
			    + "commune["
			    + c.getId()
			    + "].validFrom="
			    + (c.getValidFrom() == null ? ""
				    : format(c.getValidFrom())) + " / "
			    + format(valid), c.getValidFrom() == null
			    || c.getValidFrom().getTime() <= valid.getTime());
	    assertTrue(
		    message
			    + "commune["
			    + c.getId()
			    + "].validTo="
			    + (c.getValidTo() == null ? ""
				    : format(c.getValidTo())) + " / "
			    + format(valid), c.getValidTo() == null
			    || c.getValidTo().getTime() >= valid.getTime());

	    if (cantonCode != null) {
		assertEquals(message + " codeCanton est incorrect", cantonCode,
			c.getCodeCanton());
	    } else {
		assertNotNull(message + " codeCanton est incorrect",
			c.getCodeCanton());
	    }
	    if (idDistrict > 0) {
		assertEquals(message + " idDistrict est incorrect", idDistrict,
			c.getIdDistrict());

	    } else {
		assertTrue(message + " idDistrict est incorrect",
			c.getIdDistrict() > 0);
	    }
	}
    }

    protected void assertValidDistricts(final String message,
	    final List<DistrictWS> districts, final Date dateValid,
	    final String codeCanton) {
	final Date valid = dateValid == null ? new Date() : dateValid;
	for (final DistrictWS c : districts) {
	    assertTrue(
		    message
			    + "district["
			    + c.getId()
			    + "].validFrom="
			    + (c.getValidFrom() == null ? ""
				    : format(c.getValidFrom())) + " / "
			    + format(valid), c.getValidFrom() == null
			    || c.getValidFrom().getTime() <= valid.getTime());
	    assertTrue(
		    message
			    + "district["
			    + c.getId()
			    + "].validTo="
			    + (c.getValidTo() == null ? ""
				    : format(c.getValidTo())) + " / "
			    + format(valid), c.getValidTo() == null
			    || c.getValidTo().getTime() >= valid.getTime());

	    assertEquals(message + " code canton invalide", codeCanton,
		    c.getCodeCanton());
	}
    }

    protected void assertValidDistrict(final String message,
	    final DistrictWS district, final Date dateValid,
	    final String codeCanton) {
	final Date valid = dateValid == null ? new Date() : dateValid;
	assertTrue(
		message
			+ "district["
			+ district.getId()
			+ "].validFrom="
			+ (district.getValidFrom() == null ? ""
				: format(district.getValidFrom())) + " / "
			+ format(valid), district.getValidFrom() == null
			|| district.getValidFrom().getTime() <= valid.getTime());
	assertTrue(
		message
			+ "district["
			+ district.getId()
			+ "].validTo="
			+ (district.getValidTo() == null ? ""
				: format(district.getValidTo())) + " / "
			+ format(valid), district.getValidTo() == null
			|| district.getValidTo().getTime() >= valid.getTime());

	assertEquals(message + " code canton invalide", codeCanton,
		district.getCodeCanton());
    }

    protected void assertValidCantons(final String message,
	    final List<CantonWS> cantons, final Date dateValid) {
	final Date valid = dateValid == null ? new Date() : dateValid;
	for (final CantonWS c : cantons) {
	    assertTrue(
		    message
			    + "canton["
			    + c.getCode()
			    + "].validFrom="
			    + (c.getValidFrom() == null ? ""
				    : format(c.getValidFrom())) + " / "
			    + format(valid), c.getValidFrom() == null
			    || c.getValidFrom().getTime() <= valid.getTime());
	    assertTrue(
		    message
			    + "canton["
			    + c.getCode()
			    + "].validTo="
			    + (c.getValidTo() == null ? ""
				    : format(c.getValidTo())) + " / "
			    + format(valid), c.getValidTo() == null
			    || c.getValidTo().getTime() >= valid.getTime());
	}
    }

    protected void assertValidCanton(final String message,
	    final CantonWS canton, final Date dateValid) {
	final Date valid = dateValid == null ? new Date() : dateValid;
	assertTrue(
		message
			+ "'from' invalide: canton["
			+ canton.getCode()
			+ "].validFrom="
			+ (canton.getValidFrom() == null ? ""
				: format(canton.getValidFrom())) + " / "
			+ format(valid), canton.getValidFrom() == null
			|| canton.getValidFrom().getTime() <= valid.getTime());
	assertTrue(
		message
			+ "'to' invalide: canton["
			+ canton.getCode()
			+ "].validTo="
			+ (canton.getValidTo() == null ? ""
				: format(canton.getValidTo())) + " / "
			+ format(valid), canton.getValidTo() == null
			|| canton.getValidTo().getTime() >= valid.getTime());
    }

}
