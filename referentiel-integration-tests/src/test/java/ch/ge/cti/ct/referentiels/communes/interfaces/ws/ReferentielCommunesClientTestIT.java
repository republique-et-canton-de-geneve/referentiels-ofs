package ch.ge.cti.ct.referentiels.communes.interfaces.ws;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.Date;

import org.junit.Test;

import ch.ge.cti.ct.referentiels.AbstractClientTest;
import ch.ge.cti.ct.referentiels.communes.client.ReferentielCommunesClient;

public class ReferentielCommunesClientTestIT extends AbstractClientTest {

    @Test
    public void test() throws Exception {
	final ReferentielCommunesWS client = ReferentielCommunesClient.Factory
		.getClient(getContextRoot()
			+ "/communes/referentiel-communes?wsdl");
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
