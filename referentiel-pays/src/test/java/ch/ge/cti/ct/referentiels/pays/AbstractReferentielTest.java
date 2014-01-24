package ch.ge.cti.ct.referentiels.pays;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Date;
import java.util.Properties;

import org.junit.BeforeClass;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import ch.ge.cti.ct.act.configuration.DistributionFactory;

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
				"src/test/resources/CH1_RN+HCL_COUNTRIESGEO+1.0.xml");
		props.setProperty("referentiel.pays.file", xmlFile.toURI().toURL()
				.toString());
		props.save(new FileOutputStream(distributionFile), "");

		DistributionFactory.setDisableJNDI(true);
	}

	protected static ApplicationContext ctxt;

	@BeforeClass
	public static void setupClass() throws Exception {
		ctxt = new ClassPathXmlApplicationContext(
				"referentiel-pays-context.xml");
	}
}
