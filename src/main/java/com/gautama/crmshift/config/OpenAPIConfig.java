package com.gautama.crmshift.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;

@Configuration
public class OpenAPIConfig {

    @Bean
    public OpenAPI myOpenAPI() {

        Contact contact = new Contact();
        contact.setEmail("morozove450@gmail.com");
        contact.setName("gautamaPopados");

        Info info = new Info()
                .title("Morozov ShiftLAB Backend Project")
                .version("1.0")
                .contact(contact)
                .description("This API exposes endpoints for CRM.");

        return new OpenAPI().info(info);
    }
}