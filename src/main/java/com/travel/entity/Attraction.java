package com.travel.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.travel.entity.basic.DefaultModel;

import lombok.Getter;
import lombok.Setter;

/**
 * 景点
 * 
 * @author Nott
 *
 */
@Entity
@Table(name = "TRAVEL_ATTRACTION")
public class Attraction extends DefaultModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4677808670384495155L;

	// 景点名称
	@Column(name = "NAME", length = 64, nullable = false)
	@Getter
	@Setter
	private String name;

	// 省市代码
	@Column(name = "ProvinceCode", length=16)
	@Getter
	@Setter
	private String provinceCode;
	
	// 省市名称
	@Column(name = "ProvinceName", length=32)
	@Getter
	@Setter
	private String provinceName;
	
	// 城市代码
	@Column(name = "CityCode", length=16)
	@Getter
	@Setter
	private String cityCode;
		
	// 城市名称
	@Column(name = "CityName", length=32)
	@Getter
	@Setter
	private String cityName;

	// 热度
	@Column(name = "HOTLEVEL", length = 16, nullable = true)
	@Getter
	@Setter
	private String hotlevel;

	// 人流量等级
	@Column(name = "PEOPLE_LEVEL", length = 16, nullable = true)
	@Getter
	@Setter
	private String peoplelevel;

	// 消费等级
	@Column(name = "EXPENSE_LEVEL", length = 16, nullable = true)
	@Getter
	@Setter
	private String expenselevel;

	// 交通便利等级
	@Column(name = "TRAFFIC_LEVEL", length = 16, nullable = true)
	@Getter
	@Setter
	private String trafficlevel;
	
}
