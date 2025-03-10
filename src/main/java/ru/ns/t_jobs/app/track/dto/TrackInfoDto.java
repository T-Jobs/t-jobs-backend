package ru.ns.t_jobs.app.track.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import ru.ns.t_jobs.app.candidate.dto.CandidateDto;
import ru.ns.t_jobs.app.interview.entity.InterviewStatus;
import ru.ns.t_jobs.app.staff.dto.StaffInfoDto;
import ru.ns.t_jobs.app.vacancy.VacancyDto;

import java.util.List;

public record TrackInfoDto(
        long id,
        StaffInfoDto hr,
        CandidateDto candidate,
        VacancyDto vacancy,
        boolean finished,
        List<Long> interviews,
        @JsonProperty("last_status")
        InterviewStatus lastStatus
) {
}
