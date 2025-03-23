package ru.ns.t_jobs.tg.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public record VacancyBotDto (
        long id,
        String name,
        @JsonProperty("min_salary") Integer minSalary,
        @JsonProperty("max_salary") Integer maxSalary,
        String description,
        String town,
        List<String> tags
) {
}
