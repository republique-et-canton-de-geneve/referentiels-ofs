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
package org.sdmxsource.sdmx.util.exception;

import java.util.ArrayList;
import java.util.List;

import org.sdmxsource.sdmx.api.exception.SdmxSyntaxException;

public class SchemaValidationException extends SdmxSyntaxException {

	private static final long serialVersionUID = -6863926329160671609L;
	private List<String> validationErrors = new ArrayList<String>();
	
	
	public SchemaValidationException(List<String> errors) {
		super(mergeErrors(errors));
		for(String currentError : errors) {
			validationErrors.add(currentError);
		}
	}

	public SchemaValidationException(Throwable th) {
		super(th);
	}
	
	private static String mergeErrors(List<String> errors) {
		StringBuilder sb = new StringBuilder();
		for(String currentError : errors) {
			sb.append(currentError);
		}
		return sb.toString();
	}

	public List<String> getValidationErrors() {
		return new ArrayList<String>(validationErrors);
	}
}
