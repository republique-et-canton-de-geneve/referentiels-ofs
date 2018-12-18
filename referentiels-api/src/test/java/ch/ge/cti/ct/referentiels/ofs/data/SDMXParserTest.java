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
package ch.ge.cti.ct.referentiels.ofs.data;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.File;

import org.junit.BeforeClass;
import org.junit.Test;
import org.sdmxsource.sdmx.api.model.StructureWorkspace;

public class SDMXParserTest extends AbstractDataTest {

    private static SDMXParser parser;

    @BeforeClass
    public static void setupTestClass() throws Exception {
	assertNotNull("Le contexte applicatif Spring n'est pas chargé", ctxt);
	parser = ctxt.getBean(SDMXParser.class);
	assertNotNull("Le parseur SDMX n'a pu être instancié", parser);
    }

    @Test
    public void testParse() throws Exception {
	final File file = new File("src/test/resources/test.xml");
	final StructureWorkspace sw = parser.parse(file.toURI().toURL());
	assertNotNull("Le résultat du parsing est incorrect", sw);
	assertEquals("Erreur de parsing du header",
		"f198f7a6-57cd-4324-81a2-77b71e3edbef",
		sw.getStructureBeans(false).getHeader().getId());
	System.err.println(sw.getStructureBeans(false).getCodelists()
		.iterator().next().getCodeById("02"));
    }

    @Test
    public void testParseEncoding() throws Exception {
	final File file = new File("src/test/resources/test.xml");
	final StructureWorkspace sw = parser.parse(file.toURI().toURL());
	assertEquals("Erreur de parsing des listes", "Société simple", sw
		.getStructureBeans(false).getCodelists().iterator().next()
		.getCodeById("02").getName());
    }

}
