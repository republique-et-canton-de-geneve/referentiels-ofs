package ch.ge.cti.ct.referentiels;

import java.io.File;
import java.net.MalformedURLException;

import javax.xml.parsers.FactoryConfigurationError;

import org.apache.log4j.xml.DOMConfigurator;
import org.junit.Before;

public abstract class AbstractClientTest {

    @Before
    public void setup() throws MalformedURLException, FactoryConfigurationError {
	DOMConfigurator.configure(new File("src/test/resources/log4j.xml")
		.toURI().toURL());
    }
}
