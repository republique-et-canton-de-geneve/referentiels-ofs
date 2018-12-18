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
package ch.ge.cti.ct.referentiels.professions.service.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.databene.contiperf.PerfTest;
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
   
    public void test() throws ReferentielOfsException {
	final List<Genre> genres = ReferentielProfessionsService.INSTANCE
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
