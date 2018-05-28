package com.travel.code.client.destination.service;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.travel.entity.Area;
import com.travel.page.Sort;
import com.travel.page.Sort.Direction;
import com.travel.service.DefaultService;

@Service
@Transactional(rollbackFor = Exception.class)
public class DestinationService extends DefaultService<Area, String> {
	
	public Area findByRegionCode(String regionCode) {
		List<Area> areas = new ArrayList<>();
		try {
			areas = findBy(new AssembleCriteriaParamsCallback() {
				@Override
				public DetachedCriteria assembleParams(DetachedCriteria criteria) {
					criteria.add(Restrictions.eq("RegionCode", regionCode));
					criteria.add(Restrictions.eq("delete", Boolean.FALSE));
					return criteria;
				}
			});
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		if(areas.isEmpty()) {
			return null;
		}else {
			return areas.get(0);
		}
	}
	
	public Area findByRegionName(String regionName) {
		List<Area> areas = new ArrayList<>();
		try {
			areas = findBy(new AssembleCriteriaParamsCallback() {
				@Override
				public DetachedCriteria assembleParams(DetachedCriteria criteria) {
					criteria.add(Restrictions.eq("RegionName", regionName));
					criteria.add(Restrictions.eq("delete", Boolean.FALSE));
					return criteria;
				}
			});
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		if(areas.isEmpty()) {
			return null;
		}else {
			return areas.get(0);
		}
	}
	
	public List<Area> findByParentCode( List<Area> areas,String parentCode){
		if(areas == null) {
			areas = new ArrayList<>();
		}
		List<Area> a = new ArrayList<>();
		try {
			a = findBy(new AssembleCriteriaParamsCallback() {
				@Override
				public DetachedCriteria assembleParams(DetachedCriteria criteria) {
					criteria.add(Restrictions.eq("ParentCode", parentCode));
					criteria.add(Restrictions.eq("delete", Boolean.FALSE));
					return criteria;
				}
			},new Sort(
					new Sort.Order(Direction.ASC, "RegionCode"))
			);
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		for(Area area : a) {
			if(areas.contains(area)) {
				continue;
			}else {
				areas.add(area);
			}
		}
		return areas;
	}
	
	
	
}
