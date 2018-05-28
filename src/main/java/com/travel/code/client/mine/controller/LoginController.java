package com.travel.code.client.mine.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.travel.code.client.comment.service.CommentService;
import com.travel.code.client.destination.service.DestinationService;
import com.travel.code.client.mine.service.LoginService;
import com.travel.code.client.travelnote.service.TravelNoteService;
import com.travel.code.manage.homepage.service.MenuService;
import com.travel.controller.DefaultController;
import com.travel.entity.Area;
import com.travel.entity.Comment;
import com.travel.entity.Menu;
import com.travel.entity.TravelNote;
import com.travel.entity.User;
import com.travel.page.Page;
import com.travel.page.PageRequest;
import com.travel.page.Sort;
import com.travel.page.Sort.Direction;
import com.travel.service.Service;
import com.travel.support.CommonRequestAttributes;
import com.travel.support.CommonResponse;
import com.travel.support.Foot;

@Controller
@RequestMapping("needLogin")
public class LoginController extends DefaultController<User, String> {

	@Autowired private LoginService loginService;
	@Autowired private MenuService menuService;
	@Autowired private TravelNoteService travelNoteService;
	@Autowired private CommentService commentService;
	@Autowired private DestinationService destinationService;
	
	@Override
	public Service<User, String> service() {
		return loginService;
	}

	@Override
	public String path() {
		return "mine/";
	}
	
	@RequestMapping(value="")
	public String toMine(CommonRequestAttributes attributes, Model model) {
		HttpSession session = attributes.getRequest().getSession();
		if(session.getAttribute("userflag") == null) {
			session.setAttribute("userflag", "0");
		}
		
		try {
			Page<Menu> btns = menuService.pageBy(new Service.AssembleCriteriaParamsCallback() {
				@Override
				public DetachedCriteria assembleParams(DetachedCriteria criteria) {
					criteria.add(Restrictions.eq("type", "4"));
					criteria.add(Restrictions.eq("status", "2"));
					criteria.add(Restrictions.eq("delete", Boolean.FALSE));
					return criteria;
				}
			}, new Sort(new Sort.Order(Direction.ASC, "position")),
					new PageRequest(attributes.getPageindex(), attributes.getPagesize()));
			model.addAttribute("btns", btns);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		
		return path()+"needLogin";
	}
	
	@RequestMapping(value="toLogin")
	public String toLogin() {
		return path()+"login";
	}
	
	@RequestMapping(value="not_login")
	public String notLogin() {
		return path()+"not_login";
	}
	@RequestMapping(value="is_login")
	public String isLogin() {
		return path()+"is_login";
	}
	
	@RequestMapping(value="login")
	@ResponseBody
	public Object login(CommonRequestAttributes attributes, Model model, final User entity, @RequestParam String phone, @RequestParam String password) {
		String flag = loginService.login(attributes, phone, password);
		Map<String, String> data = new HashMap<>();
		if("0".equals(flag)) {
			data.put("status", "n");
			data.put("message", "用户名或密码错误");
			model.addAttribute("message", "用户名或密码错误");
		}else {
			data.put("status", "y");
			data.put("message", "欢迎回来");	
			model.addAttribute("message", "欢迎回来");	
		}
		return data;
	}
	
	@RequestMapping(value="exit")
	@ResponseBody
	public Object exit(CommonRequestAttributes attributes) {
		Map<String, String> data = new HashMap<>();
		HttpSession session = attributes.getRequest().getSession();
		session.setAttribute("userflag", "0");
		data.put("message", "退出成功");	
		return data;
	}
	
	@RequestMapping(value="toRegister")
	public String toRegister() {
		return path()+"register";
	}
	
	@RequestMapping(value="register")
	@ResponseBody
	public Object register(CommonRequestAttributes attributes, Model model, final User entity, @RequestParam String phone, @RequestParam String name, @RequestParam String password) {
		Map<String, String> data = new HashMap<>();
		if(loginService.register(model, phone, name, password)) {
			data.put("status", "y");
			data.put("message", "欢迎加入啾啾");
			model.addAttribute("message", "欢迎加入啾啾");
		}else {
			data.put("status", "n");
			data.put("message", "该手机号已注册");
			model.addAttribute("message", "该手机号已注册");
		}
		return data;
	}
	
	@RequestMapping(value="modify")
	public String modify(CommonRequestAttributes attributes, Model model) {
		HttpSession session = attributes.getRequest().getSession();
		String userflag = (String)session.getAttribute("userflag");
		if("0".equals(userflag)) {
			return "redirect:/navigate?page=4";
		}else {
			return path()+"modify";
		}
	}
	
	
	@RequestMapping(value="myFoot")
	public String myFoot(CommonRequestAttributes attributes, Model model) {
		return path()+"footmark";
	}
	
	@RequestMapping(value="getfoots")
	@ResponseBody
	public String getFoots(CommonRequestAttributes attributes, Model model) {
		HttpSession session = attributes.getRequest().getSession();
		User user = (User)session.getAttribute("user");
		List<TravelNote> travelnotes = travelNoteService.findByUser(attributes, model, user);
		List<Foot> foots = new ArrayList<Foot>();
		for(int i = 0; i < travelnotes.size(); i++) {
			TravelNote note = travelnotes.get(i);
			Area area = null;
			area = destinationService.findByRegionCode(note.getCityCode());
			if(area == null) {
				area = destinationService.findByRegionCode(note.getProvinceCode());
			}
			Foot foot = new Foot();
			foot.setLatitude(Double.parseDouble(area.getLatitude()));
			foot.setLongitude(Double.parseDouble(area.getLongitude()));
			if(note.getCityName()==null || "".equals(note.getCityName())) {
				foot.setTitle(note.getProvinceName());
			}else {
				foot.setTitle(note.getCityName());
			}
			String icon = "https://maps.google.com/mapfiles/marker";
			String type = String.valueOf((char)(65+i));
			foot.setIcon(icon+ type +".png");
			foots.add(foot);
		}
		CommonResponse revtal = new CommonResponse(true);
		revtal.setData(foots);
		
		return JSON.toJSONString(revtal);
	}
	
	
	@RequestMapping(value="myNote")
	public String myNote(CommonRequestAttributes attributes, Model model) {
		HttpSession session = attributes.getRequest().getSession();
		User user = (User)session.getAttribute("user");
		List<TravelNote> travelnotes = travelNoteService.findByUser(attributes, model, user);
		if(travelnotes != null && !travelnotes.isEmpty()) {
			model.addAttribute("travelnotes", travelnotes);
		}
		return path()+"mytravelnote";
	}
	@RequestMapping(value="myComment")
	public String myComment(CommonRequestAttributes attributes, Model model) {
		HttpSession session = attributes.getRequest().getSession();
		User user = (User)session.getAttribute("user");
		List<Comment> comments = commentService.findByUser(attributes, model, user);
		if(comments != null && !comments.isEmpty()) {
			model.addAttribute("comments", comments);
		}
		return path()+"mycomment";
	}
	
	@RequestMapping(value="changepassword")
	@ResponseBody
	public Object changePassword(CommonRequestAttributes attributes, Model model, @RequestParam String oldpasd, @RequestParam String newpasd) {
		HttpSession session = attributes.getRequest().getSession();
		User user = (User) session.getAttribute("user");
		Map<String, String> data = new HashMap<>();
		if(user.getPassword().equals(oldpasd)) {
			loginService.updatePassword(user, newpasd);
			data.put("status", "y");
			data.put("message", "修改密码成功");
		}else {
			data.put("status", "n");
			data.put("message", "旧密码输入错误");
		}
		return data;
	}
	
}
