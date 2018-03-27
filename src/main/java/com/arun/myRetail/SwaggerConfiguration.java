/**
 * 
 */
package com.arun.myRetail;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @author Arun
 *
 */

@Configuration
@EnableSwagger2
public class SwaggerConfiguration {

	@Bean
	public Docket api() {
		return new Docket(DocumentationType.SWAGGER_2).select()
				.apis(RequestHandlerSelectors.basePackage("com.arun.myRetail")).paths(PathSelectors.any()).build()
				.apiInfo(apiInfo());

	}

	private ApiInfo apiInfo() {
		ApiInfo apiInfo = new ApiInfoBuilder().title("myRetail API").description("myRetail Api Documentation")
				.contact(new Contact("myRetail Dev Team", "https://github.com/arunpsg", "arunkumar.psg@gmail.com"))
				.license("myRetail License Version 1.0").version("1.0").build();
		return apiInfo;
	}
}
