package ru.ns.t_jobs.tg.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import ru.ns.t_jobs.app.interview.entity.InterviewStatus;

import java.time.LocalDateTime;

public record InterviewBotDto(
        long id,
        InterviewStatus status,
        @JsonProperty("interviewer_name") String interviewerName,
        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime date
) {
}
