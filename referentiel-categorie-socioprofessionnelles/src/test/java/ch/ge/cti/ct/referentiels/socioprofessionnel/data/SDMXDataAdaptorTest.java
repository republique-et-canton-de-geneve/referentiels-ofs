/*
 * Referentiels OFS
 *
 * Copyright (C) 2018 RÃ©publique et canton de GenÃ¨ve
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
package ch.ge.cti.ct.referentiels.socioprofessionnel.data;

import static org.junit.Assert.assertNotNull;

import java.io.File;

import org.junit.BeforeClass;
import org.junit.Test;

import ch.ge.cti.ct.referentiels.socioprofessionnel.AbstractReferentielTest;
import ch.ge.cti.ct.referentiels.socioprofessionnel.model.ReferentielSocioprofessionnel;

public class SDMXDataAdaptorTest extends AbstractReferentielTest {

    private static SDMXDataAdaptor adaptor;

    @BeforeClass
    public static void setupSDMXDataAdaptorClass() throws Exception {
	adaptor = SDMXDataAdaptor.Factory.getInstance();
	assertNotNull(adaptor);
    }

    @Test
    public void testParse() throws Exception {
	final File file = new File("src/test/resources/CH1_BN+HCL_CSP+2.0.xml");
	final ReferentielSocioprofessionnel ref = adaptor.parse(file.toURI()
		.toURL());
	assertNotNull("Erreur d'instanciation du référentiel", ref);
    }

    @Test
    public void testFactory() throws Exception {
	assertNotNull(SDMXDataAdaptor.Factory.getInstance());
    }
}
