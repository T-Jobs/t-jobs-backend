package ru.ns.t_jobs.app.interview.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import ru.ns.t_jobs.app.interview.entity.InterviewType;

public record InterviewBaseDto(
        long id,
        @JsonProperty("interview_type") InterviewType interviewType,
        @JsonProperty("vacancy_id") long vacancyId
) {
}
