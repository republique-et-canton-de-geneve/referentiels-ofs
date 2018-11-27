package ch.ge.cti.ct.referentiels.professions.service.impl;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.apache.commons.lang.StringUtils;
import org.databene.contiperf.junit.ContiPerfRule;
import org.junit.BeforeClass;
import org.junit.Rule;

import ch.ge.cti.ct.referentiels.ofs.ReferentielOfsException;
import ch.ge.cti.ct.referentiels.professions.AbstractReferentielTest;
import ch.ge.cti.ct.referentiels.professions.model.Classe;
import ch.ge.cti.ct.referentiels.professions.model.Division;
import ch.ge.cti.ct.referentiels.professions.model.Genre;
import ch.ge.cti.ct.referentiels.professions.model.Groupe;

public abstract class AbstractRefTest extends AbstractReferentielTest {

    @Rule
    public ContiPerfRule rule = new ContiPerfRule();

    @BeforeClass
    public static void load() throws ReferentielOfsException {
	ReferentielProfessionsService.INSTANCE.getReferentiel();
    }

    public void assertDivision(final String message, final Division division) {
	assertNotNull(message + " division est null", division);
	assertTrue(message + ": division.id est incorrect",
		division.getId() > 0);
	assertTrue(message + ": division.nom est incorrect",
		StringUtils.isNotBlank(division.getNom()));
	assertTrue(message + ": division.classe est incorrect", division
		.getClasse().size() > 0);
	for (final Classe classe : division.getClasse()) {
	    assertClasse(message, classe);
	}
    }

    public void assertClasse(final String message, final Classe classe) {
	assertNotNull(message + " classe est null", classe);
	assertTrue(message + ": classe.id est incorrect", classe.getId() > 0);
	assertTrue(message + ": classe.nom est incorrect",
		StringUtils.isNotBlank(classe.getNom()));
	assertTrue(message + ": classe.divisionId est incorrect",
		classe.getDivisionId() > 0);
	assertTrue(message + ": classe.groupe est incorrect", classe
		.getGroupe().size() > 0);
	for (final Groupe groupe : classe.getGroupe()) {
	    assertGroupe(message, groupe);
	}
    }

    public void assertGroupe(final String message, final Groupe groupe) {
	assertNotNull(message + " groupe est null", groupe);
	assertTrue(message + ": groupe.id est incorrect", groupe.getId() > 0);
	assertTrue(message + ": groupe.nom est incorrect",
		StringUtils.isNotBlank(groupe.getNom()));
	assertTrue(message + ": groupe.divisionId est incorrect",
		groupe.getDivisionId() > 0);
	assertTrue(message + ": groupe.classeId est incorrect",
		groupe.getClasseId() > 0);
	assertTrue(message + ": groupe.genre est incorrect", groupe.getGenre()
		.size() > 0);
	for (final Genre genre : groupe.getGenre()) {
	    assertGenre(message, genre);
	}
    }

    public void assertGenre(final String message, final Genre genre) {
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
