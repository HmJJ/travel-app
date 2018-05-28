package com.travel.code.manage.destination.food.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;

import com.travel.code.client.destination.service.DestinationService;
import com.travel.code.manage.destination.attraction.service.AttractionManageService;
import com.travel.entity.Area;
import com.travel.entity.Attraction;
import com.travel.entity.Food;
import com.travel.service.DefaultService;
import com.travel.support.CommonRequestAttributes;
import com.travel.support.CommonResponse;
import com.travel.utils.BeanUtils;
import com.travel.utils.StringUtils;

@Service
@Transactional(rollbackFor = Exception.class)
public class FoodManageService extends DefaultService<Food, String> {

	@Autowired private DestinationService destinationService;
	@Autowired private AttractionManageService attractionManageService;
	
	@Transactional(readOnly = false)
	public CommonResponse commithandler(CommonRequestAttributes attributes, Model model, Food entity) {
		CommonResponse retval = new CommonResponse(false);
		try {
			Food temp = findBy(entity.getId());
			if (temp == null) {
				temp = new Food();
				BeanUtils.copyProperties(temp, entity);
			}
			if(StringUtils.isBlank(entity.getAttractionid())) {
				temp.setAttractionid("");
				temp.setAttractionname("");
			}else {
				Attraction attr = attractionManageService.findBy(entity.getAttractionid());
				temp.setAttractionid(entity.getAttractionid());
				temp.setAttractionname(attr==null?"":attr.getName());
			}
			temp.setName(entity.getName());
			temp.setProvinceCode(entity.getProvinceCode().toString());
			Area area = destinationService.findByRegionCode(entity.getProvinceCode());
			temp.setProvinceName(area==null?"":area.getRegionName().toString());
			temp.setCityCode(entity.getProvinceCode().toString());
			Area area1 = destinationService.findByRegionCode(entity.getCityCode());
			temp.setCityName(area==null?"":area1.getRegionName().toString());
			temp.setHotlevel(entity.getHotlevel());
			temp.setImgSrc(entity.getImgSrc());
			temp = merge(temp);
			retval.setResult(true);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return retval;
	}
	
}
