package com.travel.code.manage.commom.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.travel.code.manage.commom.service.ManageService;
import com.travel.controller.DefaultController;
import com.travel.entity.User;
import com.travel.service.Service;
import com.travel.support.CommonRequestAttributes;

@Controller
@RequestMapping("manage")
public class ManageController extends DefaultController<User, String> {

	@Autowired private ManageService manageService;
	
	@Override
	public Service<User, String> service() {
		return manageService;
	}

	@Override
	public String path() {
		return "/";
	}
	
	@RequestMapping(value="")
	public String manage(CommonRequestAttributes attributes, Model model) {
		return path()+"index-manage";
	}
}
