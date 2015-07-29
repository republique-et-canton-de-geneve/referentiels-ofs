package ch.ge.cti.ct.referentiels.ofs.data;

import org.junit.BeforeClass;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class AbstractDataTest {

	protected static ApplicationContext ctxt;

	@BeforeClass
	public static void setupClass() throws Exception {
		ctxt = new ClassPathXmlApplicationContext(
				"referentiels-ofs-context.xml");
	}
}
