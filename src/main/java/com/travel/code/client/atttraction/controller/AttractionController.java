package com.travel.code.client.atttraction.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.travel.code.client.atttraction.service.AttractionService;
import com.travel.code.client.food.service.FoodService;
import com.travel.code.manage.destination.attraction.service.AttractionManageService;
import com.travel.controller.DefaultController;
import com.travel.entity.Area;
import com.travel.entity.Attraction;
import com.travel.entity.Food;
import com.travel.service.Service;
import com.travel.support.CommonRequestAttributes;

@Controller
@RequestMapping("attraction")
public class AttractionController extends DefaultController<Attraction, String> {

	@Autowired private AttractionService attractionService;
	@Autowired
	private AttractionManageService attractionManageService;
	@Autowired private FoodService foodService;
	
	@Override
	public Service<Attraction, String> service() {
		return attractionService;
	}

	@Override
	public String path() {
		return "destination/";
	}
	
	@RequestMapping(value="routes_advice")
	public String routes(CommonRequestAttributes attributes, Model model) {
		HttpSession session = attributes.getRequest().getSession();
		Area area = (Area)session.getAttribute("destination");
		model = attractionManageService.advice(attributes, model, area.getRegionCode());
		
		return path()+"routes_advices";
	}
	
	@RequestMapping(value="foods_advice")
	public String foods(CommonRequestAttributes attributes, Model model) {
		HttpSession session = attributes.getRequest().getSession();
		Area area = (Area)session.getAttribute("destination");
		List<Food> hot_foods = new ArrayList<>();
		hot_foods = foodService.findByCityCode(area.getRegionCode());
		if(hot_foods == null || hot_foods.isEmpty()) {
			hot_foods = foodService.findByProvinceCode(area.getRegionCode());			
		}
		model.addAttribute("hot_foods", hot_foods);
		return path()+"foods_advices";
	}

}
