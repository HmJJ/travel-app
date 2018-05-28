package com.travel.code.manage.commom.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.travel.code.manage.commom.service.InitAreaInfoService;
import com.travel.controller.DefaultController;
import com.travel.entity.Area;
import com.travel.service.Service;

@Controller
@RequestMapping("init")
public class InitAreaInfoController extends DefaultController<Area, String> {

	@Autowired private InitAreaInfoService initAreaInfoService;

	@Override
	public Service<Area, String> service() {
		return initAreaInfoService;
	}

	@Override
	public String path() {
		// TODO Auto-generated method stub
		return null;
	}
	
	@ResponseBody
	@RequestMapping(value="info")
	public String initInfo() {
		Map<Object, Object> map = new HashMap<>();
		initAreaInfoService.initInfo();
		map.put("message", "success");
		return JSON.toJSONString(map);
	}

}
