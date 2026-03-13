package com.example.web_flux_demo.service;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.example.web_flux_demo.dto.TasksDto;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class TaskClientService {

	private final WebClient webClient;

	public TaskClientService(@Qualifier("taskWebClient") WebClient taskWebClient) {
		this.webClient = taskWebClient;
	}

	// Get all TasksDtoDto
	public Flux<TasksDto> getAllTasksDtoDto() {
		return webClient.get().retrieve().bodyToFlux(TasksDto.class);
	}

	// Get task by id
	@CircuitBreaker(name = "taskService", fallbackMethod = "fallbackTask")
	public Mono<TasksDto> getTask(Long id) {
		// return webClient.get().uri("/{id}",
		// id).retrieve().bodyToMono(TasksDto.class);
		return webClient.get().uri("/{id}", id).retrieve()
				.onStatus(status -> status.is4xxClientError(),
						response -> Mono.error(new RuntimeException("Task not found")))
				.onStatus(status -> status.is5xxServerError(),
						response -> Mono.error(new RuntimeException("Task service error")))
				.bodyToMono(TasksDto.class).onErrorResume(ex -> {
					System.out.println("Error: " + ex.getMessage());
					return Mono.empty();
				});
	}
	
	public Mono<TasksDto> fallbackTask(Long id, Throwable ex) {
	    TasksDto fallback = new TasksDto();
	    fallback.setTitle("Fallback Task");
	    fallback.setDescription("Service unavailable");
	    return Mono.just(fallback);
	}

	// Create task
	public Mono<TasksDto> createTask(TasksDto task) {
		return webClient.post().bodyValue(task).retrieve().bodyToMono(TasksDto.class);
	}

	// Update task
	public Mono<TasksDto> updateTask(Long id, TasksDto task) {
		return webClient.put().uri("/{id}", id).bodyValue(task).retrieve().bodyToMono(TasksDto.class);
	}

	// Delete task
	public Mono<Void> deleteTask(Long id) {
		return webClient.delete().uri("/{id}", id).retrieve().bodyToMono(Void.class);
	}
}
