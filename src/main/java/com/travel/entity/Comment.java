package com.travel.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.travel.entity.basic.DefaultModel;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "travel_comment")
public class Comment extends DefaultModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2363803945422919114L;

	// 用户
	@Column(name = "USER_ID", length = 64)
	@Getter
	@Setter
	private User user;
	
	// 目的地
	@Column(name = "CONTENT", length=256)
	@Getter
	@Setter
	private String content;

}
