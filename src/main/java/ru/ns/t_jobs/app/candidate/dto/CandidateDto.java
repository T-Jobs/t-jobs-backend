package ru.ns.t_jobs.app.candidate.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public record CandidateDto(
        Long id,
        String name,
        String surname,
        @JsonProperty("photo_url") String photoUrl,
        @JsonProperty("tg_id") String tgId,
        String town,
        List<Long> resumes,
        List<Long> tracks
) {
}
