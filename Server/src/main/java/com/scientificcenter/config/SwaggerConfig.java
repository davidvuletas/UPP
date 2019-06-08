package com.scientificcenter.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import static springfox.documentation.builders.PathSelectors.any;

/**
 * Swagger tool configuration.
 *
 *
 */
@EnableSwagger2
@Configuration
public class SwaggerConfig implements WebMvcConfigurer {

    /**
     * Filtering API to be included in Swagger's response/scope.
     *
     * @return
     */
    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors
                        .basePackage("com.scientificcenter"))
                .paths(any())
                .build()
                .apiInfo(apiInfo());
    }
    private ApiInfo apiInfo() {
        String description = "Company -  My API";
        return new ApiInfoBuilder()
                .title("REST API")
                .description(description)
                .version("1.0")
                .build();
    }


}
