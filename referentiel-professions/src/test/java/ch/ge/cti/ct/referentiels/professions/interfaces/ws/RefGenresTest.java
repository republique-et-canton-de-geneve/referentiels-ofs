package ch.ge.cti.ct.referentiels.professions.interfaces.ws;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.databene.contiperf.PerfTest;
import org.databene.contiperf.Required;
import org.junit.Test;

import ch.ge.cti.ct.referentiels.ofs.ReferentielOfsException;
import ch.ge.cti.ct.referentiels.professions.interfaces.ws.model.GenreWS;

@PerfTest(invocations = 1000, threads = 20)
public class RefGenresTest extends AbstractRefWSTest {

    @Test
    @Required(percentile90 = 10, percentile95 = 30)
    public void test() throws ReferentielOfsException {
	final List<GenreWS> genres = getWS().getGenres();
	assertEquals("La liste des genres est incorrecte", 383, genres.size());
	for (final GenreWS genre : genres) {
	    assertGenre("Référentiel: ", genre);
	}
    }
}
