package ch.ge.cti.ct.referentiels.socioprofessionnel.interfaces.ws;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.apache.openejb.junit.ApplicationComposer;
import org.apache.openejb.junit.EnableServices;
import org.apache.openejb.junit.Module;
import org.junit.Test;
import org.junit.runner.RunWith;

import ch.ge.cti.ct.referentiels.AbstractClientTest;
import ch.ge.cti.ct.referentiels.socioprofessionnel.client.ReferentielSocioprofessionnelClient;

@EnableServices(value = { "jax-ws" }, httpDebug = true)
@RunWith(ApplicationComposer.class)
public class ReferentielSocioprofessionnelClientTestIT extends
	AbstractClientTest {

    @Module
    public static Class<?>[] socioprofessionnel() throws Exception {
	return new Class<?>[] { ReferentielSocioprofessionnelSEI.class };
    }

    @Test
    public void test() throws Exception {
	final ReferentielSocioprofessionnelWS client = ReferentielSocioprofessionnelClient.Factory
		.getClient("http://127.0.0.1:4204/socioprofessionnel/ReferentielSocioprofessionnelSEI?wsdl");

	assertTrue(client.getNiveaux1().size() > 0);
	assertTrue(client.searchNiveaux1("Dirigeant").size() > 0);
	assertTrue(client.searchNiveaux1Regexp("^Dirigeant").size() > 0);

	assertTrue(client.getNiveaux2().size() > 0);
	assertTrue(client.getNiveaux2ByNiveau1(20).size() > 0);
	assertTrue(client.searchNiveaux2("Dirigeant").size() > 0);
	assertTrue(client.searchNiveaux2Regexp("^Dirigeant").size() > 0);

	assertNotNull(client.getNiveau1(20));
	assertNotNull(client.getNiveau2(200));
	assertNotNull(client.getNiveaux2ByNiveau1(20));
    }
}
