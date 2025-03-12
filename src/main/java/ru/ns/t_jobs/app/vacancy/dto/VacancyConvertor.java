package ru.ns.t_jobs.app.vacancy.dto;

import ru.ns.t_jobs.app.candidate.entity.Candidate;
import ru.ns.t_jobs.app.interview.entity.BaseInterview;
import ru.ns.t_jobs.app.staff.entity.Staff;
import ru.ns.t_jobs.app.tag.dto.TagConvertor;
import ru.ns.t_jobs.app.track.entity.Track;
import ru.ns.t_jobs.app.vacancy.entity.Vacancy;

import java.util.Collection;
import java.util.List;

public class VacancyConvertor {
    public static VacancyDto vacancyDto(Vacancy v) {
        return new VacancyDto(
                v.getId(),
                v.getName(),
                v.getDescription(),
                v.getSalaryMin(),
                v.getSalaryMax(),
                v.getTown(),
                v.getBaseInterviews().stream().map(BaseInterview::getId).toList(),
                TagConvertor.tagDtos(v.getTags()),
                v.getStaff().stream().map(Staff::getId).toList(),
                v.getTracks() == null ? List.of() : v.getTracks().stream().map(Track::getId).toList(),
                v.getAppliedCandidates() == null ? List.of() : v.getAppliedCandidates().stream().map(Candidate::getId).toList()
        );
    }

    public static List<VacancyDto> vacancyDtos(Collection<Vacancy> v) {
        return v.stream().map(VacancyConvertor::vacancyDto).toList();
    }
}
