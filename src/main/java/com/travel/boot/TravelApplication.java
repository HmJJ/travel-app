package com.travel.boot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ImportResource;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.bind.annotation.RequestMapping;

@ImportResource(locations = {"classpath*:/beans/**/*.xml"})
@ComponentScan(basePackages= {"com.travel.**.controller,com.travel.**.service,com.travel.**.entity,com.travel.configure"})
@SpringBootApplication
@EnableScheduling
public class TravelApplication extends SpringBootServletInitializer {

	public static void main(String[] args) {
		SpringApplication.run(TravelApplication.class, args);
	}
	
	protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
		return builder.sources(TravelApplication.class);
	}
	
	@RequestMapping(value= {"/",""})
	public String search() {
		return "index";
	}
	
	
}
