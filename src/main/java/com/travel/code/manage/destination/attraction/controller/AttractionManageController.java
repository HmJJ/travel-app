package com.travel.code.manage.destination.attraction.controller;

import java.util.HashMap;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.travel.code.client.destination.service.DestinationService;
import com.travel.code.manage.destination.attraction.service.AttractionManageService;
import com.travel.configure.DefaultMappingConstants;
import com.travel.controller.DefaultController;
import com.travel.entity.Area;
import com.travel.entity.Attraction;
import com.travel.page.Page;
import com.travel.page.PageRequest;
import com.travel.page.Sort;
import com.travel.page.Sort.Direction;
import com.travel.service.Service;
import com.travel.service.Service.AssembleCriteriaParamsCallback;
import com.travel.support.CommonRequestAttributes;
import com.travel.support.CommonResponse;

@Controller
@RequestMapping("attractionManage")
public class AttractionManageController extends DefaultController<Attraction, String> {

	@Autowired
	private AttractionManageService attractionManageService;
	@Autowired
	private DestinationService destinationService;

	@Override
	public Service<Attraction, String> service() {
		return attractionManageService;
	}

	@Override
	public String path() {
		return "destination/attraction/";
	}

	@RequestMapping(value = { DefaultMappingConstants.SEARCH })
	public String attractionSearch(CommonRequestAttributes attributes, Model model, final Attraction entity) {
		searchhandle(attributes, model, entity);
		return path() + "search";
	}

	protected void searchhandle(CommonRequestAttributes attributes, Model model, final Attraction entity) {
		try {
			Page<Attraction> page = attractionManageService.pageBy(new AssembleCriteriaParamsCallback() {
				@Override
				public DetachedCriteria assembleParams(DetachedCriteria criteria) {
					criteria.add(Restrictions.eq("delete", Boolean.FALSE));
					return criteria;
				}
			}, new Sort(new Sort.Order(Direction.DESC, "createdate")),
					new PageRequest(attributes.getPageindex(), attributes.getPagesize()));
			model.addAttribute(ENTITY, entity);
			model.addAttribute(PAGE, page);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
	}

	@Override
	protected void createhandler(CommonRequestAttributes attributes, Model model) {
		model.addAttribute("province_list", new HashMap<>());
		model.addAttribute("cities_list", new HashMap<>());
		super.createhandler(attributes, model);
	}

	@Override
	protected void modifyhandler(CommonRequestAttributes attributes, Model model, String id) {
//		model = getProvinceAndCity(model, id);
		super.modifyhandler(attributes, model, id);
	}

	@Override
	public String commit(CommonRequestAttributes attributes, Model model, Attraction entity) throws Exception {
		commithandler(attributes, model, entity);
		return super.commit(attributes, model, entity);
	}

	public String commithandler(CommonRequestAttributes attributes, Model model, Attraction entity) {
		CommonResponse retval = attractionManageService.commithandler(attributes, model, entity);
		return JSON.toJSONString(retval);
	}

	public Model getProvinceAndCity(Model model, String id) {
		Attraction attr = attractionManageService.findBy(id);
		Area a = destinationService.findByRegionCode(attr.getCityCode());
		Area a1 = null;
		Area a2 = null;
		do {
			a2 = destinationService.findByRegionCode(a.getParentCode());
			if (a2 != null) {
				a1 = a2;
			}
		} while (a2 == null);	

		model.addAttribute("province", a1);
		model.addAttribute("city", a);
		return model;
	}

	@ResponseBody
	@RequestMapping(value="attr")
	public String findAttr(CommonRequestAttributes attributes, Model model, @RequestParam String regionCode) {
		return JSON.toJSONString(attractionManageService.findByCityCode(attributes, model, regionCode));
	}

}
