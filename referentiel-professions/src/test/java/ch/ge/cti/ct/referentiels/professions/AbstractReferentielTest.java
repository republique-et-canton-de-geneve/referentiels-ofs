package ch.ge.cti.ct.referentiels.professions;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.Properties;

import org.junit.BeforeClass;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import ch.ge.cti.ct.act.configuration.DistributionFactory;

public class AbstractReferentielTest {

    @BeforeClass
    public static void setupDistribution() throws Exception {
	final File targetDir = new File("target");
	final File distributionFile = new File(targetDir,
		"Distribution.properties");
	System.setProperty("distribution.properties", distributionFile.toURI()
		.toURL().toString());
	final Properties props = new Properties();
	final File xmlFile = new File(
		"src/test/resources/CH1_BN+HCL_SBN+2.0.xml");
	props.setProperty("referentiel.professions.file", xmlFile.toURI()
		.toURL().toString());
	OutputStream os = null;
	try {
	    os = new FileOutputStream(distributionFile);
	    props.store(os, "");
	} finally {
	    os.close();
	}

	DistributionFactory.setDisableJNDI(true);
    }

    protected static ApplicationContext ctxt;

    @BeforeClass
    public static void setupClass() throws Exception {
	ctxt = new ClassPathXmlApplicationContext(
		"referentiel-professions-context.xml");
    }
}
