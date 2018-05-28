package com.travel.code.manage.destination.travelnote.service;

import java.util.Date;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;

import com.travel.entity.TravelNote;
import com.travel.service.DefaultService;
import com.travel.support.CommonRequestAttributes;
import com.travel.support.CommonResponse;
import com.travel.utils.BeanUtils;

@Service
@Transactional(rollbackFor = Exception.class)
public class TravelNoteManageService extends DefaultService<TravelNote, String> {

	@Transactional(readOnly = false)
	public CommonResponse commithandler(CommonRequestAttributes attributes, Model model, TravelNote entity) {
		CommonResponse retval = new CommonResponse(false);
		try {
			TravelNote temp = findBy(entity.getId());
			if (temp == null) {
				temp = new TravelNote();
				BeanUtils.copyProperties(temp, entity);
			}
			temp.setStatus(entity.getStatus());
			temp.setModifydate(new Date());
			temp = merge(temp);
			retval.setResult(true);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return retval;
	}

}
