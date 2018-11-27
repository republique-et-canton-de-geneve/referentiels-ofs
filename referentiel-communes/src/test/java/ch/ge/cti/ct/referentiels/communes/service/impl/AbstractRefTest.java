package ch.ge.cti.ct.referentiels.communes.service.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.databene.contiperf.junit.ContiPerfRule;
import org.junit.BeforeClass;
import org.junit.Rule;

import ch.ge.cti.ct.referentiels.communes.AbstractReferentielTest;
import ch.ge.cti.ct.referentiels.communes.model.Canton;
import ch.ge.cti.ct.referentiels.communes.model.Commune;
import ch.ge.cti.ct.referentiels.communes.model.District;
import ch.ge.cti.ct.referentiels.communes.model.ReferentielCommunes;
import ch.ge.cti.ct.referentiels.ofs.ReferentielOfsException;

import com.google.common.base.Function;
import com.google.common.base.Predicate;
import com.google.common.collect.FluentIterable;

public abstract class AbstractRefTest extends AbstractReferentielTest {

    @Rule
    public ContiPerfRule rule = new ContiPerfRule();

    @BeforeClass
    public static void load() throws ReferentielOfsException {
	ReferentielCommunesService.INSTANCE.getReferentiel();
    }

    protected void assertValidCommunes(final String message,
	    final List<Commune> communes, final Date dateValid,
	    final String cantonCode, final int idDistrict,
	    final int countCommunes) {
	final Date valid = dateValid == null ? new Date() : dateValid;
	assertEquals(message + "le nombre de communes est incorrect",
		countCommunes, communes.size());
	for (Commune c : communes) {
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
	    final List<District> districts, final Date dateValid,
	    final String codeCanton) {
	final Date valid = dateValid == null ? new Date() : dateValid;
	for (District c : districts) {
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
	    final District district, final Date dateValid,
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
	    final List<Canton> cantons, final Date dateValid) {
	final Date valid = dateValid == null ? new Date() : dateValid;
	for (Canton c : cantons) {
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

    protected void assertValidCanton(final String message, final Canton canton,
	    final Date dateValid) {
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

    protected void checkReferentiel() throws ReferentielOfsException {
	final ReferentielCommunes referentiel = ReferentielCommunesService.INSTANCE
		.getReferentiel();
	assertNotNull("Le référentiel est vide", referentiel);
	assertEquals("La liste des cantons est incorrecte", 26, referentiel
		.getCanton().size());

	assertTrue(
		"Certains cantons n'ont pas de district",
		FluentIterable.from(referentiel.getCanton()).allMatch(
			new Predicate<Canton>() {
			    @Override
			    public boolean apply(final Canton canton) {
				return canton.getDistrict() != null
					&& canton.getDistrict().size() > 0
					&& StringUtils.isNotBlank(canton
						.getCode())
					&& StringUtils.isNotBlank(canton
						.getNom());
			    }
			}));

	assertTrue(
		"Certains districts n'ont pas de commune",
		FluentIterable
			.from(referentiel.getCanton())
			.transformAndConcat(
				new Function<Canton, Iterable<? extends District>>() {
				    @Override
				    public Iterable<? extends District> apply(
					    final Canton canton) {
					return canton.getDistrict();
				    }
				}).allMatch(new Predicate<District>() {
			    @Override
			    public boolean apply(final District district) {
				return district.getCommune() != null
					&& district.getCommune().size() > 0
					&& StringUtils.isNotBlank(district
						.getNom());
			    }
			}));
	assertFalse("La liste des communes est vide", referentiel.getCanton()
		.get(0).getDistrict().get(0).getCommune().size() == 0);
    }
}
