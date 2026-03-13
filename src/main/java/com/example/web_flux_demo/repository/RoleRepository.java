package com.example.web_flux_demo.repository;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

import com.example.web_flux_demo.dto.Role;

@Repository
public interface RoleRepository extends ReactiveCrudRepository<Role, Long> {

}
