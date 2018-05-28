package com.travel.code.client.comment.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.travel.code.client.comment.service.CommentService;
import com.travel.controller.DefaultController;
import com.travel.entity.Comment;
import com.travel.entity.User;
import com.travel.service.Service;
import com.travel.support.CommonRequestAttributes;
import com.travel.support.CommonResponse;

@Controller
@RequestMapping("comment")
public class CommentController extends DefaultController<Comment, String> {

	@Autowired private CommentService commentService;
	
	@Override
	public Service<Comment, String> service() {
		return commentService;
	}

	@Override
	public String path() {
		// TODO Auto-generated method stub
		return null;
	}
	
	@ResponseBody
	@RequestMapping(value = "done")
	public String comment(CommonRequestAttributes attributes, Model model, Comment entity, @RequestParam String noteid) {
		HttpSession session = attributes.getRequest().getSession();
		User user = (User)session.getAttribute("user");
		CommonResponse retval = commentService.comment(attributes, model, entity, noteid, user);
		return JSON.toJSONString(retval);
	}

}
