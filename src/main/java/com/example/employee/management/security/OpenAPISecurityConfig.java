package com.example.employee.management.security;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenAPISecurityConfig {

    private static final String OAUTH_SCHEME_NAME = "my_oAuth_security_schema";

    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI().components(new Components()
                        .addSecuritySchemes(OAUTH_SCHEME_NAME, createOAuthScheme()))
                .addSecurityItem(new SecurityRequirement().addList(OAUTH_SCHEME_NAME))
                .info(new Info().title("Employee Management Service")
                        .description("A service providing APIs to manage employee data.")
                        .version("1.0"));
    }

    private SecurityScheme createOAuthScheme() {
        OAuthFlows flows = createOAuthFlows();
        return new SecurityScheme().type(SecurityScheme.Type.HTTP)
                .flows(flows);
    }

    private OAuthFlows createOAuthFlows() {
        OAuthFlow flow1 = createGetTokenCodeFlow();
        return new OAuthFlows().clientCredentials(flow1);
    }

    private OAuthFlow createGetTokenCodeFlow() {
        return new OAuthFlow()
                .authorizationUrl("http://localhost:8080/oauth2/code/")
                .tokenUrl("http://localhost:8080/oauth2/token/")
                .scopes(new Scopes().addString("write", ""));
    }

}