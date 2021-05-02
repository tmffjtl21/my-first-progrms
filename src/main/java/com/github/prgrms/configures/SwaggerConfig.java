package com.github.prgrms.configures;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.web.bind.annotation.RequestMethod;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.builders.ResponseMessageBuilder;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.ResponseMessage;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.List;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

	private static List<ResponseMessage> responseMessages = new ArrayList<>();
	
	static {
		responseMessages.add(new ResponseMessageBuilder().code(200).message("OK").build());
		responseMessages.add(new ResponseMessageBuilder().code(400).message("Bad Request").build());
		responseMessages.add(new ResponseMessageBuilder().code(401).message("Unauthorized").build());
		responseMessages.add(new ResponseMessageBuilder().code(403).message("Forbidden").build());
		responseMessages.add(new ResponseMessageBuilder().code(404).message("Not Found").build());
		responseMessages.add(new ResponseMessageBuilder().code(500).message("Internal Server Error").responseModel(new ModelRef("Error")).build());
	}
	
	@Bean
	public Docket api2() {
		return new Docket(DocumentationType.SWAGGER_2)
				.groupName("my-first-progrms")
				.apiInfo(metadata())
				
				/**
				 * select() method returns an instance of ApiSelectorBuilder, which
				 * provides a way to control the endpoints exposed by Swagger
				 **/
				.select()
				
				/**
				 * Predicates for selection of RequestHandlers can be configured with
				 * the help of RequestHandlerSelectors and PathSelectors. Using any()
				 * for both will make documentation for your entire API available
				 * through Swagger.
				 **/

				.apis(RequestHandlerSelectors.basePackage("com.github.prgrms.users"))
				.paths(PathSelectors.ant("/api/**"))
				.build()
				//.pathMapping("/")
				
				/**
				 * Instructing Swagger not to use default response messages.
				 */

	            .useDefaultResponseMessages(false)
	            .globalResponseMessage(RequestMethod.GET, responseMessages)
				;
	}

	private ApiInfo metadata() {
		return new ApiInfoBuilder().title("my-first-progrms")
				.description("Programmers API. <br>"
						+ "각 인터페이스 사용 시, 먼저 Implementation Notes 및 Model 구조를 정독해 주시기 바랍니다.")
				.termsOfServiceUrl("http://vod.osstem.com/software/sla/sladb.nsf/sla/bm?Open")
				.contact(new Contact("LEE TAEJIN", "", "tmffjtl21@osstem.com"))
				.license("")
				.version("0.1")
				.build();
	}
}
