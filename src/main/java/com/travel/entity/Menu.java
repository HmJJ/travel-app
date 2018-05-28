package com.travel.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.travel.entity.basic.DefaultModel;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name="TRAVEL_MENU")
public class Menu extends DefaultModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8306377678056903193L;

	//菜单按钮的类型（1：首页、2：目的地、3：酒店、4：个人中心）
	@Column(name="TYPE", length=2)
	@Getter
	@Setter
	private String type;
	
	@Column(name="NAME", length=16)
	@Getter
	@Setter
	private String name;
	
	@Column(name="STATUS", length=2)
	@Getter
	@Setter
	private String status;
	
	@Column(name="POSITION", length=4)
	@Getter
	@Setter
	private Integer position;
	
	/**
	 *  图片地址
	 */
	@Column(name = "IMG_SRC", length = 128, nullable = false)
	@Getter
	@Setter
	private String imgSrc;
	
	/**
	 * 跳转地址
	 */
	@Column(name = "LINK_URL", length = 128, nullable = true)
	@Getter
	@Setter
	private String linkUrl;

}
