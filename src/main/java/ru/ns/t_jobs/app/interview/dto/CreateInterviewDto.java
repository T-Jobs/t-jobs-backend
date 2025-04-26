package ru.ns.t_jobs.app.interview.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDateTime;

public record CreateInterviewDto(
        @JsonProperty("interview_type_id") long interviewTypeId,
        @JsonProperty("interviewer_id") Long interviewerId,
        @JsonProperty("track_id") long trackId,
        @JsonProperty("date")
        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
        LocalDateTime date,
        @JsonProperty("link")
        String link
) {
}
