package com.example.web_flux_demo.handler;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import com.example.web_flux_demo.dto.Role;
import com.example.web_flux_demo.repository.RoleRepository;
import com.example.web_flux_demo.validator.ValidatorUtil;

import jakarta.validation.Validator;
import reactor.core.publisher.Mono;

@Component
public class RoleHandler {

	private final RoleRepository repository;

	private final Validator validator;

	public RoleHandler(RoleRepository repository, Validator validator) {
		this.repository = repository;
		this.validator = validator;
	}

	// CREATE
	public Mono<ServerResponse> createRole(ServerRequest request) {
		return request.bodyToMono(Role.class).flatMap(role -> {

			ValidatorUtil.validate(role, validator);
			return repository.save(role).flatMap(saved -> ServerResponse.ok().bodyValue(saved));
		});
	}

	// GET ALL
	public Mono<ServerResponse> getAllRoles(ServerRequest request) {
		System.out.println("Fetching all roles...Jay Shree Ram");
		return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).body(repository.findAll(), Role.class);
	}

	// GET BY ID
	public Mono<ServerResponse> getRoleById(ServerRequest request) {
		Long id = Long.valueOf(request.pathVariable("id"));

		return repository.findById(id).flatMap(role -> ServerResponse.ok().bodyValue(role))
				.switchIfEmpty(ServerResponse.notFound().build());
	}

	// UPDATE
	public Mono<ServerResponse> updateRole(ServerRequest request) {
		Long id = Long.valueOf(request.pathVariable("id"));

		Mono<Role> updatedRole = request.bodyToMono(Role.class);

		return repository.findById(id).flatMap(existing -> updatedRole.map(r -> {
			existing.setName(r.getName());
			existing.setDescription(r.getDescription());
			return existing;
		})).flatMap(repository::save).flatMap(role -> ServerResponse.ok().bodyValue(role))
				.switchIfEmpty(ServerResponse.notFound().build());
	}

	// DELETE
	public Mono<ServerResponse> deleteRole(ServerRequest request) {
		Long id = Long.valueOf(request.pathVariable("id"));

		return repository.deleteById(id).then(ServerResponse.noContent().build());
	}
}
