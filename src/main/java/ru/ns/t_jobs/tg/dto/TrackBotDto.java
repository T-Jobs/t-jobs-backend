package ru.ns.t_jobs.tg.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record TrackBotDto(
        long id,
        String name,
        @JsonProperty("interview") InterviewBotDto interview
) {
}
