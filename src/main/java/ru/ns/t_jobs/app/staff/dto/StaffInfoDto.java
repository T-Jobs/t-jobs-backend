package ru.ns.t_jobs.app.staff.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import ru.ns.t_jobs.app.interview.entity.InterviewType;
import ru.ns.t_jobs.auth.user.Role;

import java.util.List;

public record StaffInfoDto(
        Long id,
        String name,
        String surname,
        String photoUrl,
        List<Long> tracks,
        @JsonProperty("interview_types")
        List<InterviewType> interviewTypes,
        List<Long> vacancies,
        List<String> roles,
        List<Long> interviews,
        @JsonProperty("interviewer_mode") boolean interviewerMode
) {
}
