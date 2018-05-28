package com.travel.code.client.upvote.service;

import java.util.Date;
import java.util.Set;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;

import com.travel.entity.TravelNote;
import com.travel.entity.User;
import com.travel.service.DefaultService;
import com.travel.support.CommonRequestAttributes;
import com.travel.support.CommonResponse;

@Service
@Transactional(rollbackFor = Exception.class)
public class UpvoteService extends DefaultService<TravelNote, String> {
	
	@Transactional(readOnly = false)
	public CommonResponse upvote(CommonRequestAttributes attributes, Model model, String id, User user) {
		CommonResponse retval = new CommonResponse(false);
		try {
			TravelNote temp = findBy(id);
			if(temp != null) {
				Set<User> upvotes = temp.getUpvotes();
				if(temp.getUpvotes().contains(user)) {
					upvotes.remove(user);
					temp.setUpvotes(upvotes);
				} else {
					upvotes.add(user);
					temp.setUpvotes(upvotes);
				}
				temp.setModifydate(new Date());
				temp = merge(temp);
				retval.setResult(true);
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return retval;
	}
	
}
