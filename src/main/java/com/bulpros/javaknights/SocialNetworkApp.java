package com.bulpros.javaknights;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@ComponentScan({"com.bulpros.javaknights"})
@SpringBootApplication
public class SocialNetworkApp extends WebMvcConfigurerAdapter {

	public static void main(String[] args) {
		SpringApplication.run(SocialNetworkApp.class, args);
	}

}
