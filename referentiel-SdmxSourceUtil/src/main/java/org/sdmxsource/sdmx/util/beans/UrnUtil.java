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
package org.sdmxsource.sdmx.util.beans;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;
import org.sdmxsource.sdmx.api.constants.ExceptionCode;
import org.sdmxsource.sdmx.api.constants.SDMX_STRUCTURE_TYPE;
import org.sdmxsource.sdmx.api.exception.SdmxSemmanticException;
import org.sdmxsource.sdmx.api.model.beans.base.AgencySchemeBean;
import org.sdmxsource.util.ObjectUtil;


public class UrnUtil {
	private static Logger LOG = Logger.getLogger(UrnUtil.class);
	
	
	public static void validateURN(String urn, SDMX_STRUCTURE_TYPE structureType) {
		if(urn.split("=").length == 0) {
			throw new SdmxSemmanticException(ExceptionCode.STRUCTURE_URN_MALFORMED, urn);
		}
		String urnPrefix = urn.split("=")[0];
		SDMX_STRUCTURE_TYPE targetType =  SDMX_STRUCTURE_TYPE.parsePrefix(urnPrefix);
		if(targetType != structureType) {
			throw new SdmxSemmanticException(ExceptionCode.STRUCTURE_URN_UNEXPECTED_PREFIX, urn, structureType.getUrnPrefix());
		}
		String[] components = getUrnComponents(urn);
		if(structureType.isMaintainable()) {
			if(components == null) {
				throw new SdmxSemmanticException(ExceptionCode.STRUCTURE_URN_MALFORMED, urn);
			}
			if(structureType == SDMX_STRUCTURE_TYPE.SUBSCRIPTION) {
				if(components.length != 4) {
					throw new SdmxSemmanticException(ExceptionCode.STRUCTURE_URN_MALFORMED, urn);
				}
			} else if(components.length != 2) {
				throw new SdmxSemmanticException(ExceptionCode.STRUCTURE_URN_MALFORMED, urn);
			}
		} else if(structureType.isIdentifiable()) {
			if(components == null || components.length <= 2) {
				if(components.length == 1 && structureType == SDMX_STRUCTURE_TYPE.AGENCY) {
					
				} else {
					throw new SdmxSemmanticException(ExceptionCode.STRUCTURE_URN_MALFORMED, urn);
				}
			}
		}
		for(String currentComponent : components) {
			if(!ObjectUtil.validString(currentComponent)) {
				throw new SdmxSemmanticException(ExceptionCode.STRUCTURE_URN_MALFORMED, urn);
			}
		}
		String version = getVersionFromUrn(urn);
		if(version == null && structureType != SDMX_STRUCTURE_TYPE.AGENCY) {
			throw new SdmxSemmanticException(ExceptionCode.STRUCTURE_URN_MALFORMED, urn);
		}
	}
	/**
	 * Returns the type of identifiable object that this urn represents
	 * @param urn
	 * @return
	 * @throws StructureException
	 */
	public static SDMX_STRUCTURE_TYPE getIdentifiableType(String urn) throws SdmxSemmanticException {
		if(urn.split("=").length == 0) {
			throw new SdmxSemmanticException(ExceptionCode.STRUCTURE_URN_MALFORMED, urn);
		}
		String urnPrefix = urn.split("=")[0];
		return SDMX_STRUCTURE_TYPE.parsePrefix(urnPrefix);
	}
	
    public static String getUrnPostfix(String urn) {
        return urn.split("=")[1];
    }
    
    public static String getUrnPrefix(String urn) {
        return urn.split("=")[0];
    }
    
    
    public static String getUrnPostfix(String agencyId, String id, String version) {
        return agencyId + ":" + id + "("+version+")";
    }
    
    public static String getUrnPostfix(String agencyId, String maintid, String version, String... id) {
        String idPost="";
    	for(int i = 0 ; i < id.length ; i++) {
    		idPost+=".";
    		idPost+= id[i];
    		i++;
    	}
    	return agencyId + ":" + maintid + "("+version+")" + idPost;
    }
    
    private static String removeVersionsFromUrn(String urn) {
    	if(urn.contains("[") || urn.contains("]")) {
    		LOG.warn("URN using pre 2.1 syntax [#version] upgrading to (#version) : " + urn);
    		urn = urn.replaceAll("\\[", "(");
    		urn = urn.replaceAll("\\]", ")");
    	}
        return  urn.replaceAll("(?=\\().*?(?<=\\))", "");
    }

    /**
     * Returns
     * @param urn
     * @return
     */
    public static String[] getUrnComponents(String urn) {
    	String mainUrn = getUrnPostfix(urn);
    	if(getIdentifiableType(urn) == SDMX_STRUCTURE_TYPE.AGENCY) {
    		String[] agencies = mainUrn.split("\\.");
    		if(agencies.length == 1) {
    			//This is an agency belonging to the default scheme
    			return new String[] {AgencySchemeBean.DEFAULT_SCHEME, AgencySchemeBean.FIXED_ID, agencies[0]};
    		}
    		
    		return new String[] {mainUrn.substring(0, mainUrn.lastIndexOf(".")), AgencySchemeBean.FIXED_ID, agencies[agencies.length-1]};
    	}
        
        String urnVersionsRemoved = removeVersionsFromUrn(mainUrn);
        String[] majorComponents = urnVersionsRemoved.split("\\:");
        if(majorComponents.length == 1) {
        	throw new SdmxSemmanticException("URN agency id is expected to be followed by a ':' character : '"+urn+"'");
        }
        if(majorComponents.length != 2) {
        	throw new SdmxSemmanticException("URN should not contain more than one ':' character: '"+urn+"'");
        }
        String[] minorComponents = majorComponents[1].split("\\.");
        String[] returnArray = new String[minorComponents.length + 1];
        returnArray[0] = majorComponents[0];
        for(int i = 0; i < minorComponents.length; i++) {
        	returnArray[i+1] = minorComponents[i];
        }
        return returnArray;
    }

    public static String getVersionFromUrn(String urn) {
    	if(urn.contains("[") || urn.contains("]")) {
    		LOG.warn("URN using pre 2.1 syntax [#version] upgrading to (#version) : " + urn);
    		urn = urn.replaceAll("\\[", "(");
    		urn = urn.replaceAll("\\]", ")");
    	}
        String mainUrn = getUrnPostfix(urn);
        String versionRegEx = "((?=\\().*?(?<=\\)))";
        Pattern p = Pattern.compile(versionRegEx);
        Matcher m = p.matcher(mainUrn);
        if(m.find()) {
        	String version = m.group();
        	version = version.replaceAll("\\(", "");
        	version = version.replaceAll("\\)", "");
        	return version;
        }
        return null;
    }
}
