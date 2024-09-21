package com.blog.config;


import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {
    private static final String AUTHORIZATIION_HEADER="Authorization";



    @Bean
    public OpenAPI blogAppOpenAPI() {
        return new OpenAPI()
                .info(new Info().title("BlogApp API")
                        .description("This is for blogging ")
                        .version("v0.0.1")
                        .contact(new Contact().name("Vishal Singh").email("gnsinghvishal7887@gmail.com"))
                        .license(new License().name("Apache 2.0").url("http://springdoc.org")))
                .externalDocs(new ExternalDocumentation()
                        .description("hello Welcome to my blog api")
                        .url("https://www.instagram.com/analyser_vishal/"));
    }


}
