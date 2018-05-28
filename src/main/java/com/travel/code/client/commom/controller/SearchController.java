package com.travel.code.client.commom.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.travel.code.client.commom.service.SearchService;
import com.travel.controller.DefaultController;
import com.travel.entity.Area;
import com.travel.service.Service;
import com.travel.support.CommonRequestAttributes;
import com.travel.support.CommonResponse;

@Controller
@RequestMapping("search")
public class SearchController extends DefaultController<Area, String> {

	@Autowired private SearchService SearchService;
	
	@Override
	public Service<Area, String> service() {
		return SearchService;
	}

	@Override
	public String path() {
		// TODO Auto-generated method stub
		return null;
	}
	
	@SuppressWarnings("unchecked")
	@ResponseBody
	@RequestMapping(value="place")
	public String findPlace(CommonRequestAttributes attributes, Model model, @RequestParam String searchtext) {
		CommonResponse retval = SearchService.findAllPlace(searchtext);
		Map<String, Object> map = new HashMap<>();
		map = (Map<String, Object>) retval.getData();
		model.addAttribute("result_list", map.get("result_list"));
		return JSON.toJSONString(retval);
	}
	
	@RequestMapping(value="info")
	public String detail(CommonRequestAttributes attributes, Model model, @RequestParam Area area) {
		CommonResponse retval = SearchService.getDetailInfo(area);
		model.addAttribute(ENTITY, area);
		return JSON.toJSONString(retval);
	}

}
