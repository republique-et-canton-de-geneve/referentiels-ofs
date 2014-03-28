package ch.ge.cti.ct.referentiels;

import java.io.File;
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

	serverRoot = new File("src/test/resources/server");
	final File distributionFile = new File(serverRoot,
		"Distribution.properties");
	System.setProperty("distribution.properties",
		distributionFile.getAbsolutePath());

	DistributionFactory.setDisableJNDI(true);
    }

    @Configuration
    public Properties configure() throws Exception {
	final Properties p = new Properties();
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
	p.put("log4j.appender.console", "org.apache.log4j.ConsoleAppender");
	p.put("log4j.appender.console.layout", "org.apache.log4j.PatternLayout");
	p.put("log4j.appender.console.layout.ConversionPattern",
		"[%-5p] %c %m %n");

	return p;
    }
}
