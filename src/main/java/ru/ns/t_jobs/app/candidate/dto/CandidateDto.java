package ru.ns.t_jobs.app.candidate.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import ru.ns.t_jobs.app.vacancy.dto.VacancyDto;
import ru.ns.t_jobs.app.vacancy.entity.Vacancy;

import java.util.List;

public record CandidateDto(
        Long id,
        String name,
        String surname,
        @JsonProperty("photo_url") String photoUrl,
        @JsonProperty("tg_id") String tgId,
        String town,
        List<Long> resumes,
        List<Long> tracks,
        @JsonProperty("applied_vacancies") List<VacancyDto> appliedVacancies
) {
}
