package ch.ge.cti.ct.referentiels.professions.interfaces.ws;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.apache.commons.lang.StringUtils;
import org.databene.contiperf.junit.ContiPerfRule;
import org.junit.BeforeClass;
import org.junit.Rule;

import ch.ge.cti.ct.referentiels.ofs.ReferentielOfsException;
import ch.ge.cti.ct.referentiels.professions.AbstractReferentielTest;
import ch.ge.cti.ct.referentiels.professions.interfaces.ws.model.ClasseWS;
import ch.ge.cti.ct.referentiels.professions.interfaces.ws.model.DivisionWS;
import ch.ge.cti.ct.referentiels.professions.interfaces.ws.model.GenreWS;
import ch.ge.cti.ct.referentiels.professions.interfaces.ws.model.GroupeWS;

public class AbstractRefWSTest extends AbstractReferentielTest {

    @Rule
    public ContiPerfRule rule = new ContiPerfRule();

    @BeforeClass
    public static void load() throws ReferentielOfsException {
	// chargement du fichier SDMX
	new ReferentielProfessionsSEI().searchGenre("Professions");
    }

    private ReferentielProfessionsWS ws = null;

    protected ReferentielProfessionsWS getWS() throws ReferentielOfsException {
	if (ws == null) {
	    ws = new ReferentielProfessionsSEI();
	}
	return ws;
    }

    public void assertDivision(final String message, final DivisionWS division) {
	assertNotNull(message + " division est null", division);
	assertTrue(message + ": division.id est incorrect",
		division.getId() > 0);
	assertTrue(message + ": division.nom est incorrect",
		StringUtils.isNotBlank(division.getNom()));
    }

    public void assertClasse(final String message, final ClasseWS classe) {
	assertNotNull(message + " classe est null", classe);
	assertTrue(message + ": classe.id est incorrect", classe.getId() > 0);
	assertTrue(message + ": classe.nom est incorrect",
		StringUtils.isNotBlank(classe.getNom()));
	assertTrue(message + ": classe.divisionId est incorrect",
		classe.getDivisionId() > 0);
    }

    public void assertGroupe(final String message, final GroupeWS groupe) {
	assertNotNull(message + " groupe est null", groupe);
	assertTrue(message + ": groupe.id est incorrect", groupe.getId() > 0);
	assertTrue(message + ": groupe.nom est incorrect",
		StringUtils.isNotBlank(groupe.getNom()));
	assertTrue(message + ": groupe.divisionId est incorrect",
		groupe.getDivisionId() > 0);
	assertTrue(message + ": groupe.classeId est incorrect",
		groupe.getClasseId() > 0);
    }

    public void assertGenre(final String message, final GenreWS genre) {
	assertNotNull(message + " genre est null", genre);
	assertTrue(message + ": genre.id est incorrect", genre.getId() > 0);
	assertTrue(message + ": genre.nom est incorrect",
		StringUtils.isNotBlank(genre.getNom()));
	assertTrue(message + ": genre.divisionId est incorrect",
		genre.getDivisionId() > 0);
	assertTrue(message + ": genre.classeId est incorrect",
		genre.getClasseId() > 0);
	assertTrue(message + ": genre.groupeId est incorrect",
		genre.getGroupeId() > 0);
    }
}
