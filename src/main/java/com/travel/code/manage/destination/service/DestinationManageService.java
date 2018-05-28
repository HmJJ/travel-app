package com.travel.code.manage.destination.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;

import com.alibaba.fastjson.JSON;
import com.travel.entity.Area;
import com.travel.page.Sort;
import com.travel.page.Sort.Direction;
import com.travel.service.DefaultService;
import com.travel.support.CommonResponse;

@Service
@Transactional(rollbackFor = Exception.class)
public class DestinationManageService extends DefaultService<Area, String> {
	
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
	
	public String findProvince(Model model) {
		CommonResponse retval = new CommonResponse(false);
		Map<String, Object> map = new HashMap<>();
		try {
			List<Area> areas = findBy(new AssembleCriteriaParamsCallback() {
				@Override
				public DetachedCriteria assembleParams(DetachedCriteria criteria) {
					criteria.add(Restrictions.eq("ParentCode", "000000"));
					criteria.add(Restrictions.eq("delete", Boolean.FALSE));
					return criteria;
				}
			},new Sort(
					new Sort.Order(Direction.ASC, "RegionCode"))
			);
			map.put("province_list", areas);
			model.addAttribute("province_list", areas);
			retval.setData(map);
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return JSON.toJSONString(retval);
	}
	
	public String findCitys(Model model, String parentCode) {
		CommonResponse retval = new CommonResponse(false);
		Map<String, Object> map = new HashMap<>();
		List<Area> area = new ArrayList<>();
		try {
			List<Area> areas = findBy(new AssembleCriteriaParamsCallback() {
				@Override
				public DetachedCriteria assembleParams(DetachedCriteria criteria) {
					criteria.add(Restrictions.eq("ParentCode", parentCode));
					criteria.add(Restrictions.eq("delete", Boolean.FALSE));
					return criteria;
				}
			},new Sort(
					new Sort.Order(Direction.ASC, "RegionCode"))
			);
			if(areas.size()>0) {
				for(Area a : areas) {
					findByParentCode(area, a.getRegionCode());
				}
			}
			map.put("cities_list", area);
			model.addAttribute("cities_list", area);
			retval.setData(map);
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return JSON.toJSONString(retval);
	}
	
	
}
