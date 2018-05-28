package com.travel.code.client.atttraction.service;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.travel.entity.Attraction;
import com.travel.page.Sort;
import com.travel.page.Sort.Direction;
import com.travel.service.DefaultService;
import com.travel.utils.StringUtils;

@Service
@Transactional(rollbackFor = Exception.class)
public class AttractionService extends DefaultService<Attraction, String> {
	
	public List<Attraction> findByCityCode(String cityCode){
		List<Attraction> attrs = new ArrayList<>();
		try {
			attrs = findBy(new AssembleCriteriaParamsCallback() {
				
				@Override
				public DetachedCriteria assembleParams(DetachedCriteria criteria) {
					criteria.add(Restrictions.eq("cityCode", cityCode));
					criteria.add(Restrictions.eq("delete", Boolean.FALSE));
					return criteria;
				}
			}, new Sort(new Sort.Order(Direction.DESC, "createdate")));
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return attrs;
	}
	
	public List<Attraction> findByProvinceCode(String provinceCode){
		List<Attraction> attrs = new ArrayList<>();
		try {
			attrs = findBy(new AssembleCriteriaParamsCallback() {
				
				@Override
				public DetachedCriteria assembleParams(DetachedCriteria criteria) {
					criteria.add(Restrictions.eq("provinceCode", provinceCode));
					criteria.add(Restrictions.eq("delete", Boolean.FALSE));
					return criteria;
				}
			}, new Sort(new Sort.Order(Direction.DESC, "createdate")));
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return attrs;
	}
	
	public List<Attraction> findByType(String code, String searchtype){
		List<Attraction> attrs = new ArrayList<>();
		if(StringUtils.isBlank(searchtype)) {
			searchtype = "hot_level";
		}
		try {
			attrs = findBy(new AssembleCriteriaParamsCallback() {
				
				@Override
				public DetachedCriteria assembleParams(DetachedCriteria criteria) {
					criteria.add(Restrictions.eq("cityCode", code));
					criteria.add(Restrictions.eq("delete", Boolean.FALSE));
					return criteria;
				}
			}, new Sort(new Sort.Order(Direction.DESC, searchtype),
						new Sort.Order(Direction.DESC, "createdate")));
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		if(attrs.isEmpty()) {
			try {
				attrs = findBy(new AssembleCriteriaParamsCallback() {
					
					@Override
					public DetachedCriteria assembleParams(DetachedCriteria criteria) {
						criteria.add(Restrictions.eq("provinceCode", code));
						criteria.add(Restrictions.eq("delete", Boolean.FALSE));
						return criteria;
					}
				}, new Sort(new Sort.Order(Direction.DESC, searchtype),
							new Sort.Order(Direction.DESC, "createdate")));
			} catch (Exception e) {
				logger.error(e.getMessage());
			}
		}
		return attrs;
	}
	
}
