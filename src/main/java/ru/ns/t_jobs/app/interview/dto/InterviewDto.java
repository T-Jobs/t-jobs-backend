package ru.ns.t_jobs.app.interview.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import ru.ns.t_jobs.app.interview.entity.InterviewStatus;
import ru.ns.t_jobs.app.interview.entity.InterviewType;

import java.time.LocalDateTime;

public record InterviewDto(
        long id,

        @JsonProperty("interviewer_id")
        long interviewerId,

        InterviewType interviewType,

        @JsonProperty("track_id")
        long trackId,

        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
        @JsonProperty("date_picked")
        LocalDateTime datePicked,

        @JsonProperty("date_approved") boolean dateApproved,
        String feedback,

        InterviewStatus status,

        @JsonProperty("able_set_time")
        boolean ableSetTime
) {
}
