package com.travel.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.travel.entity.basic.DefaultModel;

import lombok.Getter;
import lombok.Setter;

/**
 * 美食
 * 
 * @author Nott
 *
 */
@Entity
@Table(name = "TRAVEL_FOOD")
public class Food extends DefaultModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -829736120505304732L;

	// 美食名称
	@Column(name = "NAME", length = 64, nullable = false)
	@Getter
	@Setter
	private String name;

	/**
	 * 图片地址
	 */
	@Column(name = "IMG_SRC", length = 128, nullable = false)
	@Getter
	@Setter
	private String imgSrc;

	// 省市代码
	@Column(name = "ProvinceCode", length = 16)
	@Getter
	@Setter
	private String provinceCode;

	// 省市名称
	@Column(name = "ProvinceName", length = 32)
	@Getter
	@Setter
	private String provinceName;

	// 城市代码
	@Column(name = "CityCode", length = 16)
	@Getter
	@Setter
	private String cityCode;

	// 城市名称
	@Column(name = "CityName", length = 32)
	@Getter
	@Setter
	private String cityName;

	// 对应的景点
	@Column(name = "ATTRACTIONID", length = 64, nullable = true)
	@Getter
	@Setter
	private String attractionid;

	// 对应的景点
	@Column(name = "ATTRACTIONNAME", length = 64, nullable = true)
	@Getter
	@Setter
	private String attractionname;

	// 热度
	@Column(name = "HOTLEVEL", length = 16, nullable = true)
	@Getter
	@Setter
	private Integer hotlevel;
}
