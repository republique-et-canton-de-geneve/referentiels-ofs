package ch.ge.cti.ct.referentiels.formeJuridique.interfaces.ws;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.apache.openejb.junit.ApplicationComposer;
import org.apache.openejb.junit.EnableServices;
import org.apache.openejb.junit.Module;
import org.junit.Test;
import org.junit.runner.RunWith;

import ch.ge.cti.ct.referentiels.AbstractClientTest;
import ch.ge.cti.ct.referentiels.formeJuridique.client.ReferentielFormesJuridiquesClient;

@EnableServices(value = { "jax-ws" }, httpDebug = true)
@RunWith(ApplicationComposer.class)
public class ReferentielFormesJuridiquesClientTestIT extends AbstractClientTest {

    @Module
    public static Class<?>[] formesjuridiques() throws Exception {
	return new Class<?>[] { ReferentielFormesJuridiquesSEI.class };
    }

    @Test
    public void test() throws Exception {
	final ReferentielFormesJuridiquesWS client = ReferentielFormesJuridiquesClient.Factory
		.getClient("http://127.0.0.1:4204/formesjuridiques/ReferentielFormesJuridiquesSEI?wsdl");
	assertTrue(client.getFormesJuridiques().size() > 0);
	assertNotNull(client.getFormeJuridique(1));
    }
}
