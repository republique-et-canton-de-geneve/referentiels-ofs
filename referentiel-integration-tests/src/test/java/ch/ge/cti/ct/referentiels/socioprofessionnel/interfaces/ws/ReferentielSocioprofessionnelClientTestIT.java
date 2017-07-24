package ch.ge.cti.ct.referentiels.socioprofessionnel.interfaces.ws;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import ch.ge.cti.ct.referentiels.AbstractClientTest;
import ch.ge.cti.ct.referentiels.socioprofessionnel.client.ReferentielSocioprofessionnelClient;

public class ReferentielSocioprofessionnelClientTestIT extends
	AbstractClientTest {

    @Test
    public void test() throws Exception {
	final ReferentielSocioprofessionnelWS client = ReferentielSocioprofessionnelClient.Factory
		//.getClient("http://jbdev20-22:20000/referentiels-ofs/socioprofessionnel/referentiel-socioprofessionnel?wsdl");
	        .getClient("http://localhost:8080/referentiels-ofs/socioprofessionnel/referentiel-socioprofessionnel?wsdl");

	assertTrue(client.getNiveaux1().size() > 0);
	assertTrue(client.searchNiveaux1("Dirigeant").size() > 0);
	assertTrue(client.searchNiveaux1Regexp("^Dirigeant").size() > 0);

	assertTrue(client.getNiveaux2().size() > 0);
	assertTrue(client.getNiveaux2ByNiveau1(20).size() > 0);
	assertTrue(client.searchNiveaux2("Dirigeant").size() > 0);
	assertTrue(client.searchNiveaux2Regexp("^Dirigeant").size() > 0);

	assertNotNull(client.getNiveau1(20));
	assertNotNull(client.getNiveau2(201));
	assertNotNull(client.getNiveaux2ByNiveau1(20));
    }
}
