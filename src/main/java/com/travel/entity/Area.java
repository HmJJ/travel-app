package com.travel.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.travel.entity.basic.DefaultModel;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "travel_area")
public class Area extends DefaultModel {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -8017655050184104934L;

	// 地区名称
	@Column(name = "RegionName", length = 64, nullable = true)
	@Getter
	@Setter
	private String RegionName;

	// 地区代码
	@Column(name = "RegionCode", length = 64, nullable = false)
	@Getter
	@Setter
	private String RegionCode;

	// 上级代码
	@Column(name = "ParentCode", length = 64, nullable = true)
	@Getter
	@Setter
	private String ParentCode;

	// 经度
	@Column(name = "Longitude", length = 32, nullable = true)
	@Getter
	@Setter
	private String Longitude;

	// 纬度
	@Column(name = "Latitude", length = 32, nullable = true)
	@Getter
	@Setter
	private String Latitude;

}
