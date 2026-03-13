package com.example.web_flux_demo.router;

import static org.springframework.web.reactive.function.server.RequestPredicates.DELETE;
import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RequestPredicates.POST;
import static org.springframework.web.reactive.function.server.RequestPredicates.PUT;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.example.web_flux_demo.handler.RoleHandler;

@Configuration
public class RoleRouter {

    @Bean
    public org.springframework.web.reactive.function.server.RouterFunction<?> roleRoutes(RoleHandler handler) {

        return route(POST("/roles"), handler::createRole)
                .andRoute(GET("/roles"), handler::getAllRoles)
                .andRoute(GET("/roles/{id}"), handler::getRoleById)
                .andRoute(PUT("/roles/{id}"), handler::updateRole)
                .andRoute(DELETE("/roles/{id}"), handler::deleteRole);
    }
}
