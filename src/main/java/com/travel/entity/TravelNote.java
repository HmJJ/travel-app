package com.travel.entity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.travel.entity.basic.DefaultModel;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "TRAVEL_NOTE")
public class TravelNote extends DefaultModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2216873844431463285L;

	// 用户
	@ManyToOne(cascade=CascadeType.REFRESH, fetch=FetchType.EAGER)
	@JoinColumn(name = "USER_ID")
	@Getter
	@Setter
	private User user;

	// 省市代码
	@Column(name = "ProvinceCode", length=64)
	@Getter
	@Setter
	private String provinceCode;

	// 省市名称
	@Column(name = "ProvinceName", length=64)
	@Getter
	@Setter
	private String provinceName;
	
	// 城市代码
	@Column(name = "CityCode", length=64)
	@Getter
	@Setter
	private String cityCode;
	
	// 城市名称
	@Column(name = "CityName", length=64)
	@Getter
	@Setter
	private String cityName;
	
	// 景点
	@Column(name = "ATTRACTIONID", length=64)
	@Getter
	@Setter
	private String attractionid;
	
	// 景点
	@Column(name = "ATTRACTIONNAME", length=64)
	@Getter
	@Setter
	private String attractionname;
	
	// 游记标题
	@Column(name = "TITLE", length=64)
	@Getter
	@Setter
	private String title;

	// 游记内容
	@Column(name = "CONTENT", length=255)
	@Getter
	@Setter
	private String content;

	// 图片
	@OneToMany(cascade = { CascadeType.ALL }, mappedBy = "id")
	@Getter
	@Setter
	private Set<Image> images;

	// 游记状态
	@Column(name = "STATUS", length=4)
	@Getter
	@Setter
	private String status = "2";
	
	//点赞记录
	@OneToMany(cascade = { CascadeType.ALL }, mappedBy = "id")
	@Getter
	@Setter
	private Set<User> upvotes = new HashSet<>();
	
	// 点赞数
	@Column(name = "UPVOTENUM", length=8)
	@Getter
	@Setter
	private Integer upvotenum = upvotes.size();
	
	// 浏览量
	@Column(name = "WATCHS", length=8)
	@Getter
	@Setter
	private Integer watchs = 0;
	
	//评论记录
	@OneToMany(cascade = { CascadeType.ALL }, mappedBy = "id")
	@Getter
	@Setter
	private Set<Comment> comments = new HashSet<>();

}
