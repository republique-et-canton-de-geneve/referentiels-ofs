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
	public static void setupClass() throws Exception {
		parser = ctxt.getBean(SDMXParser.class);
		assertNotNull(parser);
	}

	@Test
	public void testParse() throws Exception {
		File file = new File("src/test/resources/test.xml");
		StructureWorkspace sw = parser.parse(file.toURI().toURL());
		assertNotNull("Le résultat du parsing est incorrect", sw);
		assertEquals("Erreur de parsing du header",
				"f198f7a6-57cd-4324-81a2-77b71e3edbef",
				sw.getStructureBeans(false).getHeader().getId());
		System.err.println(sw.getStructureBeans(false).getCodelists()
				.iterator().next().getCodeById("02"));
	}

	@Test
	public void testParseEncoding() throws Exception {
		File file = new File("src/test/resources/test.xml");
		StructureWorkspace sw = parser.parse(file.toURI().toURL());
		assertEquals("Erreur de parsing des listes", "Société simple", sw
				.getStructureBeans(false).getCodelists().iterator().next()
				.getCodeById("02").getName());
	}

}
