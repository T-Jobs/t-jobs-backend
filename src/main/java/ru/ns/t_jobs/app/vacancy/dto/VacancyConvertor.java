package ru.ns.t_jobs.app.vacancy.dto;

import ru.ns.t_jobs.app.candidate.dto.CandidateConvertor;
import ru.ns.t_jobs.app.interview.entity.InterviewBase;
import ru.ns.t_jobs.app.staff.entity.Staff;
import ru.ns.t_jobs.app.track.entity.Track;
import ru.ns.t_jobs.app.vacancy.entity.Vacancy;

import java.util.Collection;
import java.util.List;

public class VacancyConvertor {
    public static VacancyDto from(Vacancy v) {
        return new VacancyDto(
                v.getId(),
                v.getName(),
                v.getDescription(),
                v.getSalaryMin(),
                v.getSalaryMax(),
                v.getTown(),
                v.getInterviewBases().stream().map(InterviewBase::getId).toList(),
                v.getTags(),
                v.getStaff().stream().map(Staff::getId).toList(),
                v.getTracks().stream().map(Track::getId).toList(),
                v.getAppliedCandidates().stream().map(CandidateConvertor::from).toList()
        );
    }

    public static List<VacancyDto> from(Collection<Vacancy> v) {
        return v.stream().map(VacancyConvertor::from).toList();
    }
}
