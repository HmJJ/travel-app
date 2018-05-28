package com.travel.code.client.food.service;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.travel.entity.Food;
import com.travel.page.Sort;
import com.travel.page.Sort.Direction;
import com.travel.service.DefaultService;

@Service
@Transactional(rollbackFor = Exception.class)
public class FoodService extends DefaultService<Food, String> {
	
	public List<Food> findByCityCode(String cityCode){
		List<Food> foods = new ArrayList<>();
		try {
			foods = findBy(new AssembleCriteriaParamsCallback() {
				
				@Override
				public DetachedCriteria assembleParams(DetachedCriteria criteria) {
					criteria.add(Restrictions.eq("cityCode", cityCode));
					criteria.add(Restrictions.eq("delete", Boolean.FALSE));
					return criteria;
				}
			}, new Sort(new Sort.Order(Direction.DESC, "hotlevel"),
						new Sort.Order(Direction.DESC, "createdate")));
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return foods;
	}
	
	public List<Food> findByProvinceCode(String provinceCode){
		List<Food> foods = new ArrayList<>();
		try {
			foods = findBy(new AssembleCriteriaParamsCallback() {
				
				@Override
				public DetachedCriteria assembleParams(DetachedCriteria criteria) {
					criteria.add(Restrictions.eq("provinceCode", provinceCode));
					criteria.add(Restrictions.eq("delete", Boolean.FALSE));
					return criteria;
				}
			}, new Sort(new Sort.Order(Direction.DESC, "hotlevel"),
					new Sort.Order(Direction.DESC, "createdate")));
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return foods;
	}
	
	public List<Food> findByAttraction(String attraction){
		List<Food> foods = new ArrayList<>();
		try {
			foods = findBy(new AssembleCriteriaParamsCallback() {
				
				@Override
				public DetachedCriteria assembleParams(DetachedCriteria criteria) {
					criteria.add(Restrictions.eq("attractionid", attraction));
					criteria.add(Restrictions.eq("delete", Boolean.FALSE));
					return criteria;
				}
			}, new Sort(new Sort.Order(Direction.DESC, "hotlevel"),
						new Sort.Order(Direction.DESC, "createdate")));
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return foods;
	}
	
}
