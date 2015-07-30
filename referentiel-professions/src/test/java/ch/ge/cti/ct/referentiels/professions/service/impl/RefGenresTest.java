package ch.ge.cti.ct.referentiels.professions.service.impl;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.databene.contiperf.PerfTest;
import org.databene.contiperf.Required;
import org.junit.Test;

import ch.ge.cti.ct.referentiels.ofs.ReferentielOfsException;
import ch.ge.cti.ct.referentiels.professions.model.Genre;

@PerfTest(invocations = 1000, threads = 20)
public class RefGenresTest extends AbstractRefTest {

    @Test
   
    public void test() throws ReferentielOfsException {
	final List<Genre> genres = ReferentielProfessionsService.instance
		.getGenres();
	assertEquals("La liste des genres est incorrecte", 383, genres.size());
	for (final Genre genre : genres) {
	    assertGenre("Référentiel: ", genre);
	}
    }
}
