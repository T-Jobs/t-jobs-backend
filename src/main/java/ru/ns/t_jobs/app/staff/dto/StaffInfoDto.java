package ru.ns.t_jobs.app.staff.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import ru.ns.t_jobs.app.interview.entity.InterviewType;

import java.util.Collection;

public record StaffInfoDto(
        Long id,
        String name,
        String surname,
        String photoUrl,
        Collection<Long> tracks,
        @JsonProperty("interview_types")
        Collection<InterviewType> interviewTypes,
        Collection<Long> vacancies,
        Collection<String> roles,
        Collection<Long> interviews,
        @JsonProperty("interviewer_mode") boolean interviewerMode
) {
}
