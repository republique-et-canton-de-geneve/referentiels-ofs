package ch.ge.cti.ct.referentiels;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.Properties;

import org.apache.log4j.xml.DOMConfigurator;
import org.junit.BeforeClass;
import org.springframework.context.annotation.Configuration;

import ch.ge.cti.ct.act.configuration.DistributionFactory;

public abstract class AbstractClientTest {

    protected static File serverRoot;

    @BeforeClass
    public static void setupClass() throws Exception {
	DOMConfigurator.configure("src/test/resources/log4j.xml");

	serverRoot = new File("target/test-classes/server");
	final File distributionFile = new File(serverRoot,
		"Distribution.properties");

	final Properties props = new Properties();
	props.setProperty("referentiel.communes.file", new File(serverRoot,
		"CH1_RN+HCL_HGDE_HIST+1.0.xml").toURI().toURL().toString());
	props.setProperty("referentiel.etat-civil.file", new File(serverRoot,
		"CH1_RE+CL_MARITALSTATUS+3.0.xml").toURI().toURL().toString());
	props.setProperty("referentiel.formes-jurdiques.file", new File(
		serverRoot, "CH1_BUR+CL_LEGALFORMS+2.0.xml").toURI().toURL()
		.toString());
	props.setProperty("referentiel.pays.file", new File(serverRoot,
		"CH1_RN+HCL_COUNTRIESGEO+1.0.xml").toURI().toURL().toString());
	props.setProperty("referentiel.professions.file", new File(serverRoot,
		"CH1_BN+HCL_SBN+2.0.xml").toURI().toURL().toString());
	props.setProperty("referentiel.socioprofessionnel.file", new File(
		serverRoot, "CH1_BN+HCL_CSP+2.0.xml").toURI().toURL()
		.toString());
	OutputStream os = null;
	try {
	    os = new FileOutputStream(distributionFile);
	    props.store(os, ".Chemins mis à jour au démarrage du test.");
	} finally {
	    os.close();
	}

	System.setProperty("distribution.properties",
		distributionFile.getAbsolutePath());

	DistributionFactory.setDisableJNDI(true);
    }
}
