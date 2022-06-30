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
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URI;

import org.sdmxsource.sdmx.api.exception.SdmxException;
import org.sdmxsource.sdmx.api.util.ReadableDataLocation;
import org.sdmxsource.sdmx.api.util.WriteableDataLocation;

public class OverflowWriteableDataLocation implements WriteableDataLocation {
	private static final long serialVersionUID = 2278635606454143014L;
	private static long maxMemory = 1024l * 1024l * 30;  //DEFALUT TO 30Mb
	private static long currentMemory = 0l;

	private URI tmpUri;
	private OutputStream out;
	
	private Buffer inMemoryBuffer = new Buffer();
	private String name;
	
	public OverflowWriteableDataLocation() {
		this(null);
	}
	
	public OverflowWriteableDataLocation(String name) {
		if(currentMemory > maxMemory) {
			tmpUri = URIUtil.getTemporaryURI();
			out = URIUtil.getOutputStream(tmpUri);
		} 
		this.name = name;
	}
	
	@Override
	public String getName() {
		return name;
	}
	
	private class Buffer extends ByteArrayOutputStream {
		private boolean isOverflow;
		@Override
		public synchronized void write(byte[] b, int off, int len) {
			if(isOverflow) {
				try {
					out.write(b, off, len);
				} catch (IOException e) {
					throw new SdmxException(e, "IO Exception whilst trying to write to file output stream:" + tmpUri);
				}
			} else {
				super.write(b, off, len);
				overflow();
			}
		}

		public int getCount() {
			return count;
		}

		@Override
		public void write(int b) {
			if(isOverflow) {
				try {
					out.write(b);
				} catch (IOException e) {
					throw new SdmxException(e, "IO Exception whilst trying to write to file output stream:" + tmpUri);
				}
			} else {
				super.write(b);
				overflow();
			}
		}
		
		private void overflow() {
			currentMemory = super.count;
			if(currentMemory >= maxMemory) {
				isOverflow = true;
				//Copy over data to file
				if(tmpUri == null) {
					//Create an output file
					tmpUri = URIUtil.getTemporaryURI();
					out = URIUtil.getOutputStream(tmpUri);
				}
				try {
					this.writeTo(out);
				} catch (IOException e) {
					throw new SdmxException(e, "IO Exception whilst trying to overflow from in memory outputstream to file output stream:" + tmpUri);
				}
				currentMemory -= super.count;
				super.buf =  new byte[100];
				super.count = 0;
			}
		}

		@Override
		public void flush() throws IOException {
			if(out != null) {
				out.flush();
			}
		}
	}

	@Override
	public InputStream getInputStream() {
		if(out == null) {
			return new ByteArrayInputStream(inMemoryBuffer.toByteArray());
		}
		try {
			out.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return URIUtil.getInputStream(tmpUri);
	}
	
	@Override
	public OutputStream getOutputStream() {
		return inMemoryBuffer;
	}
	
	@Override
	public void close()  {
		if(out != null) {
			URIUtil.closeUri(tmpUri);
			URIUtil.deleteUri(tmpUri);
		} else if(inMemoryBuffer != null){
			currentMemory -= inMemoryBuffer.getCount();
			inMemoryBuffer = null;
		}
	}

	@Override
	public String toString() {
		if(tmpUri == null) {
			return new String(inMemoryBuffer.toByteArray());
		}
		return "OverflowWriteableDataLocation: " + tmpUri.toString();
	}

	/**
	 * Sets the maximum amount of data to store in memory, in kb
	 * @param maxMemory
	 */
	public static void setMaxMemoryKb(long maxMemory) {
		OverflowWriteableDataLocation.maxMemory = maxMemory * 1024l;
	}

	/**
	 * Returns the current memory usage
	 * @return
	 */
	public static long getCurrentMemory() {
		return currentMemory;
	}

	public static long getMaxMemory() {
		return maxMemory;
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
