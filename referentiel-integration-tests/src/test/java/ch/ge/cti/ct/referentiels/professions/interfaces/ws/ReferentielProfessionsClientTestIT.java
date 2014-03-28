package ch.ge.cti.ct.referentiels.professions.interfaces.ws;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.apache.openejb.junit.ApplicationComposer;
import org.apache.openejb.junit.EnableServices;
import org.apache.openejb.junit.Module;
import org.junit.Test;
import org.junit.runner.RunWith;

import ch.ge.cti.ct.referentiels.AbstractClientTest;
import ch.ge.cti.ct.referentiels.professions.client.ReferentielProfessionsClient;

@EnableServices(value = { "jax-ws" }, httpDebug = true)
@RunWith(ApplicationComposer.class)
public class ReferentielProfessionsClientTestIT extends AbstractClientTest {

    @Module
    public static Class<?>[] professions() throws Exception {
	return new Class<?>[] { ReferentielProfessionsSEI.class };
    }

    @Test
    public void test() throws Exception {
	final ReferentielProfessionsWS client = ReferentielProfessionsClient.Factory
		.getClient("http://127.0.0.1:4204/professions/ReferentielProfessionsSEI?wsdl");
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
