package com.travel.code.client.food.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.travel.controller.DefaultController;
import com.travel.entity.Food;
import com.travel.service.Service;

@Controller
@RequestMapping("food")
public class FoodController extends DefaultController<Food, String> {

	@Override
	public Service<Food, String> service() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String path() {
		// TODO Auto-generated method stub
		return null;
	}

}
