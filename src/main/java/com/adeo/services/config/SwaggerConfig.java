package com.adeo.services.config;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpMethod;
import springfox.bean.validators.configuration.BeanValidatorPluginsConfiguration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.builders.ResponseBuilder;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.Response;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
@Import(BeanValidatorPluginsConfiguration.class)
public class SwaggerConfig {

  @Bean
  public Docket swaggerConfiguration() {

    final List<Response> globalResponses = Arrays.asList(
        new ResponseBuilder()
            .code("400")
            .description("Invalid request parameter(s)")
            .build(),
        new ResponseBuilder()
            .code("405")
            .description("Method Not Allowed")
            .build(),
        new ResponseBuilder()
            .code("500")
            .description("Internal server error")
            .build()
    );

    return new Docket(DocumentationType.SWAGGER_2)
        .useDefaultResponseMessages(false)
        .select()
        .apis(RequestHandlerSelectors.any())
        .paths(PathSelectors.any())
        .build()
        .globalResponses(HttpMethod.GET, globalResponses)
        .globalResponses(HttpMethod.POST, globalResponses)
        .globalResponses(HttpMethod.DELETE, globalResponses)
        .globalResponses(HttpMethod.PATCH, globalResponses)
        .globalResponses(HttpMethod.PUT, globalResponses)
        .produces(Collections.singleton("application/json"))
        .consumes(Collections.singleton("application/json"))
        .apiInfo(metaData());
  }

  private ApiInfo metaData() {
    return new ApiInfoBuilder()
        .title("Aparthotel Adeo Services API documentation")
        .version("1.0")
        .contact(new Contact("Soki, Kruno, Vjeko", "https://nemamo-web-jer-smo-poor.com/", "nemamo-ni-email@gmail.com"))
        .build();
  }
}