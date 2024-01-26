package com.skr.redcare.githubapi.configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Config class for Swagger
 */
@Configuration
public class SwaggerConfig {

    /**
     * Creates a new {@link GroupedOpenApi} for swagger documentation
     *
     * @return {@link GroupedOpenApi} instance
     */
    @Bean
    public GroupedOpenApi openApi() {
        return GroupedOpenApi
                .builder()
                .group("v1")
                .pathsToMatch("/**")
                .build();
    }

    /**
     * Method that defines api info for swagger
     *
     * @return instance of {@link OpenAPI}
     */
    @Bean
    public OpenAPI apiInfo() {
        return new OpenAPI().info(new Info().title("Github API").
                        description("Search Github and return list of repositories with defined query passed as param.").
                        contact(new Contact().email("sijumon.skr@gmail.com").name("Sijumon Karyil Raju")).
                        version("1.0.0"));
    }
}

