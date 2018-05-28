package com.travel.code.manage.homepage.service;

import java.util.Date;
import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;

import com.travel.entity.Menu;
import com.travel.page.Sort;
import com.travel.page.Sort.Direction;
import com.travel.service.DefaultService;
import com.travel.support.CommonRequestAttributes;
import com.travel.support.CommonResponse;
import com.travel.utils.BeanUtils;

@Service
@Transactional(rollbackFor = Exception.class)
public class MenuService extends DefaultService<Menu, String> {
	
	public Menu findBy(final String id) {
		List<Menu> mainPages = findBy(new AssembleCriteriaParamsCallback() {
			@Override
			public DetachedCriteria assembleParams(DetachedCriteria criteria) {
				criteria.add(Restrictions.eq("id", id));
				criteria.add(Restrictions.eq("delete", Boolean.FALSE));
				return criteria;
			}
		});
		if (mainPages == null || mainPages.isEmpty()) {
			return null;
		}
		return mainPages.get(0);
	}
	
	public Menu findByName(final String name) {
		List<Menu> mainPages = findBy(new AssembleCriteriaParamsCallback() {
			@Override
			public DetachedCriteria assembleParams(DetachedCriteria criteria) {
				criteria.add(Restrictions.eq("name", name));
				criteria.add(Restrictions.eq("delete", Boolean.FALSE));
				return criteria;
			}
		});
		if (mainPages == null || mainPages.isEmpty()) {
			return null;
		}
		return mainPages.get(0);
	}

	public Menu findBy(final Integer position, final String status, String type) {
		List<Menu> mainPages = findBy(new AssembleCriteriaParamsCallback() {
			@Override
			public DetachedCriteria assembleParams(DetachedCriteria criteria) {
				criteria.add(Restrictions.eq("type", type));
				criteria.add(Restrictions.eq("position", position));
				criteria.add(Restrictions.eq("status", status));
				criteria.add(Restrictions.eq("delete", Boolean.FALSE));
				return criteria;
			}
		});
		if (mainPages == null || mainPages.isEmpty()) {
			return null;
		}
		return mainPages.get(0);
	}
	
	public Integer findByType(String type){
		List<Menu> mainPages = findBy(new AssembleCriteriaParamsCallback() {
			@Override
			public DetachedCriteria assembleParams(DetachedCriteria criteria) {
				criteria.add(Restrictions.eq("type", type));
				criteria.add(Restrictions.isNotNull("name"));
				return criteria;
			}
		}, new Sort(
					new Sort.Order(Direction.DESC, "createdate")));
		if (mainPages == null || mainPages.isEmpty()) {
			return 1;
		}
		return mainPages.get(0).getPosition()+1;
	}
	
	
	@Transactional(readOnly = false)
	public CommonResponse commithandler(CommonRequestAttributes attributes, Model model, Menu entity) {
		CommonResponse retval = new CommonResponse(false);
		try {
			Menu temp = findByName(entity.getName());
			if (temp == null) {
				temp = new Menu();
				BeanUtils.copyProperties(temp, entity);
			}
			if(entity.getPosition()==null){
				temp.setPosition(findByType(entity.getType()));
			}else {
				temp.setPosition(entity.getPosition());
			}
			temp.setName(entity.getName());
			temp.setLinkUrl(entity.getLinkUrl());
			temp.setImgSrc(entity.getImgSrc());
			if(temp.getStatus()==null || "".equals(entity.getStatus())){
				temp.setStatus("1");
			}
			temp.setModifydate(new Date());
			temp = merge(temp);
			retval.setResult(true);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return retval;
	}
	
	@Transactional(readOnly = false)
	public CommonResponse commithandler2(CommonRequestAttributes attributes, Model model, String buttonName) {
		CommonResponse retval = new CommonResponse(false);
		try {
			Menu temp = findByName(buttonName);
			if("1".equals(temp.getStatus())){
				Menu old = findBy(temp.getPosition(), "2", temp.getType());
				if (old != null && old != temp) {
					old.setStatus("1");
					old.setModifydate(new Date());
					old = merge(old);
				}
				temp.setStatus("2");
			}else{
				temp.setStatus("1");
			}
			temp.setModifydate(new Date());
			temp = merge(temp);
			retval.setResult(true);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return retval;
	}
	
}
