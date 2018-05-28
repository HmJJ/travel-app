package com.travel.support;

import lombok.Getter;
import lombok.Setter;

public class Foot {

	// 纬度
	@Getter
	@Setter
	private double Latitude = 0.0;

	// 经度
	@Getter
	@Setter
	private double Longitude = 0.0;

	//地区名
	@Getter
	@Setter
	private String title = null;

	@Getter
	@Setter
	private int zoom = 4;

	@Getter
	@Setter
	private String icon = null;

	@Getter
	@Setter
	private String animation = "google.maps.Animation.DROP";

	
	@Override
	public String toString() {
		return "{"+
					"lat:" + this.Latitude + "," +
					"lon:" + this.Longitude + "," +
					"title:'" + this.title + "'," +
					"zoom:" + this.zoom + "," +
					"icon:'" + this.icon + "'," +
					"animation:" + this.animation +
				"}";
		
	}
}
