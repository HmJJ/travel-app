package com.travel.repository.support;

import java.io.Serializable;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.travel.entity.basic.SupportModel;

public class DefaultSupportRepository<T extends SupportModel, PK extends Serializable> implements SupportRepository<T, PK> {

	protected final Log logger = LogFactory.getLog(getClass());

}
