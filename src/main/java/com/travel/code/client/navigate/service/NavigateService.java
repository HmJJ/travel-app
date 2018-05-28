package com.travel.code.client.navigate.service;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.travel.code.client.destination.service.DestinationService;
import com.travel.entity.Area;
import com.travel.entity.User;
import com.travel.service.DefaultService;

@Service
public class NavigateService extends DefaultService<User, String> {

	@Autowired private DestinationService destinationServicel;

	
	public Area findByField(String field) {
		List<Area> areas = destinationServicel.findBy(new AssembleCriteriaParamsCallback() {
			@Override
			public DetachedCriteria assembleParams(DetachedCriteria criteria) {
				criteria.add(Restrictions.like("RegionName", field, MatchMode.ANYWHERE));
				return criteria;
			}
		});
		if(areas.isEmpty() || areas == null) {
			return null;
		}else {
			return areas.get(0);
		}
	}
	
}
