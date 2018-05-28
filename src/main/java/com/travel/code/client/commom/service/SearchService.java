package com.travel.code.client.commom.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.travel.entity.Area;
import com.travel.page.Sort;
import com.travel.page.Sort.Direction;
import com.travel.service.DefaultService;
import com.travel.support.CommonResponse;

@Service
@Transactional(rollbackFor = Exception.class)
public class SearchService extends DefaultService<Area, String> {
	
	public CommonResponse findAllPlace(String searchtext) {
		CommonResponse retval = new CommonResponse(false);
		Map<String, Object> map = new HashMap<>();
		try {
			List<Area> dis = findBy(new AssembleCriteriaParamsCallback() {
				@Override
				public DetachedCriteria assembleParams(DetachedCriteria criteria) {
					criteria.add(Restrictions.like("RegionName", searchtext, MatchMode.ANYWHERE));
					criteria.add(Restrictions.eq("delete", Boolean.FALSE));
					return criteria;
				}
			},new Sort(
					new Sort.Order(Direction.DESC, "ParentCode"),
					new Sort.Order(Direction.DESC, "RegionCode"))
			);
			map.put("result_list", dis);
			retval.setData(map);
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return retval;
	}

	public CommonResponse getDetailInfo(Area area) {
		CommonResponse retval = new CommonResponse(false);
		
		return retval;
	}
	
}
