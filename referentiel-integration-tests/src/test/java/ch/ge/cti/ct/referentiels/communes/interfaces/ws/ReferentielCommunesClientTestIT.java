package ch.ge.cti.ct.referentiels.communes.interfaces.ws;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.Date;

import org.apache.openejb.junit.ApplicationComposer;
import org.apache.openejb.junit.EnableServices;
import org.apache.openejb.junit.Module;
import org.junit.Test;
import org.junit.runner.RunWith;

import ch.ge.cti.ct.referentiels.AbstractClientTest;
import ch.ge.cti.ct.referentiels.communes.client.ReferentielCommunesClient;

/**
 * @see http://tomee.apache.org/examples-trunk/application-composer/README.html
 * @see http://tomee.apache.org/examples-trunk/simple-webservice/README.html
 * @see http
 *      ://rmannibucau.wordpress.com/2012/11/13/openejb-application-composer
 *      -new-features-jaxrs-jaxws-classes/
 */
@EnableServices(value = "jax-ws")
@RunWith(ApplicationComposer.class)
public class ReferentielCommunesClientTestIT extends AbstractClientTest {

    @Module
    public static Class<?>[] communes() throws Exception {
	return new Class<?>[] { ReferentielCommunesSEI.class };
    }

    @Test
    public void test() throws Exception {
	final ReferentielCommunesWS client = ReferentielCommunesClient.Factory
		.getClient("http://127.0.0.1:4204/communes/ReferentielCommunesSEI?wsdl");

	assertTrue(client.getCantons().size() > 0);

	assertTrue(client.getCantons().size() > 0);
	assertTrue(client.getCantonsDate(new Date()).size() > 0);
	assertTrue(client.getDistrictsByCanton("GE").size() > 0);
	assertTrue(client.getDistrictsByCantonDate("GE", new Date()).size() > 0);

	assertTrue(client.getCommunesByCanton("GE").size() > 0);
	assertTrue(client.getCommunesByCantonDate("GE", new Date()).size() > 0);
	assertTrue(client.getCommunesByDistrict(101).size() > 0);
	assertTrue(client.getCommunesByDistrictDate(101, new Date()).size() > 0);

	assertTrue(client.searchCommune("Gen").size() > 0);
	assertTrue(client.searchCommuneDate("Gen", new Date()).size() > 0);

	assertNotNull(client.getCanton("GE"));
	assertNotNull(client.getCantonDate("GE", new Date()));
	assertNotNull(client.getDistrict(101));
	assertNotNull(client.getDistrictDate(101, new Date()));
	assertNotNull(client.getCommune(1001));
    }

}
