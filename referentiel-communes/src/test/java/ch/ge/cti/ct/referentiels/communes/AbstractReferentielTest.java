package ch.ge.cti.ct.referentiels.communes;

import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.FileOutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import org.junit.BeforeClass;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import ch.ge.cti.ct.act.configuration.DistributionFactory;
import ch.ge.cti.ct.referentiels.communes.model.Canton;
import ch.ge.cti.ct.referentiels.communes.model.Commune;
import ch.ge.cti.ct.referentiels.communes.model.District;

public class AbstractReferentielTest {

	public static Date DATE_01011960 = new Date(-315619200000l);

	public static Date DATE_01011970 = new Date(-3600000l); // ZH: communes + 1
	public static Date DATE_15111976 = new Date(216860400000l); // ZH: communes + 1
	public static Date DATE_10031977 = new Date(226796400000l); // ZH: communes + 1

	public static Date DATE_31121978 = new Date(283910400000l);
	public static Date DATE_01011979 = new Date(283996800000l);

	public static Date DATE_30121989 = new Date(630975600000l);
	public static Date DATE_31121989 = new Date(631065600000l); // ZH: communes - 11
	public static Date DATE_01011990 = new Date(631152000000l); // ajout de communes du canton ZH

	public static Date DATE_31121996 = new Date(851990400000l);
	public static Date DATE_01011997 = new Date(852076800000l); // redécoupage des districts de AI / création du canton du Jura
	public static Date DATE_31121998 = new Date(915058800000l); // ZH: communes - 1
	public static Date DATE_01012000 = new Date(946684800000l);

	public static Date DATE_31122013 = new Date(1388444400000l);
	public static Date DATE_01012014 = new Date(1388530800000l);

	public static Date TODAY = new Date();

	@SuppressWarnings("deprecation")
	@BeforeClass
	public static void setupDistribution() throws Exception {
		final File targetDir = new File("target");
		final File distributionFile = new File(targetDir,
				"Distribution.properties");
		System.setProperty("distribution.properties", distributionFile.toURI()
				.toURL().toString());
		final Properties props = new Properties();
		final File xmlFile = new File(
				"src/test/resources/CH1_RN+HCL_HGDE_HIST+1.0.xml");
		props.setProperty("referentiel.communes.file", xmlFile.toURI().toURL()
				.toString());
		props.save(new FileOutputStream(distributionFile), "");

		DistributionFactory.setDisableJNDI(true);
	}

	protected static ApplicationContext ctxt;

	@BeforeClass
	public static void setupClass() throws Exception {
		ctxt = new ClassPathXmlApplicationContext(
				"referentiel-communes-context.xml");
	}

	// @BeforeClass
	public static void helpDateConstant() throws Exception {
		DateFormat df = new java.text.SimpleDateFormat("ddMMyyyy");
		String format = "public static Date DATE_%s = new Date(%dl);";
		String[] dates = { "31122013", "01012014" };
		for (String dt : dates) {
			System.err.println(String
					.format(format, dt, df.parse(dt).getTime()));
		}
	}

	final DateFormat df = new SimpleDateFormat("dd.MM.yy");

	protected void assertValidCommunes(final List<Commune> communes,
			final Date valid) {
		for (Commune c : communes) {
			assertTrue(
					"Test from invalide: commune["
							+ c.getId()
							+ "].validFrom="
							+ (c.getValidFrom() == null ? "" : df.format(c
									.getValidFrom())) + " / "
							+ df.format(valid), c.getValidFrom() == null
							|| c.getValidFrom().getTime() <= valid.getTime());
			assertTrue("Test to invalide: commune[" + c.getId() + "].validTo="
					+ (c.getValidTo() == null ? "" : df.format(c.getValidTo()))
					+ " / " + df.format(valid), c.getValidTo() == null
					|| c.getValidTo().getTime() >= valid.getTime());
		}
	}

	protected void assertValidDistricts(final List<District> districts,
			final Date valid) {
		for (District c : districts) {
			assertTrue(
					"Test from invalide: district["
							+ c.getId()
							+ "].validFrom="
							+ (c.getValidFrom() == null ? "" : df.format(c
									.getValidFrom())) + " / "
							+ df.format(valid), c.getValidFrom() == null
							|| c.getValidFrom().getTime() <= valid.getTime());
			assertTrue("Test to invalide: district[" + c.getId() + "].validTo="
					+ (c.getValidTo() == null ? "" : df.format(c.getValidTo()))
					+ " / " + df.format(valid), c.getValidTo() == null
					|| c.getValidTo().getTime() >= valid.getTime());
		}
	}

	protected void assertValidCantons(final List<Canton> cantons,
			final Date valid) {
		for (Canton c : cantons) {
			assertTrue(
					"Test from invalide: canton["
							+ c.getCode()
							+ "].validFrom="
							+ (c.getValidFrom() == null ? "" : df.format(c
									.getValidFrom())) + " / "
							+ df.format(valid), c.getValidFrom() == null
							|| c.getValidFrom().getTime() <= valid.getTime());
			assertTrue("Test to invalide: canton[" + c.getCode() + "].validTo="
					+ (c.getValidTo() == null ? "" : df.format(c.getValidTo()))
					+ " / " + df.format(valid), c.getValidTo() == null
					|| c.getValidTo().getTime() >= valid.getTime());
		}
	}
}
