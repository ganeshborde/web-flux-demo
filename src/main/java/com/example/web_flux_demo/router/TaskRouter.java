package com.example.web_flux_demo.router;

import static org.springframework.web.reactive.function.server.RequestPredicates.*;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.example.web_flux_demo.handler.TaskHandler;

@Configuration
public class TaskRouter {

    @Bean
    public org.springframework.web.reactive.function.server.RouterFunction<?> taskRoutes(TaskHandler handler) {

        return route(GET("/tasks"), handler::getAllTasks)
                .andRoute(GET("/tasks/{id}"), handler::getTask)
                .andRoute(POST("/tasks"), handler::createTask)
                .andRoute(PUT("/tasks/{id}"), handler::updateTask)
                .andRoute(DELETE("/tasks/{id}"), handler::deleteTask);
    }
}
