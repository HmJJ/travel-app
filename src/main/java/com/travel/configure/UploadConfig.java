//package com.travel.configure;
//
//import org.springframework.context.annotation.Configuration;
//import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
//import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
//import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
//
//@Configuration
//public class UploadConfig extends WebMvcConfigurationSupport {
//	
//	@Override
//	protected void addInterceptors(InterceptorRegistry registry) {
//		// TODO Auto-generated method stub
//		super.addInterceptors(registry);
//	}
//	
//	@Override
//	protected void addResourceHandlers(ResourceHandlerRegistry registry) {
//		/*
//	     * 说明：增加虚拟路径(经过本人测试：在此处配置的虚拟路径，用springboot内置的tomcat时有效，
//	     * 用外部的tomcat也有效;所以用到外部的tomcat时不需在tomcat/config下的相应文件配置虚拟路径了,阿里云linux也没问题)
//	     */
//	    registry.addResourceHandler("/upload/image/**").addResourceLocations("file:///D:/upload/images/");
//	        
//	    //阿里云(映射路径去除盘符)
//        //registry.addResourceHandler("/ueditor/image/**").addResourceLocations("/upload/image/");
//        //registry.addResourceHandler("/ueditor/video/**").addResourceLocations("/upload/video/");
//        super.addResourceHandlers(registry);
//	}
//	
//}
