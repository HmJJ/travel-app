package com.travel.code.client.travelnote.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;

import com.travel.code.client.atttraction.service.AttractionService;
import com.travel.code.client.destination.service.DestinationService;
import com.travel.entity.Area;
import com.travel.entity.Attraction;
import com.travel.entity.TravelNote;
import com.travel.entity.User;
import com.travel.page.Sort;
import com.travel.page.Sort.Direction;
import com.travel.service.DefaultService;
import com.travel.support.CommonRequestAttributes;
import com.travel.support.CommonResponse;
import com.travel.utils.BeanUtils;

@Service
@Transactional(rollbackFor = Exception.class)
public class TravelNoteService extends DefaultService<TravelNote, String>{

	@Autowired private DestinationService destinationService;
	@Autowired private AttractionService attractionService;
	
	public List<TravelNote> findByName(CommonRequestAttributes attributes, Model model, String destinationRegionName) {
		List<TravelNote> travelnotes = new ArrayList<>();
		try {
			travelnotes = findBy(new AssembleCriteriaParamsCallback() {
				
				@Override
				public DetachedCriteria assembleParams(DetachedCriteria criteria) {
					criteria.add(Restrictions.eq("cityName", destinationRegionName));
					criteria.add(Restrictions.eq("delete", Boolean.FALSE));
					return criteria;
				}
			}, new Sort(new Sort.Order(Direction.DESC, "createdate")));
			
			if(travelnotes.isEmpty()) {
				travelnotes = findBy(new AssembleCriteriaParamsCallback() {
					
					@Override
					public DetachedCriteria assembleParams(DetachedCriteria criteria) {
						criteria.add(Restrictions.eq("provinceName", destinationRegionName));
						criteria.add(Restrictions.eq("delete", Boolean.FALSE));
						return criteria;
					}
				}, new Sort(new Sort.Order(Direction.DESC, "createdate")));
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return travelnotes;
	}
	
	public List<TravelNote> findByAttraction(CommonRequestAttributes attributes, Model model, String attraction) {
		List<TravelNote> travelnotes = new ArrayList<>();
		try {
			travelnotes = findBy(new AssembleCriteriaParamsCallback() {
				
				@Override
				public DetachedCriteria assembleParams(DetachedCriteria criteria) {
					criteria.add(Restrictions.eq("attractionid", attraction));
					criteria.add(Restrictions.eq("delete", Boolean.FALSE));
					return criteria;
				}
			}, new Sort(new Sort.Order(Direction.DESC, "createdate")));
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return travelnotes;
	}
	
	public List<TravelNote> findByUser(CommonRequestAttributes attributes, Model model, User user) {
		List<TravelNote> travelnotes = new ArrayList<>();
		try {
			travelnotes = findBy(new AssembleCriteriaParamsCallback() {
				
				@Override
				public DetachedCriteria assembleParams(DetachedCriteria criteria) {
					criteria.add(Restrictions.eq("user", user));
					criteria.add(Restrictions.eq("delete", Boolean.FALSE));
					return criteria;
				}
			}, new Sort(new Sort.Order(Direction.DESC, "createdate")));
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return travelnotes;
	}

	public List<TravelNote> hotNotes(CommonRequestAttributes attributes, Model model) {
		List<TravelNote> travelnotes = new ArrayList<>();
		List<TravelNote> pretravelnotes = new ArrayList<>();
		try {
			pretravelnotes = findBy(new AssembleCriteriaParamsCallback() {
				
				@Override
				public DetachedCriteria assembleParams(DetachedCriteria criteria) {
					criteria.add(Restrictions.eq("delete", Boolean.FALSE));
					return criteria;
				}
			}, new Sort(new Sort.Order(Direction.DESC, "watchs")));
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		if(pretravelnotes != null && !pretravelnotes.isEmpty()) {
			for(TravelNote tn : pretravelnotes) {
				travelnotes.add(tn);
				if(travelnotes.size()>=3) {
					break;
				}
			}
		}
		travelnotes = maopao(travelnotes);
		return travelnotes;
	}
	
	public List<TravelNote> maopao(List<TravelNote> travelnote){
		for(int i=1;i<travelnote.size();i++) {
			for(int j=0;j<i;j++) {
				if(travelnote.get(i).getWatchs()>travelnote.get(j).getWatchs()) {
					TravelNote note = travelnote.get(j);
					travelnote.set(j, travelnote.get(i));
					travelnote.set(i, note);
				}
			}
		}
		return travelnote;
	}
	
	@Transactional(readOnly = false)
	public CommonResponse commithandler(CommonRequestAttributes attributes, Model model, TravelNote entity) {
		CommonResponse retval = new CommonResponse(false);
		HttpSession session = attributes.getRequest().getSession();
		User user = (User)session.getAttribute("user");
		try {
			TravelNote temp = null;
			if(entity.getId() != null) {
				temp = findBy(entity.getId());
			}
			if (temp == null) {
				temp = new TravelNote();
				BeanUtils.copyProperties(temp, entity);
			}
			temp.setUser(user);
			if(entity.getProvinceCode() != null) {
				temp.setProvinceCode(entity.getProvinceCode());
				Area area = destinationService.findByRegionCode(entity.getProvinceCode());
				temp.setProvinceName(area == null ? "" : area.getRegionName());
			}else {
				temp.setProvinceCode("");
				temp.setProvinceName("");
			}
			if(entity.getCityCode() != null) {
				temp.setCityCode(entity.getCityCode());
				Area area = destinationService.findByRegionCode(entity.getCityCode());
				temp.setCityName(area == null ? "" : area.getRegionName());
			}else {
				temp.setCityCode("");
				temp.setCityName("");
			}
			if(entity.getAttractionid() != null) {
				temp.setAttractionid(entity.getAttractionid());
				Attraction attr = attractionService.findBy(entity.getAttractionid());
				temp.setAttractionname(attr == null ? "" : attr.getName());
			}else {
				temp.setAttractionid("");
				temp.setAttractionname("");
			}
			temp.setTitle(entity.getTitle());
			temp.setContent(entity.getContent());
			temp.setModifydate(new Date());
			temp = merge(temp);
			retval.setResult(true);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return retval;
	}
	
	@Transactional(readOnly = false)
	public Model addView(Model model, TravelNote entity) {
		try {
			TravelNote temp = entity;
			temp.setWatchs(temp.getWatchs()+1);
			temp.setModifydate(new Date());
			temp = merge(temp);
			model.addAttribute("entity", temp);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return model;
	}

}
