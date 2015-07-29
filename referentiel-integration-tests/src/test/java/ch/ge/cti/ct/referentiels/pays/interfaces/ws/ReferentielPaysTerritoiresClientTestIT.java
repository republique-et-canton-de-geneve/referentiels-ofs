package ch.ge.cti.ct.referentiels.pays.interfaces.ws;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import ch.ge.cti.ct.referentiels.AbstractClientTest;
import ch.ge.cti.ct.referentiels.pays.client.ReferentielPaysTerritoiresClient;

public class ReferentielPaysTerritoiresClientTestIT extends AbstractClientTest {

    @Test
    public void test() throws Exception {
	final ReferentielPaysTerritoiresWS client = ReferentielPaysTerritoiresClient.Factory
		.getClient("http://localhost:26000/referentiels-ofs/territoires/referentiel-pays?wsdl");
	assertTrue(client.getContinents().size() > 0);
	assertTrue(client.getRegions().size() > 0);
	assertTrue(client.getPays().size() > 0);

	assertTrue(client.getPaysByContinent(1).size() > 0);
	assertTrue(client.getPaysByRegion(1).size() > 0);
	assertTrue(client.getRegionsByContinent(1).size() > 0);

	assertTrue(client.searchPays("Fr").size() > 0);
	assertTrue(client.searchPays("FR").size() > 0);
	assertTrue(client.searchPaysRegexp(".+sse").size() > 0);
	assertTrue(client.searchPaysRegexp(".+SSE").size() > 0);
	assertTrue(client.searchPaysRegexp("sse").size() > 0);

	assertNotNull(client.getContinent(1));
	assertNotNull(client.getPaysByIso2("CH"));
	assertNotNull(client.getPaysByIso3("CHE"));
	assertNotNull(client.getRegion(11));
    }
}
