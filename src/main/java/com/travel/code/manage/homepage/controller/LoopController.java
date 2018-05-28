package com.travel.code.manage.homepage.controller;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alibaba.fastjson.JSON;
import com.travel.code.manage.homepage.service.LoopService;
import com.travel.configure.DefaultMappingConstants;
import com.travel.controller.DefaultController;
import com.travel.entity.Image;
import com.travel.page.Page;
import com.travel.page.PageRequest;
import com.travel.page.Sort;
import com.travel.page.Sort.Direction;
import com.travel.service.Service;
import com.travel.service.Service.AssembleCriteriaParamsCallback;
import com.travel.support.CommonRequestAttributes;
import com.travel.support.CommonResponse;

@Controller
@RequestMapping("loop")
public class LoopController extends DefaultController<Image, String> {

	@Autowired private LoopService loopService;
	
	@Override
	public Service<Image, String> service() {
		return loopService;
	}

	@Override
	public String path() {
		return "homepage/loop/";
	}
	
	@RequestMapping(value = {DefaultMappingConstants.SEARCH})
	public String loopSearch(CommonRequestAttributes attributes, Model model, final Image entity) {
		searchhandle(attributes, model, entity);
		return path()+"search";
	}
	
	protected void searchhandle(CommonRequestAttributes attributes, Model model, final Image entity) {
		try {
			Page< Image > page = loopService.pageBy(new AssembleCriteriaParamsCallback() {
					@Override
					public DetachedCriteria assembleParams(DetachedCriteria criteria) {
						criteria.add(Restrictions.eq("delete", Boolean.FALSE));
						return criteria;
					}
				},
				new Sort(
					new Sort.Order(Direction.DESC, "startdate")),
				new PageRequest(attributes.getPageindex(), attributes.getPagesize())
			);
			model.addAttribute(ENTITY, entity);
			model.addAttribute(PAGE, page);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
	}

	@Override
	protected void createhandler(CommonRequestAttributes attributes, Model model) {
		// TODO Auto-generated method stub
		super.createhandler(attributes, model);
	}
	
	@Override
	protected void modifyhandler(CommonRequestAttributes attributes, Model model, String id) {
		// TODO Auto-generated method stub
		super.modifyhandler(attributes, model, id);
	}
	
	@Override
	public String commit(CommonRequestAttributes attributes, Model model, Image entity) throws Exception {
		// TODO Auto-generated method stub
		return super.commit(attributes, model, entity);
	}
	
	public String commithandler(CommonRequestAttributes attributes, Model model, Image entity) {
		CommonResponse retval = loopService.commithandler(attributes, model, entity);
		return JSON.toJSONString(retval);
	}

}
