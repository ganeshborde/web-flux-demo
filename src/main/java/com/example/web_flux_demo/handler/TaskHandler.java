package com.example.web_flux_demo.handler;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import com.example.web_flux_demo.dto.TasksDto;
import com.example.web_flux_demo.service.TaskClientService;

import reactor.core.publisher.Mono;

@Component
public class TaskHandler {

    private final TaskClientService service;

    public TaskHandler(TaskClientService service) {
        this.service = service;
    }

    // GET all tasks
    public Mono<ServerResponse> getAllTasks(ServerRequest request) {
        return ServerResponse.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(service.getAllTasksDtoDto(), TasksDto.class);
    }

    // GET task by id
    public Mono<ServerResponse> getTask(ServerRequest request) {
        Long id = Long.valueOf(request.pathVariable("id"));

        return service.getTask(id)
                .flatMap(task -> ServerResponse.ok().bodyValue(task))
                .switchIfEmpty(ServerResponse.notFound().build());
    }

    // CREATE task
    public Mono<ServerResponse> createTask(ServerRequest request) {
        return request.bodyToMono(TasksDto.class)
                .flatMap(service::createTask)
                .flatMap(task -> ServerResponse.ok().bodyValue(task));
    }

    // UPDATE task
    public Mono<ServerResponse> updateTask(ServerRequest request) {
        Long id = Long.valueOf(request.pathVariable("id"));

        return request.bodyToMono(TasksDto.class)
                .flatMap(task -> service.updateTask(id, task))
                .flatMap(updated -> ServerResponse.ok().bodyValue(updated));
    }

    // DELETE task
    public Mono<ServerResponse> deleteTask(ServerRequest request) {
        Long id = Long.valueOf(request.pathVariable("id"));

        return service.deleteTask(id)
                .then(ServerResponse.noContent().build());
    }
}
