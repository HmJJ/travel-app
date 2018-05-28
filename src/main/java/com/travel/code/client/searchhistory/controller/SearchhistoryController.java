package com.travel.code.client.searchhistory.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.travel.code.client.searchhistory.service.SearchhistoryService;
import com.travel.controller.DefaultController;
import com.travel.entity.Area;
import com.travel.entity.History;
import com.travel.entity.User;
import com.travel.service.Service;
import com.travel.support.CommonRequestAttributes;

@Controller
@RequestMapping("searchhistory")
public class SearchhistoryController extends DefaultController<History, String> {

	@Autowired private SearchhistoryService searchhistoryService;
	
	@Override
	public Service<History, String> service() {
		return searchhistoryService;
	}

	@Override
	public String path() {
		return "";
	}
	
	@RequestMapping(value="clear")
	@ResponseBody
	public Object clear(CommonRequestAttributes attributes, Model model) {
		HttpSession session = attributes.getRequest().getSession();
		User user = (User)session.getAttribute("user");
		searchhistoryService.clear(user);
		List<Area> historys = searchhistoryService.searchHistory(attributes, model);
		if(historys != null && !historys.isEmpty()) {
			model.addAttribute("historys", historys);
		}
		List<Area> hots = searchhistoryService.hotSearch(attributes, model);
		if(hots != null && !historys.isEmpty()) {
			model.addAttribute("hots", hots);
		}
		Map<String, String> map = new HashMap<String, String>();
		map.put("message", "success");
		return map;
	}

}
