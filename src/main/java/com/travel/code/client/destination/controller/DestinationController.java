package com.travel.code.client.destination.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.travel.code.client.destination.service.DestinationService;
import com.travel.code.client.searchhistory.service.SearchhistoryService;
import com.travel.controller.DefaultController;
import com.travel.entity.Area;
import com.travel.entity.History;
import com.travel.entity.User;
import com.travel.service.Service;
import com.travel.support.CommonRequestAttributes;
import com.travel.support.CommonResponse;

@Controller
@RequestMapping("destination")
public class DestinationController extends DefaultController<Area, String> {

	@Autowired private DestinationService destinationService;
	@Autowired private SearchhistoryService searchhistoryService;
//	@Autowired private TravelNoteService travelNoteService;
	
	@Override
	public Service<Area, String> service() {
		return destinationService;
	}

	@Override
	public String path() {
		return "destination/";
	}
	
	@RequestMapping(value="check")
	public String check(CommonRequestAttributes attributes, Model model, @RequestParam String regionName, @RequestParam String regionCode) {
		HttpSession session = attributes.getRequest().getSession();
		User user = (User)session.getAttribute("user");
		//parent Area && Area
		Area area = destinationService.findByRegionCode(regionCode);
		Area parentarea = destinationService.findByRegionCode(area.getParentCode());
		session.setAttribute("destination", area);
		session.setAttribute("parent", parentarea);
		if(parentarea == null || "市辖区".equals(parentarea.getRegionName()) || "县".equals(parentarea.getRegionName())) {
			if(parentarea != null) {
				parentarea = destinationService.findByRegionCode(parentarea.getParentCode());
				session.setAttribute("parent", parentarea);
			}
		}
		//记录搜索历史
		History history = new History();
		history.setUserid(user.getId());
		history.setSearch_history(regionName);
		@SuppressWarnings("unused")
		CommonResponse retval = searchhistoryService.commithandler(attributes, model, history);
		session.setAttribute("destinationRegionName", regionName);
		
		return "redirect:/navigate?page=2";
	}


}
