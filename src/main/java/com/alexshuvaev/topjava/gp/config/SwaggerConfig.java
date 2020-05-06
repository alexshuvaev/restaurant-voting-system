package com.alexshuvaev.topjava.gp.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.BasicAuth;
import springfox.documentation.service.SecurityScheme;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger.web.OperationsSorter;
import springfox.documentation.swagger.web.UiConfiguration;
import springfox.documentation.swagger.web.UiConfigurationBuilder;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

/**
 * Java config for Springfox Swagger REST API documentation plugin.
 *
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig {
    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .ignoredParameterTypes(AuthenticationPrincipal.class)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.alexshuvaev.topjava.gp"))
                .paths(PathSelectors.any())
                .build()
                .directModelSubstitute(LocalDate.class, java.sql.Date.class)
                .apiInfo(apiDetails())
                .securitySchemes(securitySchemes());
    }

    @Bean
    public UiConfiguration uiConfig() {
        return UiConfigurationBuilder
                .builder()
                .operationsSorter(OperationsSorter.METHOD)
                .build();
    }

    private ApiInfo apiDetails() {
        return new ApiInfo(
                "Restaurant Voting App API",
                "API for Restaurant",
                "1.0",
                "Free to use",
                new springfox.documentation.service.Contact(
                        "Alexandr Shuvaev",
                        "https://github.com/alexshuvaev",
                        "alexshuvaevdev@gmail.com"),
                "",
                "",
                Collections.emptyList());
    }

    private static List<? extends SecurityScheme> securitySchemes() {
        return List.of(new BasicAuth("Basic"));
    }
}
