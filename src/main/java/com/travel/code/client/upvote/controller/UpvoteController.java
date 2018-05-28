package com.travel.code.client.upvote.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.travel.code.client.upvote.service.UpvoteService;
import com.travel.controller.DefaultController;
import com.travel.entity.TravelNote;
import com.travel.entity.User;
import com.travel.service.Service;
import com.travel.support.CommonRequestAttributes;
import com.travel.support.CommonResponse;

@Controller
@RequestMapping("upvote")
public class UpvoteController extends DefaultController<TravelNote, String> {

	@Autowired private UpvoteService upvoteService;
	
	@Override
	public Service<TravelNote, String> service() {
		return upvoteService;
	}

	@Override
	public String path() {
		// TODO Auto-generated method stub
		return null;
	}
	
	@ResponseBody
	@RequestMapping(value = "done")
	public String upvote(CommonRequestAttributes attributes, Model model,@RequestParam String id) {
		HttpSession session = attributes.getRequest().getSession();
		User user = (User)session.getAttribute("user");
		CommonResponse retval = upvoteService.upvote(attributes, model, id, user);
		return JSON.toJSONString(retval);
	}

}
