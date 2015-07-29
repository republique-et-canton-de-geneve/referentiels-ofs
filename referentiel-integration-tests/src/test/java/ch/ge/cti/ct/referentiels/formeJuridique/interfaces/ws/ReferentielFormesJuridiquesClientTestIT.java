package ch.ge.cti.ct.referentiels.formeJuridique.interfaces.ws;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import ch.ge.cti.ct.referentiels.AbstractClientTest;
import ch.ge.cti.ct.referentiels.formeJuridique.client.ReferentielFormesJuridiquesClient;

public class ReferentielFormesJuridiquesClientTestIT extends AbstractClientTest {

    @Test
    public void test() throws Exception {
	final ReferentielFormesJuridiquesWS client = ReferentielFormesJuridiquesClient.Factory
		.getClient("http://localhost:26000/referentiels-ofs/formes-juridiques/referentiel-formes-juridiques?wsdl");
	assertTrue(client.getFormesJuridiques().size() > 0);
	assertNotNull(client.getFormeJuridique(1));
    }
}
