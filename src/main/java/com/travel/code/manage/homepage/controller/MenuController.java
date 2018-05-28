package com.travel.code.manage.homepage.controller;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.travel.code.manage.homepage.service.MenuService;
import com.travel.configure.DefaultMappingConstants;
import com.travel.controller.DefaultController;
import com.travel.entity.Menu;
import com.travel.page.Page;
import com.travel.page.PageRequest;
import com.travel.page.Sort;
import com.travel.page.Sort.Direction;
import com.travel.service.Service;
import com.travel.service.Service.AssembleCriteriaParamsCallback;
import com.travel.support.CommonRequestAttributes;
import com.travel.support.CommonResponse;

@Controller
@RequestMapping("menu")
public class MenuController extends DefaultController<Menu, String> {

	@Autowired private MenuService menuService;
//	private static final String _fileupload_url = "upload/image";
	
	@Override
	public Service<Menu, String> service() {
		return menuService;
	}

	@Override
	public String path() {
		return "homepage/menu/";
	}
	
	@RequestMapping(value = {DefaultMappingConstants.SEARCH})
	public String menuSearch(CommonRequestAttributes attributes, Model model, final Menu entity) {
		searchhandle(attributes, model, entity);
		return path()+"search";
	}
	
	protected void searchhandle(CommonRequestAttributes attributes, Model model, final Menu entity) {
		try {
			Page< Menu > page = menuService.pageBy(new AssembleCriteriaParamsCallback() {
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
//		model.addAttribute("_fileupload_url", _fileupload_url);
		super.createhandler(attributes, model);
	}
	
	@Override
	protected void modifyhandler(CommonRequestAttributes attributes, Model model, String id) {
//		model.addAttribute("_fileupload_url", _fileupload_url);
		super.modifyhandler(attributes, model, id);
	}
	
	@Override
	public String commit(CommonRequestAttributes attributes, Model model, Menu entity) throws Exception {
		commithandler(attributes, model, entity);
		return super.commit(attributes, model, entity);
	}
	
	public String commithandler(CommonRequestAttributes attributes, Model model, Menu entity) {
		CommonResponse retval = menuService.commithandler(attributes, model, entity);
		return JSON.toJSONString(retval);
	}
	
	@ResponseBody
	@RequestMapping(value = "apply" + "/{name}", method = RequestMethod.POST)
	public String commit2(CommonRequestAttributes attributes, Model model, @PathVariable String name) throws Exception {
		return commithandler2(attributes, model, name);
	}
	
	protected String commithandler2(CommonRequestAttributes attributes, Model model, String name) {
		CommonResponse retval = menuService.commithandler2(attributes, model, name);
		return JSON.toJSONString(retval);
	}

}
