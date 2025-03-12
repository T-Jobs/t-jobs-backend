package ru.ns.t_jobs.app.staff.dto;

import ru.ns.t_jobs.app.interview.entity.Interview;
import ru.ns.t_jobs.app.staff.entity.Staff;
import ru.ns.t_jobs.app.track.entity.Track;
import ru.ns.t_jobs.app.vacancy.entity.Vacancy;
import ru.ns.t_jobs.auth.credentials.Role;

import java.util.Collection;
import java.util.List;

public class StaffConvertor {

    public static StaffInfoDto staffInfoDto(Staff s) {
        return new StaffInfoDto(
                s.getId(),
                s.getName(),
                s.getSurname(),
                s.getPhotoUrl(),
                s.getTracks().stream().map(Track::getId).toList(),
                s.getInterviewTypes(),
                s.getVacancies() == null ? List.of() : s.getVacancies().stream().map(Vacancy::getId).toList(),
                s.getRoles().stream().map(Role::getName).toList(),
                s.getInterviews().stream().map(Interview::getId).toList(),
                s.isInterviewerMode()
        );
    }

    public static List<StaffInfoDto> staffInfoDtos(Collection<Staff> s) {
        return s.stream().map(StaffConvertor::staffInfoDto).toList();
    }
}
