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

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.sdmxsource.sdmx.api.model.beans.base.MaintainableBean;
import org.sdmxsource.sdmx.api.model.beans.reference.MaintainableRefBean;
import org.sdmxsource.sdmx.api.model.beans.reference.StructureReferenceBean;
import org.sdmxsource.util.ObjectUtil;
import org.sdmxsource.util.VersionableUtil;


/**
 * This class provides utility methods that can be performed on a MaintainableBean
 */
public class MaintainableUtil<T extends MaintainableBean> {
	
	/**
	 * Returns a collection keeping only the latest versions of each maintainable passed in.
	 * <p/>
	 * Note, the returned Set is a new Set, no changes are made to the passed in collection.
	 * 
	 * @param maintianables
	 * @param ref
	 * @return
	 */
	public static Set<MaintainableBean> filterCollectionGetLatest(Collection<MaintainableBean> maintianables) {
		Map<String, MaintainableBean> resultMap = new HashMap<String, MaintainableBean>();
		boolean filteredResponse = false;
		for(MaintainableBean currentMaint : maintianables) {
			String key = currentMaint.getStructureType().getType() + "_" + currentMaint.getAgencyId() + "_" + currentMaint.getId();
			if(resultMap.containsKey(key)) {
				filteredResponse = true;
				MaintainableBean storedAgainstKey = resultMap.get(key);
				if(VersionableUtil.isHigherVersion(currentMaint.getVersion(), storedAgainstKey.getVersion())) {
					resultMap.put(key, currentMaint);
				}
			} else {
				resultMap.put(key, currentMaint);
			}
		}
		if(filteredResponse) {
			return new HashSet<MaintainableBean>(resultMap.values());
		}
		return new HashSet<MaintainableBean>(maintianables);
	}

	/**
	 * Returns a collection keeping only the latest versions of each maintainable passed in.
	 * <p/>
	 * Note, the returned Set is a new Set, no changes are made to the passed in collection.
	 * 
	 * @param maintianables
	 * @param ref
	 * @return
	 */
	public Set<T> filterCollectionGetLatestOfType(Collection<T> maintianables) {
		Map<String, T> resultMap = new HashMap<String, T>();
		boolean filteredResponse = false;
		for(T currentMaint : maintianables) {
			String key = currentMaint.getAgencyId() + "_" + currentMaint.getId();
			if(resultMap.containsKey(key)) {
				filteredResponse = true;
				T storedAgainstKey = resultMap.get(key);
				if(VersionableUtil.isHigherVersion(currentMaint.getVersion(), storedAgainstKey.getVersion())) {
					resultMap.put(key, currentMaint);
				}
			} else {
				resultMap.put(key, currentMaint);
			}
		}
		if(filteredResponse) {
			return new HashSet<T>(resultMap.values());
		}
		return new HashSet<T>(maintianables);
	}
	/**
	 * Returns a collection of maintainables that match the input reference
	 * @param maintianables
	 * @param ref
	 * @return
	 */
	public Set<T> filterCollection(Collection<T> maintianables, MaintainableRefBean ref) {
		Set<T> returnSet = new HashSet<T>();
		
		String agencyId = null;
		String id = null;
		String version = null;
		
		if(ref != null) {
			agencyId = ref.getAgencyId();
			id = ref.getMaintainableId();
			version = ref.getVersion();
		}
		
		for(T currentMaintainable : maintianables) {
			if(agencyId == null || currentMaintainable.getAgencyId().equals(agencyId)) {
				if(id == null || currentMaintainable.getId().equals(id)) {
					if(version == null || currentMaintainable.getVersion().equals(version)) {
						returnSet.add(currentMaintainable);
					}
				}
			}
		}
		return returnSet;
	}

	/**
	 * For a set of maintainables, this method will return the maintainable that has the same urn as the ref bean.
	 * <p/>
	 * If the ref bean does not have a urn, then it will return any matches for the agency id, id and version
	 * as the ref bean. 
	 * <p/>
	 * If the ref bean does not have a urn OR agency id, id and version set then it will return all the beans.
	 * <p/>
	 * <p/>
	 *  
	 * @param maintianables
	 * @param ref
	 * @return
	 */
	public static void findMatches(Collection<MaintainableBean> populateCollection, Collection<? extends MaintainableBean> maintianables, StructureReferenceBean ref) {
		if(ref == null) {
			throw new IllegalArgumentException("Ref is null");
		}

		if(maintianables != null) {
			for(MaintainableBean currentMaintainable : maintianables) {
				if(match(currentMaintainable, ref)) {
					populateCollection.add(currentMaintainable);
				}
			}
		}
	}

	/**
	 * For a set of maintainables, this method will return the maintainable that has the same urn as the ref bean.
	 * <p/>
	 * If the ref bean does not have a urn, then it will return any matches for the agency id, id and version
	 * as the ref bean. 
	 * <p/>
	 * If the ref bean does not have a urn OR agency id, id and version set then it will return all the beans.
	 * <p/>
	 * <p/>
	 *  
	 * @param maintianables
	 * @param ref
	 * @return
	 */
	public static Set<MaintainableBean> findMatches(Collection<? extends MaintainableBean> maintianables, StructureReferenceBean ref) {
		if(ref == null) {
			throw new IllegalArgumentException("Ref is null");
		}
		Set<MaintainableBean> returnSet = new HashSet<MaintainableBean>();

		if(maintianables != null) {
			for(MaintainableBean currentMaintainable : maintianables) {
				if(match(currentMaintainable, ref)) {
					returnSet.add(currentMaintainable);
				}
			}
		}
		return returnSet;
	}

	public static boolean match(MaintainableBean maint, StructureReferenceBean ref) {
		if(ref==null) {
			return true;
		}
		if (ObjectUtil.validString(ref.getMaintainableUrn())) {
			return maint.getUrn().equals(ref.getMaintainableUrn());
		}

		boolean match = true;
		if(ObjectUtil.validString(ref.getMaintainableReference().getAgencyId())) {
			match = maint.getAgencyId().equals(ref.getMaintainableReference().getAgencyId());
		}
		if(match) {
			if(ObjectUtil.validString(ref.getMaintainableReference().getMaintainableId())) {
				match = maint.getId().equals(ref.getMaintainableReference().getMaintainableId());
			}
		}
		if(match) {
			if(ObjectUtil.validString(ref.getMaintainableReference().getVersion())) {
				match = maint.getVersion().equals(ref.getMaintainableReference().getVersion());
			}
		}

		return match;
	}
	
	public static boolean subsetOf(MaintainableRefBean ref, MaintainableRefBean ref2) {
		boolean match = true;
		
		if(ObjectUtil.validString(ref.getAgencyId())) {
			match = ref.getAgencyId().equals(ref2.getAgencyId());
		}
		if(match) {
			if(ObjectUtil.validString(ref.getMaintainableId())) {
				match = ref.getMaintainableId().equals(ref2.getMaintainableId());
			}
		}
		if(match) {
			if(ObjectUtil.validString(ref.getVersion())) {
				match = ref.getVersion().equals(ref2.getVersion());
			}
		}
		return match;
	}
	/**
	 * For a set of maintainables, this method will return the maintainable that matches the parameters of the ref bean.
	 * <p/>
	 * If the ref bean does not have a urn, then it will return the maintainable that has the same agency id, id and version
	 * as the ref bean. 
	 * <p/>
	 * If the ref bean does not have a urn OR agency id, id and version set then it will result in an error.
	 * <p/>
	 * This method will stop at the first match and return it, no checks are performed on the type of maintainables in the set.
	 * <p/>
	 * If no match is found null is returned.  
	 *  
	 * @param maintianables
	 * @param ref
	 * @return
	 */
	public static MaintainableBean resolveReference(Collection<? extends MaintainableBean> maintianables, StructureReferenceBean ref) {
		if(ref == null) {
			throw new IllegalArgumentException("Ref is null");
		}
		MaintainableRefBean mRef = ref.getMaintainableReference();
		if(!ObjectUtil.validString(ref.getTargetUrn())  && !ObjectUtil.validString(mRef.getAgencyId(), mRef.getMaintainableId(), mRef.getVersion())) {
			throw new IllegalArgumentException("Ref requires a URN or AgencyId, Maintainable Id and Version");
		}
		if(maintianables != null) {
			for(MaintainableBean currentMaintainable : maintianables) {
				if(currentMaintainable.getStructureType() == ref.getMaintainableStructureType()) {
					if(mRef.getAgencyId() == null || currentMaintainable.getAgencyId().equals(mRef.getAgencyId())) {
						if(mRef.getMaintainableId() == null || currentMaintainable.getId().equals(mRef.getMaintainableId())) {
							if(mRef.getVersion() == null || currentMaintainable.getVersion().equals(mRef.getVersion())) {
								return currentMaintainable;
							}
						}
					}
				}
			}
		}
		return null;
	}

	public static MaintainableBean resolveReference(Collection<? extends MaintainableBean> maintainables, MaintainableRefBean ref) {
		if(!ObjectUtil.validCollection(maintainables)) {
			return null;
		}
		if(ref == null) {
			if(maintainables.size() == 1) {
				return maintainables.iterator().next();
			}
			throw new IllegalArgumentException("Can not resolve reference, more then one bean supplied and no reference parameters passed in");
		}
		if(maintainables != null) {
			for(MaintainableBean currentMaintainable : maintainables) {
				if(ref.getAgencyId() == null || currentMaintainable.getAgencyId().equals(ref.getAgencyId())) {
					if(ref.getMaintainableId() == null || currentMaintainable.getId().equals(ref.getMaintainableId())) {
						if(ref.getVersion() == null || currentMaintainable.getVersion().equals(ref.getVersion())) {
							return currentMaintainable;
						}
					}
				}
			}
		}
		return null;
	}
}
