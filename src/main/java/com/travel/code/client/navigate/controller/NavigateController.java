package com.travel.code.client.navigate.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.travel.code.client.mine.service.LoginService;
import com.travel.code.client.navigate.service.NavigateService;
import com.travel.code.client.searchhistory.service.SearchhistoryService;
import com.travel.code.client.travelnote.service.TravelNoteService;
import com.travel.code.manage.homepage.service.MenuService;
import com.travel.controller.DefaultController;
import com.travel.entity.Area;
import com.travel.entity.Menu;
import com.travel.entity.TravelNote;
import com.travel.entity.User;
import com.travel.page.Page;
import com.travel.page.PageRequest;
import com.travel.page.Sort;
import com.travel.page.Sort.Direction;
import com.travel.service.Service;
import com.travel.support.CommonRequestAttributes;

@Controller
@RequestMapping("navigate")
public class NavigateController extends DefaultController<User, String> {

	@Autowired private NavigateService navigateService;
	@Autowired private MenuService menuService;
	@Autowired private LoginService loginService;
	@Autowired private SearchhistoryService searchhistoryService;
	@Autowired private TravelNoteService travelNoteService;
	
	@Override
	public Service<User, String> service() {
		return navigateService;
	}
	
	@Override
	public String path() {
		return "/";
	}
	
	@RequestMapping(value="")
	public String navigation(CommonRequestAttributes attributes, Model model, @RequestParam String page) {
		HttpSession session = attributes.getRequest().getSession();
		session.setAttribute("page", page);
		List<Area> historys = searchhistoryService.searchHistory(attributes, model);
		if(historys != null && !historys.isEmpty()) {
			model.addAttribute("historys", historys);
		}
		if(session.getAttribute("userflag") == null || "0".equals(session.getAttribute("userflag"))) {
			session.setAttribute("userflag", "0");
		}else {
			session.setAttribute("userflag", "1");
		}
		return path()+"navigation";
	}
	
	@RequestMapping(value="locationCity")
	@ResponseBody
	public Object locationCity(CommonRequestAttributes attributes, @RequestParam String country, @RequestParam String province, @RequestParam String city) {
		HttpSession session = attributes.getRequest().getSession();
		Area Province = navigateService.findByField(province);
		Area City = navigateService.findByField(city);
		session.setAttribute("LocationCityCode", City.getRegionCode());
		session.setAttribute("LocationCityName", City.getRegionName());
		session.setAttribute("LocationProvinceCode", Province.getRegionCode());
		session.setAttribute("LocationProvinceName", Province.getRegionName());
		session.setAttribute("destinationRegionName", "none");
		Map<String, String> data = new HashMap<>();
		data.put("message", "获取当前位置成功");	
		return data;
	}
	
	@RequestMapping(value="1")
	public String mainpage(CommonRequestAttributes attributes, Model model) {
		HttpSession session = attributes.getRequest().getSession();
		if(session.getAttribute("userflag") == null || "0".equals(session.getAttribute("userflag"))) {
			session.setAttribute("userflag", "0");
		}else {
			session.setAttribute("userflag", "1");
		}
		try {
			Page<Menu> btns = menuService.pageBy(new Service.AssembleCriteriaParamsCallback() {
				@Override
				public DetachedCriteria assembleParams(DetachedCriteria criteria) {
					criteria.add(Restrictions.eq("type", "1"));
					criteria.add(Restrictions.eq("status", "2"));
					criteria.add(Restrictions.eq("delete", Boolean.FALSE));
					return criteria;
				}
			}, new Sort(new Sort.Order(Direction.ASC, "position")),
					new PageRequest(attributes.getPageindex(), attributes.getPagesize()));
			
			if(loginService.isLogin(attributes)) {
				List<Area> historys = searchhistoryService.searchHistory(attributes, model);
				if(historys != null && !historys.isEmpty()) {
					model.addAttribute("historys", historys);
				}
			}
			
			//查询热门游记
			List<TravelNote> travelnotes = travelNoteService.hotNotes(attributes, model);
			if(travelnotes != null && !travelnotes.isEmpty()) {
				model.addAttribute("travelnotes", travelnotes);
			}
			
			model.addAttribute("btns", btns);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return "homepage/search";
	}
	
	@RequestMapping(value="2")
	public String destination(CommonRequestAttributes attributes, Model model) {
		HttpSession session = attributes.getRequest().getSession();
		try {
			Page<Menu> btns = menuService.pageBy(new Service.AssembleCriteriaParamsCallback() {
				@Override
				public DetachedCriteria assembleParams(DetachedCriteria criteria) {
					criteria.add(Restrictions.eq("type", "2"));
					criteria.add(Restrictions.eq("status", "2"));
					criteria.add(Restrictions.eq("delete", Boolean.FALSE));
					return criteria;
				}
			}, new Sort(new Sort.Order(Direction.ASC, "position")),
					new PageRequest(attributes.getPageindex(), attributes.getPagesize()));
			//查询该目的地游记
			String regionName = "";
			if("none".equals(session.getAttribute("destinationRegionName"))) {
				regionName = (String) session.getAttribute("LocationCityName");
			}else {
				regionName = (String) session.getAttribute("destinationRegionName");
			}
			session.setAttribute("LocationCityName", regionName);
			model.addAttribute("LocationCityName", regionName);
			List<TravelNote> travelnotes = travelNoteService.findByName(attributes, model, regionName);
			if(travelnotes != null && !travelnotes.isEmpty()) {
				model.addAttribute("travelnotes", travelnotes);
			}
			model.addAttribute("btns", btns);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return "destination/search";
	}
	
	@RequestMapping(value="3")
	public String hotel() {
		return "hotel/search";
	}
	
	@RequestMapping(value="4")
	public String notLogin(CommonRequestAttributes attributes, Model model) {
		try {
			Page<Menu> btns = menuService.pageBy(new Service.AssembleCriteriaParamsCallback() {
				@Override
				public DetachedCriteria assembleParams(DetachedCriteria criteria) {
					criteria.add(Restrictions.eq("type", "4"));
					criteria.add(Restrictions.eq("status", "2"));
					criteria.add(Restrictions.eq("delete", Boolean.FALSE));
					return criteria;
				}
			}, new Sort(new Sort.Order(Direction.ASC, "position")),
					new PageRequest(attributes.getPageindex(), attributes.getPagesize()));
			model.addAttribute("btns", btns);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return "mine/needLogin";
	}

}
