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
package ch.ge.cti.ct.referentiels.ofs;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.io.IOException;

import org.junit.Test;

public class ReferentielOfsExceptionTest {

	@Test
	public void testReferentielOfsException() {
		final ReferentielOfsException roe = new ReferentielOfsException();
		assertNull("ReferentielOfsException.message est incorrect",
				roe.getMessage());
		assertNull("ReferentielOfsException.cause est incorrect",
				roe.getCause());
	}

	@Test
	public void testReferentielOfsExceptionStringThrowable() {
		final ReferentielOfsException roe = new ReferentielOfsException(
				"message", new IOException("__IOException__"));
		assertEquals("ReferentielOfsException.message est incorrect",
				"message", roe.getMessage());
		assertEquals("ReferentielOfsException.cause est incorrect",
				IOException.class, roe.getCause().getClass());
	}

	@Test
	public void testReferentielOfsExceptionString() {
		final ReferentielOfsException roe = new ReferentielOfsException(
				"message");
		assertEquals("ReferentielOfsException.message est incorrect",
				"message", roe.getMessage());
		assertNull("ReferentielOfsException.cause est incorrect",
				roe.getCause());
	}

	@Test
	public void testReferentielOfsExceptionThrowable() {
		final ReferentielOfsException roe = new ReferentielOfsException(
				new IOException("__IOException__"));
		assertEquals("ReferentielOfsException.message est incorrect",
				"java.io.IOException: __IOException__", roe.getMessage());
		assertEquals("ReferentielOfsException.cause est incorrect",
				IOException.class, roe.getCause().getClass());
	}

}
