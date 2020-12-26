package com.nrkt.springbootmongoex.config;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.hateoas.Links;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.builders.RequestParameterBuilder;
import springfox.documentation.schema.ScalarType;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.List;

@EnableSwagger2
@Configuration
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class SwaggerConfig {

    @Bean
    public Docket apiV1() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.nrkt.springbootmongoex.controller.rest"))
                .paths(PathSelectors.regex("/api.*"))
                .build()
                .apiInfo(apiEndpointInfo())
                .globalRequestParameters(parameters())
                .useDefaultResponseMessages(false)
                .tags(
                        new Tag("Employee", "Employee Service"),
                        new Tag("File", "File Service")
                ).ignoredParameterTypes(Links.class);
    }

    private ApiInfo apiEndpointInfo() {
        return new ApiInfoBuilder()
                .title("Spring Boot MongoDB Example")
                .description("MONGODB & MONGODB GRIDFS DEMO")
                .contact(new Contact("Nrkt", "", ""))
                .version("1.0.0")
                .build();
    }

    private List<RequestParameter> parameters() {
        RequestParameterBuilder parameterBuilder = new RequestParameterBuilder();
        List<RequestParameter> params = new ArrayList<>();

        params.add(parameterBuilder
                .name("mediaType")
                .description("Enter media type: xml, json or hal. (Default: json, hal = \"application/hal+json\" with link)")
                .required(false)
                .in(ParameterType.QUERY)
                .query(q -> {
                    q.model(m -> m.scalarModel(ScalarType.STRING));
                    q.defaultValue("json");
                })
                .build());

        return params;
    }
}
