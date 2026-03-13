package com.example.web_flux_demo.dto;

import java.time.LocalDateTime;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TasksDto {

    private Long id;

    private String title;

    private String description;

    private String priority;

    private String status;

    private LocalDateTime dueDate;

    private List<String> tags;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    private Long assignedEmployeeId;

    // User returned from task API
    private UserDto createdBy;
}
