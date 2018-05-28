package com.travel.repository;

import java.io.Serializable;
import java.util.Map;

import org.hibernate.criterion.DetachedCriteria;

import com.travel.entity.basic.SupportModel;
import com.travel.page.Page;
import com.travel.page.PageRequest;

public interface PageRepository<T extends SupportModel, PK extends Serializable> extends FindRepository<T, PK> {
	
	Page<T> pageBy(final DetachedCriteria criteria, final PageRequest pageable);
	
	Page<T> pageBy(final StringBuilder hsql, final Map<String, Object>params, final PageRequest pageable);
	
	Page<T> pageBy(final StringBuilder hsql, final StringBuilder hcount, final PageRequest pageable);
	
	Page<T> pageBy(final StringBuilder hsql, final StringBuilder hcount, final Map<String, Object>params, final PageRequest pageable);

}
