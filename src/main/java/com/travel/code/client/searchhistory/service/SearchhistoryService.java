package com.travel.code.client.searchhistory.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;

import com.travel.code.client.destination.service.DestinationService;
import com.travel.entity.Area;
import com.travel.entity.History;
import com.travel.entity.User;
import com.travel.page.Sort;
import com.travel.page.Sort.Direction;
import com.travel.service.DefaultService;
import com.travel.support.CommonRequestAttributes;
import com.travel.support.CommonResponse;
import com.travel.utils.BeanUtils;

@Service
@Transactional(rollbackFor = Exception.class)
public class SearchhistoryService extends DefaultService<History, String> {
	
//	@Autowired
//	EntityManagerFactory entityManagerFactory;
	
	@Autowired private DestinationService destinationService;
	
	public List<Area> searchHistory(CommonRequestAttributes attributes, Model model){
		HttpSession session = attributes.getRequest().getSession();
		List<History> prehistorys = new ArrayList<>();
		List<Area> historys = new ArrayList<>();
		
		User user = (User)session.getAttribute("user");
		if(user != null) {
			try {
				prehistorys = findBy(new AssembleCriteriaParamsCallback() {
					
					@Override
					public DetachedCriteria assembleParams(DetachedCriteria criteria) {
						criteria.add(Restrictions.eq("userid", user.getId()));
						criteria.add(Restrictions.eq("delete", Boolean.FALSE));
						return criteria;
					}
				}, new Sort(new Sort.Order(Direction.ASC, "createdate")));
			} catch (Exception e) {
				logger.error(e.getMessage());
			}
		}
		for(History h : prehistorys) {
			Area a = destinationService.findByRegionName(h.getSearch_history());
			if(a != null) {
				historys.add(a);
			}
		}
		return historys;
	}
	
	public List<Area> hotSearch(CommonRequestAttributes attributes, Model model){
//		EntityManager entityManager = entityManagerFactory.createEntityManager();
		List<Area> hotSearchs = new ArrayList<>();
		List<String> prehotSearchs = new ArrayList<>();
		List<History> search_contents = new ArrayList<>();
		
//		String sql = "select search_history from travel_search_history group by search_history desc";
//		Query query = entityManager.createQuery(sql);
//		prehotSearchs = (List<String>)query.getResultList();
		
//		try {
//			search_contents = findBy(new AssembleCriteriaParamsCallback() {
//				
//				@Override
//				public DetachedCriteria assembleParams(DetachedCriteria criteria) {
//					criteria.add(Restrictions.sqlRestriction("from travel_search_history group by search_history"));
//					return criteria;
//				}
//			});
//		} catch (Exception e) {
//			logger.error(e.getMessage());
//		}
		for(History h : search_contents) {
			prehotSearchs.add(h.getSearch_history());
		}
		
		for(String s : prehotSearchs) {
			Area a = destinationService.findByRegionCode(s);
			if(a != null) {
				hotSearchs.add(a);
			}
		}
		
		return hotSearchs;
	}
	
	@Transactional(readOnly = false)
	public CommonResponse commithandler(CommonRequestAttributes attributes, Model model, History entity) {
		CommonResponse retval = new CommonResponse(false);
		try {
			History temp = null;
			if(entity.getId() != null) {
				temp = findBy(entity.getId());
			}
			if (temp == null) {
				temp = new History();
				BeanUtils.copyProperties(temp, entity);
			}
			temp.setUserid(entity.getUserid());
			temp.setSearch_history(entity.getSearch_history());
			temp.setModifydate(new Date());
			temp = merge(temp);
			retval.setResult(true);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return retval;
	}

	@Transactional(readOnly = false)
	public void clear(User user) {
		List<History> historys = findBy(new AssembleCriteriaParamsCallback() {
			@Override
			public DetachedCriteria assembleParams(DetachedCriteria criteria) {
				criteria.add(Restrictions.eq("userid",user.getId()));
				return criteria;
			}
		});
		if(!historys.isEmpty()) {
			for(History h : historys) {
				delete(h.getId());
			}
		}
	}
	
}
