package com.example.web_flux_demo.dto;

import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Table("roles")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Role {

	private Long id;
	@NotBlank(message = "Role name required")
	@Size(min = 3, max = 100)
	@Column("name")
	private String name;

	@Column("description")
	private String description;
}
