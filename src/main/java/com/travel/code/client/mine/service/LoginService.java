package com.travel.code.client.mine.service;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;

import com.travel.entity.User;
import com.travel.service.DefaultService;
import com.travel.support.CommonRequestAttributes;

@Service
@Transactional(rollbackFor = Exception.class)
public class LoginService extends DefaultService<User, String> {
	
	public boolean findByPhone(String phone) {
		List<User> users = findBy(new AssembleCriteriaParamsCallback() {
			@Override
			public DetachedCriteria assembleParams(DetachedCriteria criteria) {
				criteria.add(Restrictions.eq("phone", phone));
				criteria.add(Restrictions.eq("delete", Boolean.FALSE));
				return criteria;
			}
		});
		if(users.isEmpty() || users == null) {
			return false;
		}else {
			return true;
		}
	}
	
	public String login(CommonRequestAttributes attributes, String phone, String password) {
		HttpSession session = attributes.getRequest().getSession();
		List<User> users = findBy(new AssembleCriteriaParamsCallback() {
			@Override
			public DetachedCriteria assembleParams(DetachedCriteria criteria) {
				criteria.add(Restrictions.eq("phone", phone));
				criteria.add(Restrictions.eq("password", password));
				criteria.add(Restrictions.eq("delete", Boolean.FALSE));
				return criteria;
			}
		});
		if(users.isEmpty() || users == null) {
			return "0";
		}else {
			session.setAttribute("user", users.get(0));
			session.setAttribute("userflag", "1");
			return "1";
		}
	}

	@Transactional(readOnly = false)
	public boolean register(Model model, String phone, String name, String password) {
		boolean flag = false;
		try {
			if(findByPhone(phone)) {
				flag = false;
			}else {
				User user = new User();
				user.setType(1);
				user.setPhone(phone);
				user.setUsername(name);
				user.setPassword(password);
				user.setModifydate(new Date());
				user = merge(user);
				flag = true;
				model.addAttribute("user",user);
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		return flag;
	}
	
	public boolean isLogin(CommonRequestAttributes attributes) {
		HttpSession session = attributes.getRequest().getSession();
		if("0".equals(session.getAttribute("userflag")) || session.getAttribute("userflag") == null) {
			return false;
		}else {
			return true;
		}
	}

	@Transactional(readOnly = false)
	public void updatePassword(User user, String newpasd) {
		try {
			User temp = user;
			temp.setPassword(newpasd);
			temp.setModifydate(new Date());
			temp = merge(temp);
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
	}
	

}
