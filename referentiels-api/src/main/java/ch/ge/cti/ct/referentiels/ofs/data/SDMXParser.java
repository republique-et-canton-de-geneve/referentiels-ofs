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

import java.net.URL;

import org.sdmxsource.sdmx.api.factory.ReadableDataLocationFactory;
import org.sdmxsource.sdmx.api.manager.parse.StructureParsingManager;
import org.sdmxsource.sdmx.api.model.StructureWorkspace;
import org.sdmxsource.sdmx.api.util.ReadableDataLocation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * parseur générique de fichier SDMX<br/>
 * Génère une structure intermédiaire SDMX  
 *
 */
@Service("sdmxParser")
public class SDMXParser {
	private final StructureParsingManager structureParsingManager;
	private final ReadableDataLocationFactory rdlFactory;

	/**
	 * Constructeur di parseur
	 * @param structureParsingManager parseur SDMX
	 * @param rdlFactory factory de reader de fichier SDMX
	 */
	@Autowired(required = true)
	public SDMXParser(
			final StructureParsingManager structureParsingManager,
			final ReadableDataLocationFactory rdlFactory) {
		this.structureParsingManager = structureParsingManager;
		this.rdlFactory = rdlFactory;
	}

	/**
	 * Parsing du fichier SDMX
	 * @param urlSDMX url du fichier SDMX
	 * @return structure de données SDMX
	 */
	public StructureWorkspace parse(final URL urlSDMX) {
		final ReadableDataLocation rdl = rdlFactory
				.getReadableDataLocation(urlSDMX);
		return structureParsingManager.parseStructures(rdl);
	}

}
