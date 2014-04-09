package ch.ge.cti.ct.referentiels.etatCivil;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.Properties;

import org.junit.BeforeClass;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import ch.ge.cti.ct.act.configuration.DistributionFactory;

public class AbstractReferentielTest {
    public static final String SDMX_FILE = "CH1_RE+CL_MARITALSTATUS+3.0.xml";

    @BeforeClass
    public static void setupDistribution() throws Exception {
	final File targetDir = new File("target/test-classes");
	final File distributionFile = new File(targetDir,
		"Distribution.properties");
	System.setProperty("distribution.properties", distributionFile.toURI()
		.toURL().toString());
	final Properties props = new Properties();
	final File xmlFile = new File("src/test/resources/" + SDMX_FILE);
	props.setProperty("referentiel.etat-civil.file", xmlFile.toURI()
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
		"referentiel-etat-civil-context.xml");
    }
}
