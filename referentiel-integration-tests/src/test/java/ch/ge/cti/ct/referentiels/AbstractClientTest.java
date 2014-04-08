package ch.ge.cti.ct.referentiels;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.Properties;

import org.apache.log4j.xml.DOMConfigurator;
import org.apache.openejb.junit.Configuration;
import org.junit.BeforeClass;

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

    @Configuration
    public Properties configure() throws Exception {
	final Properties p = new Properties();
	p.put("log4j.appender.console", "org.apache.log4j.ConsoleAppender");
	p.put("log4j.appender.console.layout", "org.apache.log4j.PatternLayout");
	p.put("log4j.appender.console.layout.ConversionPattern",
		"[%-5p] %c %m %n");
	p.put("log4j.rootLogger", "info,console");
	p.put("log4j.category.OpenEJB", "warn");
	p.put("log4j.category.OpenEJB.server", "warn");
	p.put("log4j.category.OpenEJB.startup.config", "warn");
	p.put("log4j.category.org.apache.webbeans", "warn");
	p.put("log4j.category.OpenEJB.cdi", "warn");
	p.put("log4j.category.org.apache.cxf.service.factory.ReflectionServiceFactoryBean",
		"info");
	p.put("log4j.category.org.apache.cxf", "warn");
	p.put("log4j.category.org.springframework", "warn");
	p.put("log4j.category.org.sdmxsource", "warn");

	return p;
    }
}
