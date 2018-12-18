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
package org.sdmxsource.util.resourceBundle;

import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

import org.sdmxsource.sdmx.api.exception.SdmxException;
import org.sdmxsource.sdmx.api.util.MessageResolver;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.stereotype.Service;

@Service
public class MessageDecoder implements MessageResolver {
	private static ResourceBundleMessageSource messageSource;
	private static Set<String> baseNames = new HashSet<String>();
	private static Locale loc = new Locale("en");
	
	static {
		messageSource = new ResourceBundleMessageSource();
		messageSource.setBasename("exception");
		baseNames.add("exception");
	}
	
	public MessageDecoder() {
		SdmxException.setMessageResolver(this);
	}

	@Override
	public String resolveMessage(String messageCode, Locale locale, Object... args) {
		try {
			return messageSource.getMessage(messageCode, args, locale);
		} catch(Throwable th) {
			return messageCode;
		}
	}

	public static String decodeMessage(String id, Object... args) {
		
		
		if(messageSource == null) {
			return id;
		}
		return messageSource.getMessage(id, args, loc);
	}
	
	public static String decodeMessageDefaultLocale(String id, Object... args) {
		return messageSource.getMessage(id, args, loc);
	}
	
	public static String decodeMessageGivenLocale(String id, String lang, Object... args) {
		
		if(messageSource == null) {
			return id;
		}
		return messageSource.getMessage(id, args, loc);
	}
	
	public static ResourceBundleMessageSource getMessageSource() {
		return messageSource;
	}

	public static void addBaseName(String baseName) {
		if(MessageDecoder.messageSource == null) {
			MessageDecoder.messageSource = new ResourceBundleMessageSource();
		}
		baseNames.add(baseName);
		messageSource.setBasenames(baseNames.toArray(new String[baseNames.size()]));
		MessageDecoder.messageSource = messageSource;
	}	
	
	public void setBasenames(Set<String> basenames) {
		for(String baseName : basenames) {
			addBaseName(baseName);
		}
	}
}
