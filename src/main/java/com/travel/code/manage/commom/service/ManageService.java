package com.travel.code.manage.commom.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.travel.entity.User;
import com.travel.service.DefaultService;

@Service
@Transactional(rollbackFor = Exception.class)
public class ManageService extends DefaultService<User, String> {

}
