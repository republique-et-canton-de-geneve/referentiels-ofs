/*
 * Referentiels OFS
 *
 * Copyright (C) 2018 République et canton de Genève
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
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
