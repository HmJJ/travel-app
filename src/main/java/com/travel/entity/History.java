package com.travel.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.travel.entity.basic.DefaultModel;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name="TRAVEL_SEARCH_HISTORY")
public class History extends DefaultModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5687801776406744929L;

	// 用户
	@Column(name = "USER_ID")
	@Getter
	@Setter
	private String userid;
	
	//搜索历史
	@Column(name="SEARCH_HISTORY", length = 32, nullable=true)
	@Getter
	@Setter
	private String search_history;

}
