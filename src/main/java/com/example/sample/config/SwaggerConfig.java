package com.example.sample.config;

import java.util.ArrayList;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.VendorExtension;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.WebMvcObjectMapperConfigurer;
import springfox.documentation.spring.web.plugins.Docket;

@Configuration
//@EnableSwagger2
//@EnableWebMvc
public class SwaggerConfig {

	@Bean
	public Docket createDocket() {
		// For creating new Docket
		return new Docket(DocumentationType.SWAGGER_2)
				// For Selecting APIs For finding controllers
				.select()
				// Provide All Controllers Common package name
				.apis(RequestHandlerSelectors.basePackage("org.springframework.boot"))
				// Provide Common path of Controllers
				.paths(PathSelectors.ant("/api.*"))
				// Build Docket with Details
				.build() // Create Docket with Details
				.apiInfo(apiInfo());
	}
	
	
	private ApiInfo apiInfo() {
		return new ApiInfo("WELCOME TO THE PRODCT APP", 
				"This is Used to Product Application", 
				"R-1.0 GA", "http://tcs.com", 
				new Contact("TCS", "http://tcs.com", "testemail@tcs.com"), 
				"TestLicence", "http://tcs1.com", new ArrayList<VendorExtension>());
	}
}
