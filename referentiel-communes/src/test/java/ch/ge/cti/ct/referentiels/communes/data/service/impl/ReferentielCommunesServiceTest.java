package ch.ge.cti.ct.referentiels.communes.data.service.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import ch.ge.cti.ct.referentiels.communes.data.AbstractReferentielTest;
import ch.ge.cti.ct.referentiels.communes.model.Canton;
import ch.ge.cti.ct.referentiels.communes.model.Commune;
import ch.ge.cti.ct.referentiels.communes.model.District;
import ch.ge.cti.ct.referentiels.communes.model.ReferentielCommunes;
import ch.ge.cti.ct.referentiels.communes.service.impl.ReferentielCommunesService;
import ch.ge.cti.ct.referentiels.ofs.ReferentielOfsException;

import com.google.common.base.Function;
import com.google.common.base.Predicate;
import com.google.common.collect.FluentIterable;

public class ReferentielCommunesServiceTest extends AbstractReferentielTest {

	@Before
	public void checkBefore() throws ReferentielOfsException {
		// on vérifie le référentiel avant
		checkReferentiel();
	}

	@After
	public void checkAfter() throws ReferentielOfsException {
		// on vérifie le référentiel après
		checkReferentiel();
	}

	@Test
	public void testGetReferentiel() throws ReferentielOfsException {
		checkReferentiel();
	}

	@Test
	public void testGetCantons() throws ReferentielOfsException {
		final List<Canton> cantons = ReferentielCommunesService.instance
				.getCantons();
		assertNotNull("La liste des cantons est vide", cantons);
		assertEquals("La liste des cantons est incorrecte", 26, cantons.size());
		assertEquals("La liste des cantons est incorrecte", 11, cantons.get(0)
				.getDistrict().size());
	}

	@Test
	public void testGetCanton() throws ReferentielOfsException {
		Canton canton = ReferentielCommunesService.instance.getCanton("GE");
		assertNotNull("Canton non trouvé", canton);
		assertEquals("Canton incorrect", "GE", canton.getCode());
		assertEquals("Canton.districts incorrect", 1, canton.getDistrict()
				.size());

		canton = ReferentielCommunesService.instance.getCanton("XX");
		assertNull("Canton trouvé", canton);
	}

	@Test
	public void testGetDistricts() throws ReferentielOfsException {
		List<District> districts = ReferentielCommunesService.instance
				.getDistricts("GE");
		assertEquals("Nombre de districts incorrect", 1, districts.size());
		// check shallowCopy
		assertEquals("La liste des districts est incorrecte", 45, districts
				.get(0).getCommune().size());

		districts = ReferentielCommunesService.instance.getDistricts("JU");
		assertEquals("Nombre de districts incorrect", 3, districts.size());

		districts = ReferentielCommunesService.instance.getDistricts("AI");
		assertEquals("Nombre de districts incorrect", 1, districts.size());

		districts = ReferentielCommunesService.instance.getDistricts("XX");
		assertEquals("Nombre de districts incorrect", 0, districts.size());
	}

	@Test
	public void testGetDistrict() throws ReferentielOfsException {
		District district = ReferentielCommunesService.instance
				.getDistrict(2500);
		assertNotNull("District non trouvé", district);
		assertEquals("La liste des communes est incorrecte", 45, district
				.getCommune().size());

		district = ReferentielCommunesService.instance.getDistrict(9999);
		assertNull("District non trouvé", district);
	}

	@Test
	public void testGetCommunesByDistrict() throws ReferentielOfsException {
		List<Commune> communes = ReferentielCommunesService.instance
				.getCommunesByDistrict(2500);
		assertEquals("Nombre de communes incorrect", 45, communes.size());

		communes = ReferentielCommunesService.instance
				.getCommunesByDistrict(101);
		assertEquals("Nombre de communes incorrect", 14, communes.size());

		communes = ReferentielCommunesService.instance
				.getCommunesByDistrict(1600);
		assertEquals("Nombre de communes incorrect", 6, communes.size());

		communes = ReferentielCommunesService.instance
				.getCommunesByDistrict(1601);
		assertEquals("Nombre de communes incorrect", 0, communes.size());

		communes = ReferentielCommunesService.instance
				.getCommunesByDistrict(9999);
		assertEquals("Nombre de communes incorrect", 0, communes.size());
	}

	@Test
	public void testGetCommunesByCanton() throws ReferentielOfsException {
		List<Commune> communes = ReferentielCommunesService.instance
				.getCommunesByCanton("GE");
		assertEquals("Nombre de communes incorrect", 45, communes.size());

		communes = ReferentielCommunesService.instance
				.getCommunesByCanton("JU");
		assertEquals("Nombre de communes incorrect", 57, communes.size());

		communes = ReferentielCommunesService.instance
				.getCommunesByCanton("AI");
		assertEquals("Nombre de communes incorrect", 6, communes.size());

		communes = ReferentielCommunesService.instance
				.getCommunesByCanton("XX");
		assertEquals("Nombre de communes incorrect", 0, communes.size());
	}

	/** Date minimale de validité: 01-01-1960 */
	private static Date DATE_01011960 = new Date(-315619200000l);
	private static Date DATE_01011979 = new Date(283996800000l);
	private static Date DATE_31121989 = new Date(631065600000l);
	private static Date DATE_01011990 = new Date(631152000000l);
	private static Date DATE_31121996 = new Date(851990400000l);
	private static Date DATE_01011997 = new Date(852076800000l);
	private static Date DATE_01012000 = new Date(946684800000l);

	@Test
	public void testGetCantonsValidJU() throws ReferentielOfsException {
		List<Canton> cantons = ReferentielCommunesService.instance
				.getCantons(DATE_01011960);
		// le canton du JURA a été créé en 1979
		assertEquals("La liste des cantons est incorrecte", 25, cantons.size());

		cantons = ReferentielCommunesService.instance.getCantons(DATE_01011979);
		assertEquals("La liste des cantons est incorrecte", 26, cantons.size());

		cantons = ReferentielCommunesService.instance.getCantons(DATE_01012000);
		assertEquals("La liste des cantons est incorrecte", 26, cantons.size());

		cantons = ReferentielCommunesService.instance.getCantons(new Date());
		assertEquals("La liste des cantons est incorrecte", 26, cantons.size());
	}

	private Date[] DATES_VALID = { DATE_01011960, DATE_01011979, DATE_31121989,
			DATE_01011990, DATE_31121996, DATE_01011997, DATE_01012000,
			new Date() };

	@Test
	public void testGetDistrictsValidJU() throws ReferentielOfsException {
		List<District> districts = ReferentielCommunesService.instance
				.getDistricts("JU");
		assertEquals("Nombre de districts incorrect", 3, districts.size());

		districts = ReferentielCommunesService.instance.getDistricts("JU",
				DATE_01011960);
		assertEquals("Nombre de districts incorrect", 0, districts.size());

		districts = ReferentielCommunesService.instance.getDistricts("JU",
				DATE_01011979);
		assertEquals("Nombre de districts incorrect", 3, districts.size());

		districts = ReferentielCommunesService.instance.getDistricts("JU",
				new Date());
		assertEquals("Nombre de districts incorrect", 3, districts.size());

		for (Date dateValid : DATES_VALID) {
			districts = ReferentielCommunesService.instance.getDistricts("JU",
					dateValid);
			for (District d : districts) {
				assertTrue(
						"district.validFrom est incorrect",
						d.getValidFrom() == null
								|| d.getValidFrom().getTime() <= dateValid
										.getTime());
				assertTrue(
						"district.validTo est incorrect",
						d.getValidTo() == null
								|| d.getValidTo().getTime() >= dateValid
										.getTime());
			}
		}
	}

	@Test
	public void testGetDistrictsValidZH() throws ReferentielOfsException {
		List<District> districts = ReferentielCommunesService.instance
				.getDistricts("ZH");
		assertEquals("Nombre de districts incorrect", 12, districts.size());

		districts = ReferentielCommunesService.instance.getDistricts("ZH",
				DATE_01011960);
		assertEquals("Nombre de districts incorrect", 11, districts.size());

		districts = ReferentielCommunesService.instance.getDistricts("ZH",
				DATE_31121989);
		assertEquals("Nombre de districts incorrect", 11, districts.size());

		districts = ReferentielCommunesService.instance.getDistricts("ZH",
				DATE_01011990);
		assertEquals("Nombre de districts incorrect", 12, districts.size());

		districts = ReferentielCommunesService.instance.getDistricts("ZH",
				new Date());
		assertEquals("Nombre de districts incorrect", 12, districts.size());

		for (Date dateValid : DATES_VALID) {
			districts = ReferentielCommunesService.instance.getDistricts("ZH",
					dateValid);
			for (District d : districts) {
				assertTrue(
						"district.validFrom est incorrect",
						d.getValidFrom() == null
								|| d.getValidFrom().getTime() <= dateValid
										.getTime());
				assertTrue(
						"district.validTo est incorrect",
						d.getValidTo() == null
								|| d.getValidTo().getTime() >= dateValid
										.getTime());
			}
		}
	}

	@Test
	public void testGetDistrictsValidAI() throws ReferentielOfsException {
		List<District> districts = ReferentielCommunesService.instance
				.getDistricts("AI");
		assertEquals("Nombre de districts incorrect", 1, districts.size());

		districts = ReferentielCommunesService.instance.getDistricts("AI",
				DATE_01011960);
		assertEquals("Nombre de districts incorrect", 2, districts.size());

		districts = ReferentielCommunesService.instance.getDistricts("AI",
				DATE_31121996);
		assertEquals("Nombre de districts incorrect", 2, districts.size());

		districts = ReferentielCommunesService.instance.getDistricts("AI",
				DATE_01011997);
		assertEquals("Nombre de districts incorrect", 1, districts.size());

		districts = ReferentielCommunesService.instance.getDistricts("AI",
				new Date());
		assertEquals("Nombre de districts incorrect", 1, districts.size());

		for (Date dateValid : DATES_VALID) {
			districts = ReferentielCommunesService.instance.getDistricts("AI",
					dateValid);
			for (District d : districts) {
				assertTrue(
						"district.validFrom est incorrect",
						d.getValidFrom() == null
								|| d.getValidFrom().getTime() <= dateValid
										.getTime());
				assertTrue(
						"district.validTo est incorrect",
						d.getValidTo() == null
								|| d.getValidTo().getTime() >= dateValid
										.getTime());
			}
		}
	}

	@Test
	public void testGetCommunesByDistrictValidAI()
			throws ReferentielOfsException {
		List<Commune> communes = null;

		communes = ReferentielCommunesService.instance
				.getCommunesByDistrict(1600);
		assertEquals("Nombre de communes incorrect", 6, communes.size());

		communes = ReferentielCommunesService.instance.getCommunesByDistrict(
				1600, DATE_01011960);
		assertEquals("Nombre de communes incorrect", 0, communes.size());

		communes = ReferentielCommunesService.instance.getCommunesByDistrict(
				1600, DATE_01011979);
		assertEquals("Nombre de communes incorrect", 0, communes.size());

		communes = ReferentielCommunesService.instance.getCommunesByDistrict(
				1600, DATE_31121989);
		assertEquals("Nombre de communes incorrect", 0, communes.size());

		communes = ReferentielCommunesService.instance.getCommunesByDistrict(
				1600, DATE_31121996);
		assertEquals("Nombre de communes incorrect", 0, communes.size());

		communes = ReferentielCommunesService.instance.getCommunesByDistrict(
				1600, DATE_01011997);
		assertEquals("Nombre de communes incorrect", 6, communes.size());

		communes = ReferentielCommunesService.instance.getCommunesByDistrict(
				1600, new Date());
		assertEquals("Nombre de communes incorrect", 6, communes.size());

		communes = ReferentielCommunesService.instance.getCommunesByDistrict(
				1601, DATE_01011960);
		assertEquals("Nombre de communes incorrect", 5, communes.size());

		communes = ReferentielCommunesService.instance.getCommunesByDistrict(
				1601, DATE_01011979);
		assertEquals("Nombre de communes incorrect", 5, communes.size());

		communes = ReferentielCommunesService.instance.getCommunesByDistrict(
				1601, DATE_31121989);
		assertEquals("Nombre de communes incorrect", 5, communes.size());

		communes = ReferentielCommunesService.instance.getCommunesByDistrict(
				1601, DATE_31121996);
		assertEquals("Nombre de communes incorrect", 5, communes.size());

		communes = ReferentielCommunesService.instance.getCommunesByDistrict(
				1601, DATE_01011997);
		assertEquals("Nombre de communes incorrect", 0, communes.size());

		communes = ReferentielCommunesService.instance.getCommunesByDistrict(
				1601, new Date());
		assertEquals("Nombre de communes incorrect", 0, communes.size());

		communes = ReferentielCommunesService.instance.getCommunesByDistrict(
				1602, DATE_01011960);
		assertEquals("Nombre de communes incorrect", 1, communes.size());

		communes = ReferentielCommunesService.instance.getCommunesByDistrict(
				1602, DATE_01011979);
		assertEquals("Nombre de communes incorrect", 1, communes.size());

		communes = ReferentielCommunesService.instance.getCommunesByDistrict(
				1602, DATE_31121989);
		assertEquals("Nombre de communes incorrect", 1, communes.size());

		communes = ReferentielCommunesService.instance.getCommunesByDistrict(
				1602, DATE_31121996);
		assertEquals("Nombre de communes incorrect", 1, communes.size());

		communes = ReferentielCommunesService.instance.getCommunesByDistrict(
				1602, DATE_01011997);
		assertEquals("Nombre de communes incorrect", 0, communes.size());

		communes = ReferentielCommunesService.instance.getCommunesByDistrict(
				1602, new Date());
		assertEquals("Nombre de communes incorrect", 0, communes.size());

		for (Date dateValid : DATES_VALID) {
			communes = ReferentielCommunesService.instance
					.getCommunesByDistrict(1600, dateValid);
			for (Commune d : communes) {
				assertTrue(
						"communes.validFrom est incorrect",
						d.getValidFrom() == null
								|| d.getValidFrom().getTime() <= dateValid
										.getTime());
				assertTrue(
						"communes.validTo est incorrect",
						d.getValidTo() == null
								|| d.getValidTo().getTime() >= dateValid
										.getTime());
			}
		}

		for (Date dateValid : DATES_VALID) {
			communes = ReferentielCommunesService.instance
					.getCommunesByDistrict(1601, dateValid);
			for (Commune d : communes) {
				assertTrue(
						"communes.validFrom est incorrect",
						d.getValidFrom() == null
								|| d.getValidFrom().getTime() <= dateValid
										.getTime());
				assertTrue(
						"communes.validTo est incorrect",
						d.getValidTo() == null
								|| d.getValidTo().getTime() >= dateValid
										.getTime());
			}
		}

		for (Date dateValid : DATES_VALID) {
			communes = ReferentielCommunesService.instance
					.getCommunesByDistrict(1602, dateValid);
			for (Commune d : communes) {
				assertTrue(
						"communes.validFrom est incorrect",
						d.getValidFrom() == null
								|| d.getValidFrom().getTime() <= dateValid
										.getTime());
				assertTrue(
						"communes.validTo est incorrect",
						d.getValidTo() == null
								|| d.getValidTo().getTime() >= dateValid
										.getTime());
			}
		}
	}

	@Test
	public void testGetCommunesByCantonValidAI() throws ReferentielOfsException {
		List<Commune> communes = null;

		communes = ReferentielCommunesService.instance
				.getCommunesByCanton("AI");
		assertEquals("Nombre de communes incorrect", 6, communes.size());

		communes = ReferentielCommunesService.instance.getCommunesByCanton(
				"AI", DATE_01011960);
		assertEquals("Nombre de communes incorrect", 6, communes.size());

		communes = ReferentielCommunesService.instance.getCommunesByCanton(
				"AI", DATE_01011979);
		assertEquals("Nombre de communes incorrect", 6, communes.size());

		communes = ReferentielCommunesService.instance.getCommunesByCanton(
				"AI", DATE_31121989);
		assertEquals("Nombre de communes incorrect", 6, communes.size());

		communes = ReferentielCommunesService.instance.getCommunesByCanton(
				"AI", DATE_31121996);
		assertEquals("Nombre de communes incorrect", 6, communes.size());

		communes = ReferentielCommunesService.instance.getCommunesByCanton(
				"AI", DATE_01011997);
		assertEquals("Nombre de communes incorrect", 6, communes.size());

		communes = ReferentielCommunesService.instance.getCommunesByCanton(
				"AI", new Date());
		assertEquals("Nombre de communes incorrect", 6, communes.size());

		for (Date dateValid : DATES_VALID) {
			communes = ReferentielCommunesService.instance.getCommunesByCanton(
					"AI", dateValid);
			for (Commune d : communes) {
				assertTrue(
						"communes.validFrom est incorrect",
						d.getValidFrom() == null
								|| d.getValidFrom().getTime() <= dateValid
										.getTime());
				assertTrue(
						"communes.validTo est incorrect",
						d.getValidTo() == null
								|| d.getValidTo().getTime() >= dateValid
										.getTime());
			}
		}
	}

	//========================================================================================================================================================
	//========================================================================================================================================================

	private void checkReferentiel() throws ReferentielOfsException {
		final ReferentielCommunes referentiel = ReferentielCommunesService.instance
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
