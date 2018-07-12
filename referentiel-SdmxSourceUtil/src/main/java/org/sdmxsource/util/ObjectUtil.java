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
package org.sdmxsource.util;

import java.util.Collection;
import java.util.Iterator;
import java.util.Map;

/**
 * Utility class providing helper methods for generic Objects.
 */
public class ObjectUtil {

	/**
	 * Returns whether all of the strings are not null and have a length of greater than zero
	 * after trimming the leading and trailing whitespace.
	 * @param strings
	 * @return
	 */
	public static boolean validString(String...strings) {
		if(strings == null) {
			return false;
		}
		for(String str : strings) {
			if(str == null || str.length() == 0) {
				return false;
			}
			if(str.trim().length() == 0) {
				return false;
			}
		}
		return true;
	}
	
	/**
	 * Returns whether at least one of the strings is not null and has a length of greater than zero
	 * after trimming the leading and trailing whitespace
	 * @param strings
	 * @return
	 */
	public static boolean validOneString(String...strings) {
		for(String str : strings) {
			if(str != null && str.length() > 0) {
				if(str.trim().length() == 0) {
					return false;
				}
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Returns whether all of the objects are not null
	 * @param strings
	 * @return
	 */
	public static boolean validObject(Object... objs) {
		for(Object obj : objs) {
			if(obj == null) {
				return false;
			}
		}
		return true;
	}
	
	/**
	 * Returns true if:
	 * <ul>
	 *   <li>Both Strings are null</li>
	 *   <li>Both Strings are equal</li>
	 * </ul>
	 * @param s1
	 * @param s2
	 * @return
	 */
	@Deprecated
	public static boolean equivalentString(String s1, String s2) {
		return equivalent(s1, s2);	
	}
	
	/**
	 * Returns true if:
	 * <ul>
	 *   <li>Both Strings are null</li>
	 *   <li>Both Strings are equal</li>
	 * </ul>
	 * @param o1
	 * @param o2
	 * @return
	 */
	public static boolean equivalent(Object o1, Object o2) {
		if(o1 == null && o2 == null) {
			return true;
		} 
		if(o1 == null && o2 != null) {
			return false;
		}
		if(o2 == null && o1 != null) {
			return false;
		}
		
		return o1.equals(o2);
	}
	
	public static boolean equivalentCollection(Collection<?> c1, Collection<?> c2) {
		containsAll(c1, c2);
		Iterator<?> it1 = c1.iterator();
		Iterator<?> it2 = c2.iterator();
		while(it1.hasNext()) {
			Object obj1 = it1.next();
			Object obj2 = it2.next();
			if(!equivalent(obj1, obj2)) {
				return false;
			}
		} 
		return true;
	}
	
	public static boolean containsAll(Collection<?> c1, Collection<?> c2) {
		if(c1 == null && c2 == null) {
			return true;
		} 
		if(c2 == null && c1 != null) {
			return false;
		}
		if(c1 == null && c2 != null) {
			return false;
		}
		if(c1.size() != c2.size()) {
			return false;
		}
		if(!c1.containsAll(c2)) {
			return false;
		}
		if(!c2.containsAll(c1)) {
			return false;
		}
		return true;
	}
	
	/**
	 * Returns true if the collection is not null and has a size greater than zero. 
	 * @param collection
	 * @return
	 */
	public static boolean validCollection(Collection<?> collection) {
		return collection!= null && collection.size() > 0;
	}
	
	/**
	 * Returns true if the array is not null and has a size greater than zero.
	 * @param arr
	 * @return
	 */
	public static boolean validArray(Object[] arr) {
		return arr!= null && arr.length > 0;
	}
	
	/**
	 * Returns true if the Map is not null and has a size greater than zero.
	 * @param map
	 * @return
	 */
	public static boolean validMap(Map<?, ?> map) {
		return map!= null && map.size() > 0;
	}
}
