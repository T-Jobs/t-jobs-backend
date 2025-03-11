package ru.ns.t_jobs.app.candidate.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import ru.ns.t_jobs.app.tag.Tag;

import java.time.LocalDate;
import java.util.List;

public record ResumeDto(
        long id,
        @JsonProperty("candidate_id") long candidateId,
        @JsonProperty("salary_min") Integer salaryMin,
        String description,
        List<Tag> tags,
        @JsonFormat(pattern = "yyyy-MM-dd") LocalDate data
) {
}
