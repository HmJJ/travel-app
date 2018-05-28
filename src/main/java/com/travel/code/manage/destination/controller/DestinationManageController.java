package com.travel.code.manage.destination.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.travel.code.manage.destination.service.DestinationManageService;
import com.travel.controller.DefaultController;
import com.travel.entity.Area;
import com.travel.service.Service;

@Controller
@RequestMapping("destinationManage")
public class DestinationManageController extends DefaultController<Area, String> {

	@Autowired private DestinationManageService destinationManageService;
	
	@Override
	public Service<Area, String> service() {
		return destinationManageService;
	}

	@Override
	public String path() {
		// TODO Auto-generated method stub
		return null;
	}

	@ResponseBody
	@RequestMapping("province")
	public String findProvince(Model model) {
		return destinationManageService.findProvince(model);
	}

	@ResponseBody
	@RequestMapping("cities")
	public String findCities(Model model, @RequestParam String parentCode) {
		return destinationManageService.findCitys(model, parentCode);
	}

}
