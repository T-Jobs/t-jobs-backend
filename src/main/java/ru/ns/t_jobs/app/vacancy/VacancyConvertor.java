package ru.ns.t_jobs.app.vacancy;

import ru.ns.t_jobs.app.interview.entity.InterviewBase;
import ru.ns.t_jobs.app.staff.entity.Staff;
import ru.ns.t_jobs.app.track.entity.Track;

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
                v.getTracks().stream().map(Track::getId).toList()
        );
    }
}
