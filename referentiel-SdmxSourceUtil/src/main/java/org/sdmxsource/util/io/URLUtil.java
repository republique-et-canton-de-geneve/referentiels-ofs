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

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.net.ConnectException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.net.URLConnection;
import java.util.zip.GZIPInputStream;

import org.apache.log4j.Logger;
import org.sdmxsource.sdmx.api.constants.ExceptionCode;
import org.sdmxsource.sdmx.api.exception.SdmxException;
import org.sdmxsource.sdmx.api.exception.SdmxServiceUnavailableException;
import org.sdmxsource.sdmx.api.exception.SdmxUnauthorisedException;

public class URLUtil {
	private static final Logger LOG = Logger.getLogger(URLUtil.class);

	/**
	 * Opens an input stream to the URL, accepts GZIP encoding.
	 * @param url
	 * @return
	 */
	public static InputStream getInputStream(URL url) {
		LOG.debug("Get Input Stream from URL: " + url);
		URLConnection connection = getconnection(url);
		return getInputStream(connection, null);
	}

	private static URLConnection getconnection(URL url)  {
		LOG.debug("Get URLConnection: " + url);
		// Make connection, use post mode, and send query
		URLConnection urlc;
		try {
			urlc = url.openConnection();
		} catch (IOException e) {
			throw new SdmxServiceUnavailableException(e, ExceptionCode.WEB_SERVICE_BAD_CONNECTION, url.toString());
		}
		urlc.setDoOutput(true);
		urlc.setAllowUserInteraction(false);
		urlc.addRequestProperty("Accept-Encoding", "gzip");
		return urlc;
	}

	private static InputStream getInputStream(URLConnection urlc, Object payload) {
		InputStream stream;
		try {
			if(payload != null) {
				//Send Payload
				PrintStream ps = new PrintStream(urlc.getOutputStream());
				ps.print(payload);
				ps.close();
			}
			stream = getInputStream(urlc);
		} catch(IOException e) {
			throw new SdmxServiceUnavailableException(e, ExceptionCode.WEB_SERVICE_BAD_CONNECTION, e.getMessage());
		}
		if(urlc.getContentEncoding() != null && urlc.getContentEncoding().equals("gzip")) {
			LOG.debug("Response recieved as GZIP");
			try {
				stream = new GZIPInputStream(stream);
			} catch (IOException e) {
				throw new SdmxException(e, "I/O Ecception while trying to unzip stream retrieved from service:" +urlc.getURL());
			}
		} 
		return stream;
	}

	private static InputStream getInputStream(URLConnection urlc) {
		try {
			return urlc.getInputStream();
		}  catch(ConnectException c) {
			throw new SdmxServiceUnavailableException(c, ExceptionCode.WEB_SERVICE_BAD_CONNECTION, urlc.getURL());
		}  catch(SocketException c) {
			throw new SdmxServiceUnavailableException(c, ExceptionCode.WEB_SERVICE_BAD_CONNECTION, urlc.getURL());
		} catch(SocketTimeoutException c) {
			throw new SdmxServiceUnavailableException(c, ExceptionCode.WEB_SERVICE_SOCKET_TIMEOUT, urlc.getReadTimeout());
		} catch (IOException e) {
			if(urlc instanceof HttpURLConnection) {
				try {
					if(((HttpURLConnection) urlc).getResponseCode() == 401) {
						throw new SdmxUnauthorisedException(e.getMessage());
					}
				} catch (IOException e1) {
					e1.printStackTrace();
				}
				HttpURLConnection httpConnection = (HttpURLConnection) urlc;
				InputStream is = httpConnection.getErrorStream();
				if(is != null) {
					return is;
				}
			}
			String message = null;
			if(e.getMessage().contains("Server returned HTTP response code:")) {
				String split = e.getMessage().split(":")[1];
				split = split.trim();
				split = split.substring(0, split.indexOf(" "));

				try {
					int responseCode = Integer.parseInt(split);

					switch(responseCode) {
					case 400 : message = "Response Code 400 = The request could not be understood by the server due to malformed syntax"; break;
					case 401 : message = "Response Code 401 = Authentication failure"; break;
					case 403 : message = "Response Code 403 = The server understood the request, but is refusing to fulfill it"; break;
					case 404 : message = "Response Code 404 = Page not found"; break;
					//TODO Do others
					}
					System.out.println(responseCode);
				} catch (Throwable th) {
					//DO NOTHING
				}
			}
			if(message != null) {
				throw new SdmxServiceUnavailableException(e, ExceptionCode.WEB_SERVICE_BAD_CONNECTION, message);
			} else  {
				throw new SdmxServiceUnavailableException(e, ExceptionCode.WEB_SERVICE_BAD_CONNECTION, urlc.getURL());
			}
		} 
	}

	/**
	 * Returns a URL from a String, throws a SdmxException if the URL String is not a valid URL
	 * @param urlStr
	 * @return
	 */
	public static URL getURL(String urlStr) {
		try {
			return new URL(urlStr);
		} catch (MalformedURLException e) {
			throw new SdmxException("Malformed URL: " + urlStr);
		}
	}

	/**
	 * Returns true if the HTTP Status is 200 (ok) from the given URL
	 * @param fileUri
	 * @return
	 */
	public static boolean urlExists(URL url) {
		try {
			HttpURLConnection.setFollowRedirects(false);
			// note : you may also need
			//        HttpURLConnection.setInstanceFollowRedirects(false)
			HttpURLConnection con =
					(HttpURLConnection) url.openConnection();
			con.setRequestMethod("HEAD");
			//Does the HTTP Status start with 2, if so, it is okay
			if(Integer.toString(con.getResponseCode()).startsWith("2")) {
				return true;
			}
			LOG.warn("URL "+url + " returns status code: " + con.getResponseCode());
			return false;
		}
		catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

}
