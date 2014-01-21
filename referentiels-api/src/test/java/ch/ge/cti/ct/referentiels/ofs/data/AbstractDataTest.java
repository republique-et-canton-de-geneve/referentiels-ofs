package ch.ge.cti.ct.referentiels.ofs.data;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Properties;

import org.junit.BeforeClass;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import ch.ge.cti.ct.act.configuration.DistributionFactory;

public class AbstractDataTest {

	@SuppressWarnings("deprecation")
	@BeforeClass
	public static void setupDistribution() throws Exception {
		final File targetDir = new File("target");
		if (!targetDir.exists()) {
			targetDir.mkdir();
		}
		final File distributionFile = new File(targetDir,
				"Distribution.properties");
		System.setProperty("distribution.properties", distributionFile.toURI()
				.toURL().toString());
		final Properties props = new Properties();
		final File xmlFile = new File("src/test/resources/test.xml");
		props.setProperty("referentiel.test.file", xmlFile.toURI().toURL()
				.toString());
		props.save(new FileOutputStream(distributionFile), "");

		DistributionFactory.setDisableJNDI(true);
	}

	protected static ApplicationContext ctxt;

	@BeforeClass
	public static void setupClass() throws Exception {
		ctxt = new ClassPathXmlApplicationContext(
				"referentiels-ofs-context.xml");
	}
}
