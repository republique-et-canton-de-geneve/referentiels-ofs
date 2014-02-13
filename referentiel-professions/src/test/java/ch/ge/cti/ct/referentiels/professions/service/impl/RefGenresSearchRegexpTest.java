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
public class RefGenresSearchRegexpTest extends AbstractRefTest {

    private final String searchString;
    private final int countGenres;

    public RefGenresSearchRegexpTest(final String searchString,
	    final int countGenres) {
	this.searchString = searchString;
	this.countGenres = countGenres;
    }

    @Parameters
    public static Collection<Object[]> configure() {
	return Arrays.asList(new Object[][] {
	/* 0 */{ null, -1 },
	/* 1 */{ "", -1 },
	/* 2 */{ "rofessions", 66 },
	/* 3 */{ "rofessions", 66 },
	/* 4 */{ "ng�nieurs", 11 },
	/* 5 */{ "ngenieurs", 11 },
	/* 6 */{ "xxxxxxxx", 0 }
	/* */
	});
    }

    @Test
    @Required(percentile90 = 25, percentile95 = 100)
    public void test() throws ReferentielOfsException {
	final List<Genre> genres = ReferentielProfessionsService.instance
		.searchGenreRegexp(searchString);
	if (countGenres == -1) {
	    assertNull("Genres [" + searchString + "] est incorrect", genres);
	} else {
	    assertNotNull("Genres [" + searchString + "] est incorrect", genres);
	    assertEquals("Genres [" + searchString + "].size est incorrect",
		    countGenres, genres.size());
	}
    }
}