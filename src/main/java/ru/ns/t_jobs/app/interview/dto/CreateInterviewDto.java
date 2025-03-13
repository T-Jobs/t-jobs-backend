package ru.ns.t_jobs.app.interview.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record CreateInterviewDto(
        @JsonProperty("interview_type_id") long interviewTypeId,
        @JsonProperty("interviewer_id") Long interviewerId,
        @JsonProperty("track_id") long trackId
) {
}
