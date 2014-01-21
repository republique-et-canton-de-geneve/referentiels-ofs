package ch.ge.cti.ct.referentiels.communes.interfaces.ejb;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;

import org.junit.Test;

import ch.ge.cti.ct.referentiels.communes.AbstractReferentielTest;
import ch.ge.cti.ct.referentiels.communes.model.Canton;
import ch.ge.cti.ct.referentiels.communes.model.Commune;
import ch.ge.cti.ct.referentiels.communes.model.District;
import ch.ge.cti.ct.referentiels.communes.model.ReferentielCommunes;

public class ReferentielCommunesBeanTest extends AbstractReferentielTest {

	final ReferentielCommunesBean bean = new ReferentielCommunesBean();

	@Test
	public void testGetReferentiel() throws Exception {
		final ReferentielCommunes ref = bean.getReferentiel();
		assertNotNull(ref);
		assertEquals("ReferentielCommunes.cantons.size incorrect", 26, ref
				.getCanton().size());
	}

	@Test
	public void testGetCantons() throws Exception {
		final List<Canton> cantons = bean.getCantons();
		assertNotNull(cantons);
		assertEquals("ReferentielCommunes.cantons.size incorrect", 26,
				cantons.size());
	}

	@Test
	public void testGetCanton() throws Exception {
		Canton canton = bean.getCanton("GE");
		assertNotNull(canton);
		assertEquals("ReferentielCommunes.cantons[GE].code incorrect", "GE",
				canton.getCode());
		assertEquals("ReferentielCommunes.cantons[GE].nom incorrect", "Genève",
				canton.getNom());
		assertEquals("ReferentielCommunes.cantons[GE].nomCourt incorrect",
				"GE", canton.getNomCourt());
		assertEquals(
				"ReferentielCommunes.cantons[GE].districts.size incorrect", 1,
				canton.getDistrict().size());

		canton = bean.getCanton("JU");
		assertNotNull(canton);
		assertEquals("ReferentielCommunes.cantons[JU].code incorrect", "JU",
				canton.getCode());
		assertEquals("ReferentielCommunes.cantons[JU].nom incorrect", "Jura",
				canton.getNom());
		assertNotNull("ReferentielCommunes.cantons[JU].validFrom incorrect",
				canton.getValidFrom());
		assertEquals(
				"ReferentielCommunes.cantons[JU].districts.size incorrect", 3,
				canton.getDistrict().size());
	}

	@Test
	public void testGetDistricts() throws Exception {
		final List<District> districts = bean.getDistricts("AI");
		assertNotNull(districts);
		assertEquals("ReferentielCommunes.districts[AI].size incorrect", 1,
				districts.size());
	}

	@Test
	public void testGetDistrict() throws Exception {
		final District district = bean.getDistrict(1601);
		assertNotNull(district);
		assertEquals("ReferentielCommunes.districts[1601].code incorrect",
				1601, district.getId());
		assertEquals("ReferentielCommunes.districts[1601].nom incorrect",
				"Innerer Landesteil", district.getNom());
		assertEquals("ReferentielCommunes.districts[1601].nomCourt incorrect",
				"Innerer Landesteil", district.getNomCourt());
		assertEquals(
				"ReferentielCommunes.districts[1601].communes.size incorrect",
				5, district.getCommune().size());
	}

	@Test
	public void testGetCommunesByDistrict() throws Exception {
		List<Commune> communes = bean.getCommunesByDistrict(1601);
		assertNotNull(communes);
		assertEquals(
				"ReferentielCommunes.districts[1601].communes.size incorrect",
				0, communes.size());

		communes = bean.getCommunesByDistrict(1600);
		assertNotNull(communes);
		assertEquals(
				"ReferentielCommunes.districts[1600].communes.size incorrect",
				6, communes.size());
	}

	@Test
	public void testGetCommunesByCanton() throws Exception {
		List<Commune> communes = bean.getCommunesByCanton("AI");
		assertNotNull(communes);
		assertEquals("ReferentielCommunes.Cantons[AI].communes.size incorrect",
				6, communes.size());

		communes = bean.getCommunesByCanton("GE");
		assertNotNull(communes);
		assertEquals("ReferentielCommunes.Cantons[GE].communes.size incorrect",
				45, communes.size());

		communes = bean.getCommunesByCanton("BE");
		assertNotNull(communes);
		assertEquals("ReferentielCommunes.Cantons[BE].communes.size incorrect",
				362, communes.size());

		communes = bean.getCommunesByCanton("ZH");
		assertNotNull(communes);
		assertEquals("ReferentielCommunes.Cantons[ZH].communes.size incorrect",
				170, communes.size());
	}

	@Test
	public void testGetCommune() throws Exception {
		Commune commune = bean.getCommune(6602);
		assertNotNull(commune);
		assertEquals("ReferentielCommunes.communes[6602].code incorrect", 6602,
				commune.getId());
		assertEquals("ReferentielCommunes.communes[6602].nom incorrect",
				"Anières", commune.getNom());
		assertEquals("ReferentielCommunes.communes[6602].nomCourt incorrect",
				"Anières", commune.getNomCourt());
	}

	@Test
	public void testGetCantonsDate() throws Exception {
		List<Canton> cantons = bean.getCantons(TODAY);
		assertNotNull(cantons);
		assertValidCantons(cantons, TODAY);
		assertEquals("ReferentielCommunes.cantons.size incorrect", 26,
				cantons.size());

		cantons = bean.getCantons(DATE_31121978);
		assertNotNull(cantons);
		assertValidCantons(cantons, DATE_31121978);
		assertEquals("ReferentielCommunes.cantons.size incorrect", 25,
				cantons.size());

		cantons = bean.getCantons(DATE_01011997);
		assertNotNull(cantons);
		assertValidCantons(cantons, DATE_01011997);
		assertEquals("ReferentielCommunes.cantons.size incorrect", 26,
				cantons.size());
	}

	@Test
	public void testGetDistrictsDate() throws Exception {
		List<District> districts = bean.getDistricts("AI", TODAY);
		assertNotNull(districts);
		assertValidDistricts(districts, TODAY);
		assertEquals("ReferentielCommunes.districts[AI].size incorrect", 1,
				districts.size());

		districts = bean.getDistricts("AI", DATE_31121996);
		assertNotNull(districts);
		assertValidDistricts(districts, DATE_31121996);
		assertEquals("ReferentielCommunes.districts[AI].size incorrect", 2,
				districts.size());

		districts = bean.getDistricts("AI", DATE_01011997);
		assertNotNull(districts);
		assertValidDistricts(districts, DATE_01011997);
		assertEquals("ReferentielCommunes.districts[AI].size incorrect", 1,
				districts.size());

		districts = bean.getDistricts("JU", DATE_01011960);
		assertNotNull(districts);
		assertEquals("ReferentielCommunes.districts[JU].size incorrect", 0,
				districts.size());
	}

	@Test
	public void testGetCommunesByDistrictDate1600() throws Exception {
		List<Commune> communes = bean.getCommunesByDistrict(1600, TODAY);
		assertNotNull(communes);
		assertValidCommunes(communes, TODAY);
		assertEquals(
				"ReferentielCommunes.districts[1600].communes.size incorrect",
				6, communes.size());

		communes = bean.getCommunesByDistrict(1600, DATE_31121996);
		assertNotNull(communes);
		assertValidCommunes(communes, DATE_31121996);
		assertEquals(
				"ReferentielCommunes.districts[1600].communes.size incorrect",
				0, communes.size());

		communes = bean.getCommunesByDistrict(1600, DATE_01011997);
		assertNotNull(communes);
		assertValidCommunes(communes, DATE_01011997);
		assertEquals(
				"ReferentielCommunes.districts[1600].communes.size incorrect",
				6, communes.size());
	}

	@Test
	public void testGetCommunesByDistrictDate1601() throws Exception {
		List<Commune> communes = bean.getCommunesByDistrict(1601, TODAY);
		assertNotNull(communes);
		assertValidCommunes(communes, TODAY);
		assertEquals(
				"ReferentielCommunes.districts[1601].communes.size incorrect",
				0, communes.size());

		communes = bean.getCommunesByDistrict(1601, DATE_31121996);
		assertNotNull(communes);
		assertValidCommunes(communes, DATE_31121996);
		assertEquals(
				"ReferentielCommunes.districts[1601].communes.size incorrect",
				5, communes.size());

		communes = bean.getCommunesByDistrict(1601, DATE_01011997);
		assertNotNull(communes);
		assertValidCommunes(communes, DATE_01011997);
		assertEquals(
				"ReferentielCommunes.districts[1601].communes.size incorrect",
				0, communes.size());
	}

	@Test
	public void testGetCommunesByCantonDateAI() throws Exception {
		List<Commune> communes = bean.getCommunesByCanton("AI", TODAY);
		assertNotNull(communes);
		assertValidCommunes(communes, TODAY);
		assertEquals("ReferentielCommunes.Cantons[AI].communes.size incorrect",
				6, communes.size());

		communes = bean.getCommunesByCanton("AI", DATE_01011960);
		assertNotNull(communes);
		assertValidCommunes(communes, DATE_01011960);
		assertEquals("ReferentielCommunes.Cantons[AI].communes.size incorrect",
				6, communes.size());
	}

	@Test
	public void testGetCommunesByCantonDateJU() throws Exception {
		List<Commune> communes = bean.getCommunesByCanton("JU", DATE_01011960);
		assertNotNull(communes);
		assertValidCommunes(communes, TODAY);
		assertEquals("ReferentielCommunes.Cantons[JU].communes.size incorrect",
				0, communes.size());

		communes = bean.getCommunesByCanton("JU", DATE_01011979);
		assertNotNull(communes);
		assertValidCommunes(communes, DATE_01011979);
		assertEquals("ReferentielCommunes.Cantons[JU].communes.size incorrect",
				82, communes.size());
	}

	@Test
	public void testGetCommunesByCantonDateBE() throws Exception {
		List<Commune> communes = bean.getCommunesByCanton("BE", TODAY);
		assertNotNull(communes);
		assertValidCommunes(communes, TODAY);
		assertEquals("ReferentielCommunes.Cantons[BE].communes.size incorrect",
				362, communes.size());

		communes = bean.getCommunesByCanton("BE", DATE_01011960);
		assertNotNull(communes);
		assertValidCommunes(communes, DATE_01011960);
		assertEquals("ReferentielCommunes.Cantons[BE].communes.size incorrect",
				492, communes.size());
	}

	@Test
	public void testGetCommunesByCantonDateGE() throws Exception {
		List<Commune> communes = bean.getCommunesByCanton("GE", TODAY);
		assertNotNull(communes);
		assertValidCommunes(communes, TODAY);
		assertEquals("ReferentielCommunes.Cantons[GE].communes.size incorrect",
				45, communes.size());

		communes = bean.getCommunesByCanton("GE", DATE_01011960);
		assertNotNull(communes);
		assertValidCommunes(communes, DATE_01011960);
		assertEquals("ReferentielCommunes.Cantons[GE].communes.size incorrect",
				45, communes.size());
	}

	@Test
	public void testGetCommunesByCantonDateZH() throws Exception {
		List<Commune> communes = bean.getCommunesByCanton("ZH", TODAY);
		assertNotNull(communes);
		assertValidCommunes(communes, TODAY);
		assertEquals("ReferentielCommunes.Cantons[ZH].communes.size incorrect",
				170, communes.size());

		communes = bean.getCommunesByCanton("ZH", DATE_01011960);
		assertNotNull(communes);
		assertValidCommunes(communes, DATE_01011960);
		assertEquals("ReferentielCommunes.Cantons[ZH].communes.size incorrect",
				171, communes.size());

		communes = bean.getCommunesByCanton("ZH", DATE_01011970);
		assertNotNull(communes);
		assertValidCommunes(communes, DATE_01011970);
		assertEquals("ReferentielCommunes.Cantons[ZH].communes.size incorrect",
				170, communes.size());

		communes = bean.getCommunesByCanton("ZH", DATE_15111976);
		assertNotNull(communes);
		assertValidCommunes(communes, DATE_15111976);
		assertEquals("ReferentielCommunes.Cantons[ZH].communes.size incorrect",
				169, communes.size());

		communes = bean.getCommunesByCanton("ZH", DATE_10031977);
		assertNotNull(communes);
		assertValidCommunes(communes, DATE_10031977);
		assertEquals("ReferentielCommunes.Cantons[ZH].communes.size incorrect",
				170, communes.size());

		communes = bean.getCommunesByCanton("ZH", DATE_31121989);
		assertNotNull(communes);
		assertValidCommunes(communes, DATE_31121989);
		assertEquals("ReferentielCommunes.Cantons[ZH].communes.size incorrect",
				171, communes.size());

		communes = bean.getCommunesByCanton("ZH", DATE_01011990);
		assertNotNull(communes);
		assertValidCommunes(communes, DATE_01011990);
		assertEquals("ReferentielCommunes.Cantons[ZH].communes.size incorrect",
				171, communes.size());

		communes = bean.getCommunesByCanton("ZH", DATE_31122013);
		assertNotNull(communes);
		assertValidCommunes(communes, DATE_31122013);
		assertEquals("ReferentielCommunes.Cantons[ZH].communes.size incorrect",
				171, communes.size());

		communes = bean.getCommunesByCanton("ZH", DATE_01012014);
		assertNotNull(communes);
		assertValidCommunes(communes, DATE_01012014);
		assertEquals("ReferentielCommunes.Cantons[ZH].communes.size incorrect",
				169, communes.size());
	}

	@SuppressWarnings("unused")
	private void dump(List<Commune> communes) {
		System.err
				.println("------------------------------------------------------");
		DateFormat df = new SimpleDateFormat("dd.MM.yy");
		for (Commune c : communes) {
			if (c.getValidTo() != null || c.getValidFrom() != null) {
				System.err.print(c.getId() + "\t ==> ");
				System.err.print("\t from: ");
				System.err.print(c.getValidFrom() == null ? "          " : df
						.format(c.getValidFrom()));
				System.err.print("\t to: ");
				System.err.print(c.getValidTo() == null ? "          " : df
						.format(c.getValidTo()));
				System.err.println("");
			}
		}
		System.err
				.println("------------------------------------------------------\n");
	}

}
