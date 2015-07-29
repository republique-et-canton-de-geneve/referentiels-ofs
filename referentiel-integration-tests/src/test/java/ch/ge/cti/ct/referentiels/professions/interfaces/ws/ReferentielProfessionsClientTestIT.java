package ch.ge.cti.ct.referentiels.professions.interfaces.ws;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import ch.ge.cti.ct.referentiels.AbstractClientTest;
import ch.ge.cti.ct.referentiels.professions.client.ReferentielProfessionsClient;

public class ReferentielProfessionsClientTestIT extends AbstractClientTest {

    @Test
    public void test() throws Exception {
	final ReferentielProfessionsWS client = ReferentielProfessionsClient.Factory
		.getClient("http://fs-282892:26000/referentiels-ofs/professions/referentiel-professions?wsdl");
	assertTrue(client.getClasses().size() > 0);
	assertTrue(client.getDivisions().size() > 0);
	assertTrue(client.getGenres().size() > 0);

	assertTrue(client.getClassesByDivision(1).size() > 0);
	assertTrue(client.getGenresByGroup(111).size() > 0);
	assertTrue(client.getGroupesByClasse(11).size() > 0);

	assertTrue(client.searchClasse("Ingénieur").size() > 0);
	assertTrue(client.searchClasseRegexp("génieur").size() > 0);
	assertTrue(client.searchGenre("Ingénieur").size() > 0);
	assertTrue(client.searchGenreRegexp("génieur").size() > 0);
	assertTrue(client.searchGroupe("Ingénieur").size() > 0);
	assertTrue(client.searchGroupeRegexp("génieur").size() > 0);
	assertTrue(client.searchDivision("Professions").size() > 0);
	assertTrue(client.searchDivisionRegexp("rofession").size() > 0);

	assertNotNull(client.getClasse(11));
	assertNotNull(client.getDivision(1));
	assertNotNull(client.getGenre(11101));
	assertNotNull(client.getGroupe(111));
    }
}
