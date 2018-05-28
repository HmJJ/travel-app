package com.travel.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.travel.entity.basic.DefaultModel;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name="TRAVEL_USER")
public class User extends DefaultModel {

	private static final long serialVersionUID = -5915184808202092984L;
	
	//用户类别（1：普通用户，2：VIP用户，3：管理员）
	@Column(name="TYPE", length = 1, nullable=false)
	@Getter
	@Setter
	private Integer type = 1;
	
	//用户名
	@Column(name="USERNAME", length = 16, nullable=false)
	@Getter
	@Setter
	private String username;
	
	//密码
	@Column(name="PASSWORD", length = 16, nullable=false)
	@Getter
	@Setter
	private String password;
	
	//手机号
	@Column(name="PHONE", length = 16, nullable=true)
	@Getter
	@Setter
	private String phone;
	
}
