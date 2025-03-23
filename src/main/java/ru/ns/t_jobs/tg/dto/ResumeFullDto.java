package ru.ns.t_jobs.tg.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public record ResumeFullDto(
        long id,
        String name,
        @JsonProperty("min_salary") Integer minSalary,
        List<String> tags,
        String description
) {
}
