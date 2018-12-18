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
package org.sdmxsource.util.factory;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

import org.sdmxsource.sdmx.api.factory.ReadableDataLocationFactory;
import org.sdmxsource.sdmx.api.util.ReadableDataLocation;
import org.sdmxsource.util.io.FileUtil;
import org.sdmxsource.util.io.StreamUtil;
import org.sdmxsource.util.io.URIUtil;
import org.sdmxsource.util.io.URLUtil;

public class SdmxSourceReadableDataLocationFactory implements ReadableDataLocationFactory {
	private Long maxMemory = (1024l* 1024l * 30l);  //DEFAULT to 30Mb in memory
	private Long memoryUseage = 0l;

	class OverflowReadableDataLocation implements ReadableDataLocation {
		private static final long serialVersionUID = -8299140445772552916L;
		private byte[] bytes;
		private URI uri;
		private boolean deleteOnClose;
		private String name;

		private OverflowReadableDataLocation(byte[] bytes) {
			this.bytes = bytes;
		}

		private OverflowReadableDataLocation(URI uri, boolean deleteOnClose) {
			this.uri = uri;
			this.deleteOnClose = deleteOnClose;
		}

		@Override
		public InputStream getInputStream() {
			if(bytes != null) {
				return new BufferedInputStream(new ByteArrayInputStream(bytes));
			}
			return URIUtil.getInputStream(uri);
		}
		
		@Override
		public String getName() {
			return name;
		}

		@Override
		public void close() {
			if(bytes != null) {
				memoryUseage -= bytes.length;
				bytes = null;
			} else if(uri != null ){
				URIUtil.closeUri(uri);
				if(deleteOnClose) {
					URIUtil.deleteUri(uri);
				}
			}
		}
	}
	
	@Override
	public ReadableDataLocation getReadableDataLocation(String uriStr) {
		if(uriStr == null) {
			throw new IllegalArgumentException("Can not create StreamSourceData - uriStr can not be null");
		}
		try {
			URI uri = new URI(uriStr);
			if(uri.isAbsolute()) {
				if(!uri.getScheme().equals("file")) {
					URL url = uri.toURL();
					InputStream is = URLUtil.getInputStream(url);
					return getReadableDataLocation(is);
				} else {
					File f = URIUtil.getFile(uri);
					if(!f.exists()) {
						throw new IllegalArgumentException("Can not read stream, file does not exist: " +uriStr);
					}
				}
			}
			return new OverflowReadableDataLocation(uri, false);
		} catch (URISyntaxException e) {
			throw new RuntimeException(e);
		} catch (MalformedURLException e) {
			throw new RuntimeException(e);
		} 
	}
	

	@Override
	public ReadableDataLocation getReadableDataLocation(byte[] bytes) {
		return new OverflowReadableDataLocation(bytes);
	}

	@Override
	public ReadableDataLocation getReadableDataLocation(File file) {
		if(file == null) {
			throw new IllegalArgumentException("Can not create StreamSourceData - file can not be null");
		}
		
		if(FileUtil.getFileSize(file) > (maxMemory - memoryUseage)) {
			return new OverflowReadableDataLocation(file.toURI(), false);
		}
		try {
			return getReadableDataLocation(new FileInputStream(file));
		} catch (FileNotFoundException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public ReadableDataLocation getReadableDataLocation(URL url) {
		if(url == null) {
			throw new IllegalArgumentException("Can not create StreamSourceData - url can not be null");
		}
		try {
			return getReadableDataLocation(url.openStream());
		} catch (IOException e) {
			throw new RuntimeException("Could not connect to URL: "+ url, e);
		}
	}

	@Override
	public ReadableDataLocation getReadableDataLocation(URI uri) {
		if(uri == null) {
			throw new IllegalArgumentException("Can not create StreamSourceData - uri can not be null");
		}
		return getReadableDataLocation(URIUtil.getInputStream(uri));
	}

	@Override
	public ReadableDataLocation getReadableDataLocation(InputStream is) {
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		BufferedOutputStream bos = new BufferedOutputStream(out);
		
		boolean completedRead = false;
		if(memoryUseage < maxMemory) {
			completedRead = true;
			try {
				byte[] bytes = new byte[1024];
				int i;
				while((i = is.read(bytes)) > 0) {
					bos.write(bytes, 0, i);
					memoryUseage += i;
					if(memoryUseage > maxMemory) {
						completedRead = false;
						break;
					}
				}
				bos.flush();
			} catch(IOException e) {
				e.printStackTrace();
				throw new RuntimeException(e);
			} 
		}
		if(completedRead) {
			StreamUtil.closeStream(is);
			return new OverflowReadableDataLocation(out.toByteArray());
		}

		//THE READ WAS NOT COMPLETE (MEMORY LIMITS HIT) - COPY THE STREAM TO A FILE
		URI uri = URIUtil.getTemporaryURI();
		OutputStream tmpOut = URIUtil.getOutputStream(uri);
		bos=new BufferedOutputStream(tmpOut);
		if(out.size() > 0) {
			//Write to file system
			StreamUtil.copyStream(new ByteArrayInputStream(out.toByteArray()), bos, false);
			memoryUseage -= out.size();
			out = null;
		} 
		
		try {
			StreamUtil.copyStream(is, bos, false);
		} finally {
			StreamUtil.closeStream(is);
			StreamUtil.closeStream(bos);
		}
		return new OverflowReadableDataLocation(uri, true);
	}

	public Long getMemoryUseageBytes() {
		return memoryUseage;
	}

	public void setMaxMemoryKb(int maxMemory) {
		this.maxMemory = Long.valueOf(maxMemory) * 1024l;
	}
}
