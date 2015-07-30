package ch.ge.cti.ct.referentiels.professions.service.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.databene.contiperf.PerfTest;
import org.databene.contiperf.Required;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import ch.ge.cti.ct.referentiels.ofs.ReferentielOfsException;
import ch.ge.cti.ct.referentiels.professions.model.Genre;

@RunWith(value = Parameterized.class)
@PerfTest(invocations = 1000, threads = 20)
public class RefGenresGroupeTest extends AbstractRefTest {

    private final int genreId;
    private final int countGenres;

    public RefGenresGroupeTest(final int genreId, final int countGenres) {
	this.genreId = genreId;
	this.countGenres = countGenres;
    }

    @Parameters
    public static Collection<Object[]> configure() {
	return Arrays.asList(new Object[][] {
	/* 0 */{ -1, -1 },
	/* 1 */{ 0, -1 },
	/* 2 */{ 111, 3 },
	/* 3 */{ 211, 5 },
	/* 4 */{ 311, 13 },
	/* 5 */{ 411, 8 },
	/* 6 */{ 511, 9 },
	/* 7 */{ 611, 6 },
	/* 8 */{ 711, 5 },
	/* 9 */{ 811, 4 },
	/* 10 */{ 911, 3 },
	/* 11 */{ 99, 0 }
	/* */
	});
    }

    @Test
   
    public void test() throws ReferentielOfsException {
	final List<Genre> genres = ReferentielProfessionsService.instance
		.getGenres(genreId);
	if (countGenres == -1) {
	    assertNull("Genres [" + genreId + "] est incorrect", genres);
	} else {
	    assertNotNull("Genres [" + genreId + "] est incorrect", genres);
	    assertEquals("Genres [" + genreId + "].size est incorrect",
		    countGenres, genres.size());
	    for (final Genre genre : genres) {
		assertEquals("Genres [" + genreId + "].genreId est null",
			genreId, genre.getGroupeId());
	    }
	}
    }
}
