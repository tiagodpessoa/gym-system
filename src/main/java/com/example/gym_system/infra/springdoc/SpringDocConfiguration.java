package com.example.gym_system.infra.springdoc;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringDocConfiguration {

    @Bean
    public OpenAPI customOpenApi(){
        return new OpenAPI().
                components(new Components().
                        addSecuritySchemes("bearer-key",
                            new SecurityScheme().type(SecurityScheme.Type.HTTP).scheme("bearer").bearerFormat("JWT"))).
                info(new Info().
                        title("Gym system API").
                        description("API Rest para um sistema de academia, contendo CRUD de instrutores, recepcionistas e clientes, além de um sistema de autenticação com JWT Token."));
    }
}
