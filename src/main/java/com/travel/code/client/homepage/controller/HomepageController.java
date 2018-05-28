package com.travel.code.client.homepage.controller;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.travel.code.client.homepage.service.HomepageService;
import com.travel.code.client.searchhistory.service.SearchhistoryService;
import com.travel.code.manage.homepage.service.MenuService;
import com.travel.configure.DefaultMappingConstants;
import com.travel.controller.DefaultController;
import com.travel.entity.Area;
import com.travel.entity.Menu;
import com.travel.entity.User;
import com.travel.page.Page;
import com.travel.page.PageRequest;
import com.travel.page.Sort;
import com.travel.page.Sort.Direction;
import com.travel.service.Service;
import com.travel.support.CommonRequestAttributes;

@Controller
@RequestMapping("homepage")
public class HomepageController extends DefaultController<User, String> {

	@Autowired
	private HomepageService homepageService;
	@Autowired private MenuService menuService;
	@Autowired private SearchhistoryService searchhistoryService;
	
	@Override
	public Service<User, String> service() {
		return homepageService;
	}

	@Override
	public String path() {
		return "homepage/";
	}
	
	@RequestMapping(value= {"",DefaultMappingConstants.SEARCH})
	public String Homepage(CommonRequestAttributes attributes, Model model, final Menu entity) {
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
			
			User user = (User) attributes.getRequest().getSession().getAttribute("user");
			if(user != null) {
				List<Area> historys = searchhistoryService.searchHistory(attributes, model);
				if(historys != null && !historys.isEmpty()) {
					model.addAttribute("historys", historys);
				}
			}
			
			model.addAttribute("btns", btns);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return path()+"search";
	}
	

}
