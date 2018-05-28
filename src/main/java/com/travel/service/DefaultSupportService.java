package com.travel.service;

import java.io.Serializable;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.travel.entity.basic.SupportModel;

public class DefaultSupportService<T extends SupportModel, PK extends Serializable> implements SupportService<T, PK> {
	
	protected final Log logger = LogFactory.getLog(getClass());
	
}
