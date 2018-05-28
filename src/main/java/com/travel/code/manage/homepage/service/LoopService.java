package com.travel.code.manage.homepage.service;

import java.util.Date;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;

import com.travel.entity.Image;
import com.travel.service.DefaultService;
import com.travel.support.CommonRequestAttributes;
import com.travel.support.CommonResponse;
import com.travel.utils.BeanUtils;

@Service
@Transactional(rollbackFor = Exception.class)
public class LoopService extends DefaultService<Image, String> {

	@Transactional(readOnly = false)
	public CommonResponse commithandler(CommonRequestAttributes attributes, Model model, Image entity) {
		CommonResponse retval = new CommonResponse(false);
		try {
			Image temp = findBy(entity.getId());
			if (temp == null) {
				temp = new Image();
				BeanUtils.copyProperties(temp, entity);
			}
			temp.setSort(entity.getSort());
			temp.setImgUrl(entity.getImgUrl());
			temp.setLinkUrl(entity.getLinkUrl());
			temp.setTitle(entity.getTitle());
			temp.setStartdate(entity.getStartdate());
			temp.setEnddate(entity.getEnddate());
			temp.setModifydate(new Date());
			temp = merge(temp);
			retval.setResult(true);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return retval;
	}
	
	
	
}
