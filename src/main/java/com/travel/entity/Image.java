package com.travel.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.travel.entity.basic.DefaultModel;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "TRAVEL_IMAGE")
public class Image extends DefaultModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4451173344254293784L;

	// 图片的url
	@Column(name = "IMG_URL", length = 64, nullable = false)
	@Getter
	@Setter
	private String imgUrl;

	// 链接的url
	@Column(name = "LINK_URL", length = 64, nullable = false)
	@Getter
	@Setter
	private String linkUrl;

	// 图片的开始时间
	@Column(name = "START_DATE", nullable=true)
	@Getter
	@Setter
	private Date startdate;

	// 图片的结束时间
	@Column(name = "END_DATE", nullable = true)
	@Getter
	@Setter
	private Date enddate;

	// 图片排序 sort
	@Column(name = "SORT", length = 4, nullable = true)
	@Getter
	@Setter
	private String sort;

	// 图片标题
	@Column(name = "TITLE", length = 32, nullable = true)
	@Getter
	@Setter
	private String title;

}
