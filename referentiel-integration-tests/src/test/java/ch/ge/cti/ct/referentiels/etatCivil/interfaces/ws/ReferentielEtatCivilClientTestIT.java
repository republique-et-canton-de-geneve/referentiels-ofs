package ch.ge.cti.ct.referentiels.etatCivil.interfaces.ws;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.apache.openejb.junit.ApplicationComposer;
import org.apache.openejb.junit.EnableServices;
import org.apache.openejb.junit.Module;
import org.junit.Test;
import org.junit.runner.RunWith;

import ch.ge.cti.ct.referentiels.AbstractClientTest;
import ch.ge.cti.ct.referentiels.etatCivil.client.ReferentielEtatCivilClient;

@EnableServices(value = "jax-ws")
@RunWith(ApplicationComposer.class)
public class ReferentielEtatCivilClientTestIT extends AbstractClientTest {

    @Module
    public static Class<?>[] etatcivil() throws Exception {
	return new Class<?>[] { ReferentielEtatCivilSEI.class };
    }

    @Test
    public void test() throws Exception {
	final ReferentielEtatCivilWS client = ReferentielEtatCivilClient.Factory
		.getClient("http://127.0.0.1:4204/etatcivil/ReferentielEtatCivilSEI?wsdl");

	assertTrue(client.getEtatsCivils().size() > 0);

	assertNotNull(client.getEtatCivil(1));
    }
}
