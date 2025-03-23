package ru.ns.t_jobs.app.candidate.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import ru.ns.t_jobs.app.tag.dto.TagDto;

import java.time.LocalDate;
import java.util.List;

public record ResumeDto(
        long id,
        @JsonProperty("candidate_id") long candidateId,
        String name,
        @JsonProperty("salary_min") Integer salaryMin,
        String description,
        List<TagDto> tags,
        @JsonFormat(pattern = "yyyy-MM-dd") LocalDate data
) {
}
