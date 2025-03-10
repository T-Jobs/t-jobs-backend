package ru.ns.t_jobs.app.candidate.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import ru.ns.t_jobs.app.tag.Tag;

import java.util.List;

public record ResumeDto(
        long id,
        @JsonProperty("candidate_id") long candidateId,
        @JsonProperty("salary_min") long salaryMin,
        String description,
        List<Tag> tags
) {
}
