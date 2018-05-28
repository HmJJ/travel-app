package com.travel.code.client.commom.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.travel.code.client.searchhistory.service.SearchhistoryService;
import com.travel.controller.DefaultController;
import com.travel.entity.Area;
import com.travel.entity.User;
import com.travel.service.Service;
import com.travel.support.CommonRequestAttributes;

@Controller
@RequestMapping("commom")
public class CommomController extends DefaultController<User, String> {

	@Autowired private SearchhistoryService searchhistoryService;
	
	@Override
	public Service<User, String> service() {
		return null;
	}

	@Override
	public String path() {
		return "";
	}

	@RequestMapping(value="search")
	public String toSearch(CommonRequestAttributes attributes, Model model) {
		List<Area> historys = searchhistoryService.searchHistory(attributes, model);
		if(historys != null && !historys.isEmpty()) {
			model.addAttribute("historys", historys);
		}
		List<Area> hots = searchhistoryService.hotSearch(attributes, model);
		if(hots != null && !historys.isEmpty()) {
			model.addAttribute("hots", hots);
		}
		
		return "commom/search";
	}

	@RequestMapping(value="wait")
	public String toWait(CommonRequestAttributes attributes, Model model) {
		
		return "waiting";
	}

	
}
