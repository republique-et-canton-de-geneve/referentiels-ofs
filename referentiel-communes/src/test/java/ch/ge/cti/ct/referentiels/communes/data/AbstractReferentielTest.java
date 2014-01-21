package ch.ge.cti.ct.referentiels.communes.data;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Properties;

import org.junit.BeforeClass;

import ch.ge.cti.ct.act.configuration.DistributionFactory;

public class AbstractReferentielTest {

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

}
