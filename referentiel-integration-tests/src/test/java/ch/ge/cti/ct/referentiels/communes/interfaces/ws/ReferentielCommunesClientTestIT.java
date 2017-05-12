package ch.ge.cti.ct.referentiels.communes.interfaces.ws;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.Date;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import ch.ge.cti.ct.referentiels.AbstractClientTest;
import ch.ge.cti.ct.referentiels.communes.client.ReferentielCommunesClient;
import ch.ge.cti.ct.referentiels.communes.interfaces.ws.model.CantonWS;
import ch.ge.cti.ct.referentiels.communes.interfaces.ws.model.CommuneWS;
import ch.ge.cti.ct.referentiels.ofs.ReferentielOfsException;

/**
 * @see http://tomee.apache.org/examples-trunk/application-composer/README.html
 * @see http://tomee.apache.org/examples-trunk/simple-webservice/README.html
 * @see http
 *      ://rmannibucau.wordpress.com/2012/11/13/openejb-application-composer
 *      -new-features-jaxrs-jaxws-classes/
 */
public class ReferentielCommunesClientTestIT extends AbstractClientTest {

	private static final Date NOW = new Date();
	final ReferentielCommunesWS client = ReferentielCommunesClient.Factory
		.getClient("http://localhost:26000/referentiels-ofs/communes/referentiel-communes?wsdl");

	public ReferentielCommunesClientTestIT() throws Exception{
	    
	}
	
    @Test
    public void test() throws Exception {

	assertTrue(client.getCantons().size() > 0);

	assertTrue(client.getCantons().size() > 0);
	assertTrue(client.getCantonsDate(NOW).size() > 0);
	assertTrue(client.getDistrictsByCanton("GE").size() > 0);
	assertTrue(client.getDistrictsByCantonDate("GE", NOW).size() > 0);

	assertTrue(client.getCommunesByCanton("GE").size() > 0);
	assertTrue(client.getCommunesByCantonDate("GE", NOW).size() > 0);
	assertTrue(client.getCommunesByDistrict(101).size() > 0);
	assertTrue(client.getCommunesByDistrictDate(101, NOW).size() > 0);

	assertTrue(client.searchCommune("Gen").size() > 0);
	assertTrue(client.searchCommuneDate("Gen", NOW).size() > 0);

	assertTrue(client.searchCommuneRegexp("^gen[e]+").size() > 0);
	assertTrue(client.searchCommuneDateRegexp("^gen[e]+", NOW)
		.size() > 0);

	assertNotNull(client.getCanton("GE"));
	assertNotNull(client.getCantonDate("GE", NOW));
	assertNotNull(client.getDistrict(101));
	assertNotNull(client.getDistrictDate(101, NOW));
	assertNotNull(client.getCommune(1001));
    }
    
    /**
     * Vérifie que les communes historiques délivrent plus de communes que la liste des actuelles
     * @throws ReferentielOfsException 
     */
    @Test
    public void testCommunesActuelles() throws ReferentielOfsException{
	List<CantonWS> cantons = client.getCantons();
	boolean ancienneCommune = false;
	for (CantonWS canton : cantons) {
	    String codeCanton = canton.getCode();
	    List<CommuneWS> communes = client.getCommunesByCanton(codeCanton);
	    for (CommuneWS commune : communes) {
		Date validTo = commune.getValidTo();
		    ancienneCommune |= (validTo != null);
	    }
	}
	Assert.assertTrue("Aucune commune recue n'a de date de validité expirée", ancienneCommune);
    }
    
    /**
     * Vérifie que les communes historiques délivrent plus de communes que la liste des actuelles
     * @throws ReferentielOfsException 
     */
    @Test
    public void testCommunesHistoriques() throws ReferentielOfsException{
	List<CantonWS> cantons = client.getCantons();
	boolean ancienneCommune = false;
	for (CantonWS canton : cantons) {
	    String codeCanton = canton.getCode();
	    List<CommuneWS> communesHistoriques = client.getCommunesHistoriquesByCanton(codeCanton);
	    for (CommuneWS commune : communesHistoriques) {
		Date validTo = commune.getValidTo();
		if(validTo != null){
		    ancienneCommune |= validTo.before(NOW);
		}
	    }
	}
	Assert.assertTrue("Aucune commune recue n'a de date de validité expirée", ancienneCommune);
    }

}
