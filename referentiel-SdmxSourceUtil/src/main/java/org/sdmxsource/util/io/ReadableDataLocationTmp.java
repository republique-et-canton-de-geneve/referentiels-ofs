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

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

import org.sdmxsource.sdmx.api.util.ReadableDataLocation;


/**
 * Built from an InputStream - copies the data to a local temporary file, and allocates the information out as new input streams on demand
 */
public class ReadableDataLocationTmp implements ReadableDataLocation {
	
	private static final long serialVersionUID = 860775214722641122L;
	protected URI uri;
	protected boolean deleteOnClose = false;
	private String name;
	
	public ReadableDataLocationTmp(String uriStr) {
		if(uriStr == null) {
			throw new IllegalArgumentException("Can not create StreamSourceData - uriStr can not be null");
		}
		try {
			this.uri = new URI(uriStr);
			if(uri.isAbsolute()) {
				if(!uri.getScheme().equals("file")) {
					uri = URIUtil.getURIUtil().getUri(uri.toURL().openStream());
					deleteOnClose = true;
				}
			}
		} catch (URISyntaxException e) {
			throw new RuntimeException(e);
		} catch (MalformedURLException e) {
			throw new RuntimeException(e);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
	
	public ReadableDataLocationTmp(File f) {
		if(f == null) {
			throw new IllegalArgumentException("Can not create StreamSourceData - file can not be null");
		}
		uri = f.toURI();
		name = f.getName();
	}

	public ReadableDataLocationTmp(URL url) {
		if(url == null) {
			throw new IllegalArgumentException("Can not create StreamSourceData - url can not be null");
		}
		try {
			uri = URIUtil.getURIUtil().getUri(url.openStream());
			deleteOnClose = true;
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	public ReadableDataLocationTmp(URI uri) {
		if(uri == null) {
			throw new IllegalArgumentException("Can not create StreamSourceData - uri can not be null");
		}
		this.uri = uri;
	}
	
	public ReadableDataLocationTmp(InputStream is) {
		uri = URIUtil.getTemporaryURI();
		deleteOnClose = true;
		StreamUtil.copyStream(is, URIUtil.getOutputStream(uri));
		StreamUtil.closeStream(is);
	}

	@Override
	public InputStream getInputStream() {
		return URIUtil.getInputStream(uri);
	}
	
	@Override
	public String getName() {
		return name;
	}

	@Override
	public void close() {
		if(deleteOnClose) {
			URIUtil.deleteUri(uri);
		}
	}
	@Override
	public String toString() {
		return uri.toString();
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
