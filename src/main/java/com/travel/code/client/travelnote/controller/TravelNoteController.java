package com.travel.code.client.travelnote.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.travel.code.client.travelnote.service.TravelNoteService;
import com.travel.configure.DefaultMappingConstants;
import com.travel.controller.DefaultController;
import com.travel.entity.TravelNote;
import com.travel.entity.User;
import com.travel.service.Service;
import com.travel.support.CommonRequestAttributes;
import com.travel.support.CommonResponse;

@Controller
@RequestMapping("travelnote")
public class TravelNoteController extends DefaultController<TravelNote, String> {

	@Autowired private TravelNoteService travelNoteService;
	
	@Override
	public Service<TravelNote, String> service() {
		return travelNoteService;
	}

	@Override
	public String path() {
		return "travelnote/";
	}
	
	@RequestMapping(value = {DefaultMappingConstants.SEARCH})
	public String Search(CommonRequestAttributes attributes, Model model) {
		model = searchhandle(attributes, model);
		return path()+"search";
	}
	
	protected Model searchhandle(CommonRequestAttributes attributes, Model model) {
		HttpSession session = attributes.getRequest().getSession();
		User user = (User)session.getAttribute("user");
		List<TravelNote> travelnotes = travelNoteService.findByUser(attributes, model, user);
		if(travelnotes != null && !travelnotes.isEmpty()) {
			model.addAttribute("travelnotes", travelnotes);
		}
		return model;
	}
	
	@RequestMapping(value = {DefaultMappingConstants.DETAIL})
	public String attractionSearch(CommonRequestAttributes attributes, Model model, @RequestParam String id) {
		model = detailhandle(attributes, model, id);
		return path()+"detail";
	}
	
	protected Model detailhandle(CommonRequestAttributes attributes, Model model, final String id) {
		try {
			TravelNote entity = travelNoteService.findBy(id);
			model = travelNoteService.addView(model, entity);
			model.addAttribute(ENTITY, entity);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return model;
	}
	
	@ResponseBody
	@RequestMapping(value = DefaultMappingConstants.COMMIT, method = RequestMethod.POST)
	public String commit(CommonRequestAttributes attributes, Model model, TravelNote entity) throws Exception {
		return commithandler(attributes, model, entity);
//		return super.commit(attributes, model, entity);
	}

	public String commithandler(CommonRequestAttributes attributes, Model model, TravelNote entity) {
		CommonResponse retval = travelNoteService.commithandler(attributes, model, entity);
		return JSON.toJSONString(retval);
	}
	
}
