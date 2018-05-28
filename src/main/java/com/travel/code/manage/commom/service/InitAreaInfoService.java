package com.travel.code.manage.commom.service;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.travel.entity.Area;
import com.travel.service.DefaultService;

@Service
@Transactional(rollbackFor = Exception.class)
public class InitAreaInfoService extends DefaultService<Area, String> {
	
//	EntityManager entityManager;
	@Autowired
	EntityManagerFactory entityManagerFactory;
	
	@SuppressWarnings("unchecked")
	public void initInfo() {
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		List<Area> area = new ArrayList<>();
		
		String sql = "FROM Area";
		Query query = entityManager.createQuery(sql);
		area = (List<Area>)query.getResultList();
		for(Area a : area) {
			try {
				Area temp = findByName(a.getRegionCode());
				if(temp == null) {
					temp = new Area();
					temp.setRegionName(a.getRegionName());
					temp.setRegionCode(a.getRegionCode());
					temp.setParentCode(a.getParentCode());
					temp.setLongitude(a.getLongitude());
					temp.setLatitude(a.getLatitude());
					temp = merge(temp);
				}
			} catch (Exception e) {
				logger.error(e.getMessage(), e);
			}
		}
		System.out.println("over");
	}

	private Area findByName(String regionCode) {
		List<Area> areas = findBy(new AssembleCriteriaParamsCallback() {
			@Override
			public DetachedCriteria assembleParams(DetachedCriteria criteria) {
				criteria.add(Restrictions.eq("RegionCode", regionCode));
				criteria.add(Restrictions.eq("delete", Boolean.FALSE));
				return criteria;
			}
		});
		if (areas == null || areas.isEmpty()) {
			return null;
		}
		return areas.get(0);
	}
	
}
