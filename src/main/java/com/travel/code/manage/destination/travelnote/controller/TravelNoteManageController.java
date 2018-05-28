package com.travel.code.manage.destination.travelnote.controller;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alibaba.fastjson.JSON;
import com.travel.code.manage.destination.travelnote.service.TravelNoteManageService;
import com.travel.configure.DefaultMappingConstants;
import com.travel.controller.DefaultController;
import com.travel.entity.TravelNote;
import com.travel.page.Page;
import com.travel.page.PageRequest;
import com.travel.page.Sort;
import com.travel.page.Sort.Direction;
import com.travel.service.Service;
import com.travel.service.Service.AssembleCriteriaParamsCallback;
import com.travel.support.CommonRequestAttributes;
import com.travel.support.CommonResponse;

@Controller
@RequestMapping("travelnoteManage")
public class TravelNoteManageController extends DefaultController<TravelNote, String> {

	@Autowired private TravelNoteManageService travelNoteManageService;
	
	@Override
	public Service<TravelNote, String> service() {
		return travelNoteManageService;
	}

	@Override
	public String path() {
		return "destination/travelnote/";
	}
	
	@RequestMapping(value = {DefaultMappingConstants.SEARCH})
	public String attractionSearch(CommonRequestAttributes attributes, Model model, final TravelNote entity) {
		searchhandle(attributes, model, entity);
		return path()+"search";
	}
	
	protected void searchhandle(CommonRequestAttributes attributes, Model model, final TravelNote entity) {
		try {
			Page< TravelNote > page = travelNoteManageService.pageBy(new AssembleCriteriaParamsCallback() {
					@Override
					public DetachedCriteria assembleParams(DetachedCriteria criteria) {
						criteria.add(Restrictions.eq("delete", Boolean.FALSE));
						return criteria;
					}
				},
				new Sort(
					new Sort.Order(Direction.DESC, "createdate")),
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
		super.createhandler(attributes, model);
	}
	
	@Override
	protected void modifyhandler(CommonRequestAttributes attributes, Model model, String id) {
		super.modifyhandler(attributes, model, id);
	}
	
	@Override
	public String commit(CommonRequestAttributes attributes, Model model, TravelNote entity) throws Exception {
		commithandler(attributes, model, entity);
		return super.commit(attributes, model, entity);
	}
	
	public String commithandler(CommonRequestAttributes attributes, Model model, TravelNote entity) {
		CommonResponse retval = travelNoteManageService.commithandler(attributes, model, entity);
		return JSON.toJSONString(retval);
	}

}
