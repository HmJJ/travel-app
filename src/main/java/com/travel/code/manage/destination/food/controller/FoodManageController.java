package com.travel.code.manage.destination.food.controller;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alibaba.fastjson.JSON;
import com.travel.code.manage.destination.food.service.FoodManageService;
import com.travel.configure.DefaultMappingConstants;
import com.travel.controller.DefaultController;
import com.travel.entity.Food;
import com.travel.page.Page;
import com.travel.page.PageRequest;
import com.travel.page.Sort;
import com.travel.page.Sort.Direction;
import com.travel.service.Service;
import com.travel.service.Service.AssembleCriteriaParamsCallback;
import com.travel.support.CommonRequestAttributes;
import com.travel.support.CommonResponse;

@Controller
@RequestMapping("foodManage")
public class FoodManageController extends DefaultController<Food, String> {

	@Autowired private FoodManageService foodManageService;
	
	@Override
	public Service<Food, String> service() {
		return foodManageService;
	}

	@Override
	public String path() {
		return "destination/food/";
	}
	
	@RequestMapping(value = {DefaultMappingConstants.SEARCH})
	public String attractionSearch(CommonRequestAttributes attributes, Model model, final Food entity) {
		searchhandle(attributes, model, entity);
		return path()+"search";
	}
	
	protected void searchhandle(CommonRequestAttributes attributes, Model model, final Food entity) {
		try {
			Page< Food > page = foodManageService.pageBy(new AssembleCriteriaParamsCallback() {
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
	public String commit(CommonRequestAttributes attributes, Model model, Food entity) throws Exception {
		commithandler(attributes, model, entity);
		return super.commit(attributes, model, entity);
	}
	
	public String commithandler(CommonRequestAttributes attributes, Model model, Food entity) {
		CommonResponse retval = foodManageService.commithandler(attributes, model, entity);
		return JSON.toJSONString(retval);
	}

}
