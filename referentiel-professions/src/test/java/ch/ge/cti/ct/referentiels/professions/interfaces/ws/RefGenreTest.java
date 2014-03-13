package ch.ge.cti.ct.referentiels.professions.interfaces.ws;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.util.Arrays;
import java.util.Collection;

import org.databene.contiperf.PerfTest;
import org.databene.contiperf.Required;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import ch.ge.cti.ct.referentiels.ofs.ReferentielOfsException;
import ch.ge.cti.ct.referentiels.professions.interfaces.ws.model.GenreWS;

@RunWith(value = Parameterized.class)
@PerfTest(invocations = 1000, threads = 20)
public class RefGenreTest extends AbstractRefWSTest {

    private final int genreId;
    private final String genreNom;

    public RefGenreTest(final int genreId, final String genreNom) {
	this.genreId = genreId;
	this.genreNom = genreNom;
    }

    @Parameters
    public static Collection<Object[]> configure() {
	return Arrays.asList(new Object[][] {
	/* 0 */{ -1, null },
	/* 1 */{ 0, null },
	/* 2 */{ 11101, "Agriculteurs" },
	/* 3 */{ 21101, "Fromagers et laitiers" },
	/* 4 */{ 31101, "Architectes" },
	/* 5 */{ 41101, "Maçons" },
	/* 6 */{ 51101, "Acheteurs" },
	/* 7 */{ 61101, "Hôteliers, directeurs de restaurants" },
	/* 8 */{ 71101, "Entrepreneurs, directeurs" },
	/* 9 */{ 81101, "Journalistes, rédacteurs" },
	/* 10 */{ 91106, "Professions du secteur tertiaire spa" },
	/* 11 */{ 99, null }
	/* */
	});
    }

    @Test
    @Required(percentile90 = 2, percentile95 = 5)
    public void test() throws ReferentielOfsException {
	final GenreWS genre = getWS().getGenre(genreId);
	if (genreNom == null) {
	    assertNull("GenreWS [" + genreId + "] est incorrect", genre);
	} else {
	    assertNotNull("GenreWS [" + genreId + "] est incorrect", genre);
	    assertEquals("GenreWS [" + genreId + "].id est incorrect", genreId,
		    genre.getId());
	    assertEquals("GenreWS [" + genreId + "].nom est incorrect",
		    genreNom, genre.getNom());
	}
    }
}
