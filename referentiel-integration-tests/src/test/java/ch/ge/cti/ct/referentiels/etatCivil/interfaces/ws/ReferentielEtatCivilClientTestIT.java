package ch.ge.cti.ct.referentiels.etatCivil.interfaces.ws;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import ch.ge.cti.ct.referentiels.AbstractClientTest;
import ch.ge.cti.ct.referentiels.etatCivil.client.ReferentielEtatCivilClient;

public class ReferentielEtatCivilClientTestIT extends AbstractClientTest {

    @Test
    public void test() throws Exception {
	final ReferentielEtatCivilWS client = ReferentielEtatCivilClient.Factory
		//.getClient("http://jbdev20-22:20000/referentiels-ofs/etat-civil/referentiel-etat-civil?wsdl");
	        .getClient("http://localhost:8080/referentiels-ofs/etat-civil/referentiel-etat-civil?wsdl");

	assertTrue(client.getEtatsCivils().size() > 0);

	assertNotNull(client.getEtatCivil(1));
    }
}
