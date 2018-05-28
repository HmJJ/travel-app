package com.travel.code.client.homepage.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.travel.entity.History;
import com.travel.service.DefaultService;

@Service
@Transactional(rollbackFor = Exception.class)
public class HistoryService extends DefaultService<History, String> {

}
