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
/*******************************************************************************
 * Copyright (c) 2013 Metadata Technology Ltd.
 *  
 * All rights reserved. This program and the accompanying materials are made 
 * available under the terms of the GNU Lesser General Public License v 3.0 
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/lgpl.html
 * 
 * This file is part of the SDMX Component Library.
 * 
 * The SDMX Component Library is free software: you can redistribute it and/or 
 * modify it under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation, either version 3 of the License,
 * or (at your option) any later version.
 * 
 * The SDMX Component Library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of 
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU Lesser 
 * General Public License for more details.
 * 
 * You should have received a copy of the GNU Lesser General Public License 
 * along with The SDMX Component Library If not, see 
 * http://www.gnu.org/licenses/lgpl.
 * 
 * Contributors:
 * Metadata Technology - initial API and implementation
 ******************************************************************************/
package org.sdmxsource.util.io;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

import org.sdmxsource.sdmx.api.util.ReadableDataLocation;

public class InMemoryReadableDataLocation implements ReadableDataLocation {
	private static final long serialVersionUID = 971633373372917354L;
	private byte[] bytes;
	private String name;
	
	public InMemoryReadableDataLocation(byte[] bytes) {
		this(bytes, null);
	}
	
	public InMemoryReadableDataLocation(byte[] bytes, String name) {
		this.bytes = bytes;
		this.name = name;
	}
	
	public InMemoryReadableDataLocation(String uriStr) {
		if(uriStr == null) {
			throw new IllegalArgumentException("Can not create StreamSourceData - uriStr can not be null");
		}
		name = uriStr;
		try {
			URI uri = new URI(uriStr);
			if(uri.isAbsolute()) {
				if(!uri.getScheme().equals("file")) {
					URL url = uri.toURL();
					bytes = StreamUtil.toByteArray(URLUtil.getInputStream(url));
				} else {
					bytes = FileUtil.readFileAsString(uri.getPath()).getBytes();
				}
			}
		} catch (URISyntaxException e) {
			throw new RuntimeException(e);
		} catch (MalformedURLException e) {
			throw new RuntimeException(e);
		} 
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public InputStream getInputStream() {
		return new ByteArrayInputStream(bytes);
	}

	@Override
	public void close() {
		this.bytes = null;
	}

	@Override
	public boolean isClosed() {
		return false;
	}

	@Override
	public ReadableDataLocation copy() {
		return null;
	}

}
