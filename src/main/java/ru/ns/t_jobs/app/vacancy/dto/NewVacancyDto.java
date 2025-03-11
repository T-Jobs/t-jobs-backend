package ru.ns.t_jobs.app.vacancy.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public record NewVacancyDto(
        String name,
        String description,
        @JsonProperty("salary_min") Integer salaryMin,
        @JsonProperty("salary_max") Integer salaryMax,
        String town,
        List<Long> interviews,
        List<Long> tags
) {
}
