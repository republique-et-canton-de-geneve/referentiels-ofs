package ch.ge.cti.ct.referentiels.professions.interfaces.ws;

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
import ch.ge.cti.ct.referentiels.professions.interfaces.ws.model.GenreWS;

@RunWith(value = Parameterized.class)
@PerfTest(invocations = 1000, threads = 20)
public class RefGenresGroupeTest extends AbstractRefWSTest {

    private final int groupId;
    private final int countGenres;

    public RefGenresGroupeTest(final int groupId, final int countGenres) {
	this.groupId = groupId;
	this.countGenres = countGenres;
    }

    @Parameters
    public static Collection<Object[]> configure() {
	return Arrays.asList(new Object[][] {
	/* 0 */{ -1, 0 },
	/* 1 */{ 0, 0 },
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
    @Required(percentile90 = 1, percentile95 = 1)
    public void test() throws ReferentielOfsException {
	final List<GenreWS> genres = getWS().getGenresByGroup(groupId);
	if (countGenres == -1) {
	    assertNull("Genres [" + groupId + "] est incorrect", genres);
	} else {
	    assertNotNull("Genres [" + groupId + "] est incorrect", genres);
	    assertEquals("Genres [" + groupId + "].size est incorrect",
		    countGenres, genres.size());
	    for (final GenreWS genre : genres) {
		assertEquals("Genres [" + groupId + "].groupId est null",
			groupId, genre.getGroupeId());
	    }
	}
    }
}
