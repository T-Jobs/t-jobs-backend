package ru.ns.t_jobs.app.vacancy.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import ru.ns.t_jobs.app.tag.dto.TagDto;

import java.util.List;

public record VacancyDto(
        Long id,
        String name,
        String description,
        @JsonProperty("salary_min") Integer salaryMin,
        @JsonProperty("salary_max") Integer salaryMax,
        String town,
        List<Long> interviews,
        List<TagDto> tags,
        List<Long> staff,
        List<Long> tracks,
        @JsonProperty("applied_candidates") List<Long> appliedCandidates
) {
}
