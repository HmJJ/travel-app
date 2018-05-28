package com.travel.code.client.comment.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;

import com.travel.code.client.travelnote.service.TravelNoteService;
import com.travel.entity.Comment;
import com.travel.entity.TravelNote;
import com.travel.entity.User;
import com.travel.page.Sort;
import com.travel.page.Sort.Direction;
import com.travel.service.DefaultService;
import com.travel.support.CommonRequestAttributes;
import com.travel.support.CommonResponse;
import com.travel.utils.BeanUtils;

@Service
@Transactional(rollbackFor = Exception.class)
public class CommentService extends DefaultService<Comment, String> {

	@Autowired private TravelNoteService travelNoteService;
	
	public List<Comment> findByUser(CommonRequestAttributes attributes, Model model, User user) {
		List<Comment> comments = new ArrayList<>();
		try {
			comments = findBy(new AssembleCriteriaParamsCallback() {
				
				@Override
				public DetachedCriteria assembleParams(DetachedCriteria criteria) {
					criteria.add(Restrictions.eq("user", user));
					criteria.add(Restrictions.eq("delete", Boolean.FALSE));
					return criteria;
				}
			}, new Sort(new Sort.Order(Direction.DESC, "createdate")));
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return comments;
	}
	
	@Transactional(readOnly = false)
	public CommonResponse comment(CommonRequestAttributes attributes, Model model, Comment entity, String noteid,
			User user) {
		CommonResponse retval = new CommonResponse(false);
		try {
			TravelNote note = travelNoteService.findBy(noteid);
			if(note != null) {
				Set<Comment> comments = note.getComments();
				Comment temp = findBy(entity.getId());
				if(temp == null) {
					temp = new Comment();
					BeanUtils.copyProperties(temp, entity);
				}
				temp.setUser(user);
				temp.setContent(entity.getContent());
				comments.add(temp);
				note.setComments(comments);
				temp = merge(temp);
				note = travelNoteService.merge(note);
				retval.setResult(true);
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return retval;
	}

}
