package com.travel.code.manage.destination.attraction.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;

import com.travel.code.client.destination.service.DestinationService;
import com.travel.entity.Attraction;
import com.travel.page.Sort;
import com.travel.page.Sort.Direction;
import com.travel.service.DefaultService;
import com.travel.support.CommonRequestAttributes;
import com.travel.support.CommonResponse;
import com.travel.utils.BeanUtils;

@Service
@Transactional(rollbackFor = Exception.class)
public class AttractionManageService extends DefaultService<Attraction, String> {
	
	@Autowired private DestinationService destinationService;

	@Transactional(readOnly = false)
	public CommonResponse commithandler(CommonRequestAttributes attributes, Model model, Attraction entity) {
		CommonResponse retval = new CommonResponse(false);
		try {
			Attraction temp = findBy(entity.getId());
			if (temp == null) {
				temp = new Attraction();
				BeanUtils.copyProperties(temp, entity);
			}
			temp.setProvinceCode(entity.getProvinceCode().toString());
			temp.setProvinceName(destinationService.findByRegionCode(entity.getProvinceCode())==null?"":destinationService.findByRegionCode(entity.getProvinceCode()).getRegionName().toString());
			temp.setCityCode(entity.getCityCode().toString());
			temp.setCityName(destinationService.findByRegionCode(entity.getCityCode())==null?"":destinationService.findByRegionCode(entity.getCityCode()).getRegionName().toString());
			temp.setPeoplelevel(entity.getPeoplelevel());
			temp.setHotlevel(entity.getHotlevel());
			temp.setExpenselevel(entity.getExpenselevel());
			temp.setTrafficlevel(entity.getTrafficlevel());
			temp = merge(temp);
			retval.setResult(true);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return retval;
	}
	
	public List<Attraction> findByRegionCode(String regionCode){
		List<Attraction> attrs = new ArrayList<>();
		try {
			attrs = findBy(new AssembleCriteriaParamsCallback() {
				@Override
				public DetachedCriteria assembleParams(DetachedCriteria criteria) {
					criteria.add(Restrictions.eq("regionCode", regionCode));
					criteria.add(Restrictions.eq("delete", Boolean.FALSE));
					return criteria;
				}
			});
		}catch(Exception e){
			e.printStackTrace();
		}
		return attrs;
	}
	
	public CommonResponse findByCityCode(CommonRequestAttributes attributes, Model model, String regionCode){
		CommonResponse retval = new CommonResponse(false);
		Map<String, Object> map = new HashMap<>();
		List<Attraction> attrs = new ArrayList<>();
		try {
			attrs = findBy(new AssembleCriteriaParamsCallback() {
				@Override
				public DetachedCriteria assembleParams(DetachedCriteria criteria) {
					criteria.add(Restrictions.eq("cityCode", regionCode));
					criteria.add(Restrictions.eq("delete", Boolean.FALSE));
					return criteria;
				}
			});
			map.put("attrs_list", attrs);
			model.addAttribute("attrs_list", attrs);
			retval.setData(map);
		}catch(Exception e){
			e.printStackTrace();
		}
		return retval;
	}
	
	public Model advice(CommonRequestAttributes attributes, Model model, String regionCode){
		CommonResponse retval = new CommonResponse(false);
		Map<String, Object> map = new HashMap<>();
		List<Attraction> hot = new ArrayList<>();
		List<Attraction> people = new ArrayList<>();
		List<Attraction> expense = new ArrayList<>();
		List<Attraction> traffic = new ArrayList<>();
		try {
			hot = findBy(new AssembleCriteriaParamsCallback() {
				@Override
				public DetachedCriteria assembleParams(DetachedCriteria criteria) {
					criteria.add(Restrictions.eq("cityCode", regionCode));
					criteria.add(Restrictions.eq("delete", Boolean.FALSE));
					return criteria;
				}
			},new Sort(new Sort.Order(Direction.DESC, "hotlevel")));
			if(hot.isEmpty()) {
				hot = findBy(new AssembleCriteriaParamsCallback() {
					@Override
					public DetachedCriteria assembleParams(DetachedCriteria criteria) {
						criteria.add(Restrictions.eq("provinceCode", regionCode));
						criteria.add(Restrictions.eq("delete", Boolean.FALSE));
						return criteria;
					}
				},new Sort(new Sort.Order(Direction.DESC, "hotlevel")));
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		try {
			people = findBy(new AssembleCriteriaParamsCallback() {
				@Override
				public DetachedCriteria assembleParams(DetachedCriteria criteria) {
					criteria.add(Restrictions.eq("cityCode", regionCode));
					criteria.add(Restrictions.eq("delete", Boolean.FALSE));
					return criteria;
				}
			},new Sort(new Sort.Order(Direction.ASC, "peoplelevel")));
			if(people.isEmpty()) {
				people = findBy(new AssembleCriteriaParamsCallback() {
					@Override
					public DetachedCriteria assembleParams(DetachedCriteria criteria) {
						criteria.add(Restrictions.eq("provinceCode", regionCode));
						criteria.add(Restrictions.eq("delete", Boolean.FALSE));
						return criteria;
					}
				},new Sort(new Sort.Order(Direction.ASC, "peoplelevel")));
			}
		}catch(Exception e){
			e.printStackTrace();
		}try {
			expense = findBy(new AssembleCriteriaParamsCallback() {
				@Override
				public DetachedCriteria assembleParams(DetachedCriteria criteria) {
					criteria.add(Restrictions.eq("cityCode", regionCode));
					criteria.add(Restrictions.eq("delete", Boolean.FALSE));
					return criteria;
				}
			},new Sort(new Sort.Order(Direction.ASC, "expenselevel")));
			if(expense.isEmpty()) {
				expense = findBy(new AssembleCriteriaParamsCallback() {
					@Override
					public DetachedCriteria assembleParams(DetachedCriteria criteria) {
						criteria.add(Restrictions.eq("provinceCode", regionCode));
						criteria.add(Restrictions.eq("delete", Boolean.FALSE));
						return criteria;
					}
				},new Sort(new Sort.Order(Direction.ASC, "expenselevel")));
			}
		}catch(Exception e){
			e.printStackTrace();
		}try {
			traffic = findBy(new AssembleCriteriaParamsCallback() {
				@Override
				public DetachedCriteria assembleParams(DetachedCriteria criteria) {
					criteria.add(Restrictions.eq("cityCode", regionCode));
					criteria.add(Restrictions.eq("delete", Boolean.FALSE));
					return criteria;
				}
			},new Sort(new Sort.Order(Direction.DESC, "trafficlevel")));
			if(traffic.isEmpty()) {
				traffic = findBy(new AssembleCriteriaParamsCallback() {
					@Override
					public DetachedCriteria assembleParams(DetachedCriteria criteria) {
						criteria.add(Restrictions.eq("provinceCode", regionCode));
						criteria.add(Restrictions.eq("delete", Boolean.FALSE));
						return criteria;
					}
				},new Sort(new Sort.Order(Direction.DESC, "trafficlevel")));
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		
		hot = getPreFive(hot);
		people = getPreFive(people);
		expense = getPreFive(expense);
		traffic = getPreFive(traffic);
		map.put("hot_advice", hot);
		map.put("people_advice", people);
		map.put("expense_advice", expense);
		map.put("traffic_advice", traffic);
		retval.setData(map);
		model.addAttribute("hot_advice", hot);
		model.addAttribute("people_advice", people);
		model.addAttribute("expense_advice", expense);
		model.addAttribute("traffic_advice", traffic);
		return model;
	}
	
	public List<Attraction> getPreFive(List<Attraction> attr){
		List<Attraction> preattr = new ArrayList<>();
		if(attr.size() <= 5) {
			preattr = attr;
		}else {
			for(int i = 0;i < attr.size();i++) {
				preattr.add(attr.get(i));
				if(i>4) {
					break;
				}
			}
		}
		return preattr;
	}

}
