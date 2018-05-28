package com.travel.code.client.homepage.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.travel.entity.User;
import com.travel.service.DefaultService;

@Service
@Transactional(rollbackFor = Exception.class)
public class HomepageService extends DefaultService<User, String> {

	
	
	
}
