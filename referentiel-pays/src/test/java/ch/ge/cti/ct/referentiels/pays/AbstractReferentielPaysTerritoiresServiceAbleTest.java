package ch.ge.cti.ct.referentiels.pays;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import ch.ge.cti.ct.referentiels.ofs.ReferentielOfsException;
import ch.ge.cti.ct.referentiels.pays.model.Continent;
import ch.ge.cti.ct.referentiels.pays.model.Pays;
import ch.ge.cti.ct.referentiels.pays.model.ReferentielPaysTerritoires;
import ch.ge.cti.ct.referentiels.pays.model.Region;
import ch.ge.cti.ct.referentiels.pays.service.ReferentielPaysTerritoiresServiceAble;

import com.google.common.base.Function;
import com.google.common.base.Predicate;
import com.google.common.collect.FluentIterable;

public abstract class AbstractReferentielPaysTerritoiresServiceAbleTest extends
		AbstractReferentielTest {

	protected abstract ReferentielPaysTerritoiresServiceAble getService();

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
	public void testGetContinents() throws ReferentielOfsException {
		final List<Continent> continents = getService().getContinents();
		assertNotNull("La liste des continents est vide", continents);
		assertEquals("La liste des continents est incorrecte", 6,
				continents.size());
		assertEquals("La liste des régions du continent est incorrecte", 7,
				continents.get(0).getRegion().size());
		for (Continent c : continents) {
			if (c.getId() == 3) {
				assertEquals("Continent.nom est incorrect", "Amérique",
						c.getNom());
			}
		}
	}

	@Test
	public void testGetPays() throws ReferentielOfsException {
		final List<Pays> pays = getService().getPays();
		assertNotNull("La liste des pays est vide", pays);
		assertEquals("La liste des pays est incorrecte", 272, pays.size());
		for (Pays p : pays) {
			if (p.getId() == 8210) {
				assertEquals("Pays.nom est incorrect", "Iles Féroé", p.getNom());
			} else if (p.getId() == 8201) {
				assertEquals("Pays.nomLong est incorrect",
						"République d'Albanie", p.getNomLong());
			}
			assertFalse("Pays inexistant au jour d'aujourd'hui",
					p.getId() == 8209);
		}
	}

	@Test
	public void testGetContinent() throws ReferentielOfsException {
		Continent continent = getService().getContinent(1);
		assertNotNull("Continent non trouvé", continent);
		assertEquals("Continent incorrect", 1, continent.getId());
		assertEquals("Continent.regions incorrect", 7, continent.getRegion()
				.size());

		continent = getService().getContinent(99);
		assertNull("Continent trouvé", continent);

		continent = getService().getContinent(0);
		assertNull("Continent trouvé", continent);
	}

	@Test
	public void testGetRegions() throws ReferentielOfsException {
		List<Region> regions = getService().getRegions();
		assertEquals("Nombre de regions incorrect", 32, regions.size());
		for (Region r : regions) {
			if (r.getId() == 15) {
				assertEquals("Region.nom est incorrect",
						"Europe méridionale occidentale", r.getNom());
			}
		}
		regions = getService().getRegions(2);
		assertEquals("Nombre de regions incorrect", 7, regions.size());

		regions = getService().getRegions(3);
		assertEquals("Nombre de regions incorrect", 6, regions.size());

		regions = getService().getRegions(0);
		assertEquals("Nombre de regions incorrect", 0, regions.size());

		regions = getService().getRegions(99);
		assertEquals("Nombre de regions incorrect", 0, regions.size());
	}

	@Test
	public void testGetRegionsByContinent() throws ReferentielOfsException {
		List<Region> regions = getService().getRegions(1);
		assertEquals("Nombre de regions incorrect", 7, regions.size());
		assertEquals("La liste des regions est incorrecte", 8, regions.get(0)
				.getPays().size());

		regions = getService().getRegions(2);
		assertEquals("Nombre de regions incorrect", 7, regions.size());

		regions = getService().getRegions(3);
		assertEquals("Nombre de regions incorrect", 6, regions.size());

		regions = getService().getRegions(0);
		assertEquals("Nombre de regions incorrect", 0, regions.size());

		regions = getService().getRegions(99);
		assertEquals("Nombre de regions incorrect", 0, regions.size());
	}

	@Test
	public void testGetRegion() throws ReferentielOfsException {
		Region region = getService().getRegion(11);
		assertNotNull("Region non trouvé", region);
		assertEquals("La liste des pays est incorrecte", 8, region.getPays()
				.size());

		region = getService().getRegion(9999);
		assertNull("Region non trouvé", region);
	}

	@Test
	public void testGetPaysByRegion() throws ReferentielOfsException {
		List<Pays> pays = getService().getPaysByRegion(11);
		assertEquals("Nombre de pays incorrect", 8, pays.size());

		pays = getService().getPaysByRegion(11);
		assertEquals("Nombre de pays incorrect", 8, pays.size());

		pays = getService().getPaysByRegion(12);
		assertEquals("Nombre de pays incorrect", 13, pays.size());

		pays = getService().getPaysByRegion(61);
		assertEquals("Nombre de pays incorrect", 3, pays.size());

		pays = getService().getPaysByRegion(0);
		assertEquals("Nombre de pays incorrect", 0, pays.size());

		pays = getService().getPaysByRegion(9999);
		assertEquals("Nombre de pays incorrect", 0, pays.size());
	}

	@Test
	public void testGetPaysByContinent() throws ReferentielOfsException {
		List<Pays> pays = getService().getPaysByContinent(1);
		assertEquals("Nombre de pays incorrect", 64, pays.size());

		pays = getService().getPaysByContinent(2);
		assertEquals("Nombre de pays incorrect", 64, pays.size());

		pays = getService().getPaysByContinent(3);
		assertEquals("Nombre de pays incorrect", 56, pays.size());

		pays = getService().getPaysByContinent(4);
		assertEquals("Nombre de pays incorrect", 53, pays.size());

		pays = getService().getPaysByContinent(5);
		assertEquals("Nombre de pays incorrect", 32, pays.size());

		pays = getService().getPaysByContinent(6);
		assertEquals("Nombre de pays incorrect", 3, pays.size());

		pays = getService().getPaysByContinent(0);
		assertEquals("Nombre de pays incorrect", 0, pays.size());

		pays = getService().getPaysByContinent(7);
		assertEquals("Nombre de pays incorrect", 0, pays.size());
	}

	//========================================================================================================================================================
	//========================================================================================================================================================

	private void checkReferentiel() throws ReferentielOfsException {
		final ReferentielPaysTerritoires referentiel = getService()
				.getReferentiel();
		assertNotNull("Le référentiel est vide", referentiel);
		assertEquals("La liste des continents est incorrecte", 6, referentiel
				.getContinent().size());

		FluentIterable.from(referentiel.getContinent()).allMatch(
				new Predicate<Continent>() {
					@Override
					public boolean apply(final Continent continent) {
						assertTrue("Continent[" + continent.getId()
								+ "].id est incorrect", continent.getId() > 0);
						assertTrue("Continent[" + continent.getId()
								+ "].nom est vide ou null",
								StringUtils.isNotBlank(continent.getNom()));
						assertNotNull("Continent[" + continent.getId()
								+ "].regions est null", continent.getRegion());
						assertTrue("Continent[" + continent.getId()
								+ "].regions.size=0", continent.getRegion()
								.size() > 0);
						return true;
					}
				});

		FluentIterable
				.from(referentiel.getContinent())
				.transformAndConcat(
						new Function<Continent, Iterable<? extends Region>>() {
							@Override
							public Iterable<? extends Region> apply(
									final Continent continent) {
								return continent.getRegion();
							}
						}).allMatch(new Predicate<Region>() {
					@Override
					public boolean apply(final Region region) {
						assertTrue("Region[" + region.getId()
								+ "].id est incorrect", region.getId() > 0);
						assertTrue("Region[" + region.getId()
								+ "].nom est vide ou null",
								StringUtils.isNotBlank(region.getNom()));
						assertNotNull("Region[" + region.getId()
								+ "].pays est null", region.getPays());
						assertTrue(
								"Region[" + region.getId() + "].pays.size=0",
								region.getPays().size() > 0);
						return region.getPays() != null
								&& region.getPays().size() > 0
								&& StringUtils.isNotBlank(region.getNom());
					}
				});

		FluentIterable
				.from(referentiel.getContinent())
				.transformAndConcat(
						new Function<Continent, Iterable<? extends Region>>() {
							@Override
							public Iterable<? extends Region> apply(
									final Continent continent) {
								return continent.getRegion();
							}
						})
				.transformAndConcat(
						new Function<Region, Iterable<? extends Pays>>() {
							@Override
							public Iterable<? extends Pays> apply(
									final Region region) {
								return region.getPays();
							}
						}).allMatch(new Predicate<Pays>() {
					@Override
					public boolean apply(final Pays pays) {
						assertTrue("Pays[" + pays.getId()
								+ "].id est incorrect", pays.getId() > 0);
						assertTrue("Pays[" + pays.getId()
								+ "].nom est vide ou null",
								StringUtils.isNotBlank(pays.getNom()));
						assertTrue("Pays[" + pays.getId()
								+ "].nomLong est vide ou null",
								StringUtils.isNotBlank(pays.getNomLong()));
						// certains pays n'ont pas de code iso !!!
						// assertTrue("Pays[" + pays.getId()+ "].iso2 est vide ou null",StringUtils.isNotBlank(pays.getIso2()));
						// assertTrue("Pays[" + pays.getId()+ "].iso3 est vide ou null",StringUtils.isNotBlank(pays.getIso3()));
						return true;
					}
				});
	}

}
