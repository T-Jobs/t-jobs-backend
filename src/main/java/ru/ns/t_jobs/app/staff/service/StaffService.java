package ru.ns.t_jobs.app.staff.service;

import org.springframework.stereotype.Repository;
import ru.ns.t_jobs.app.interview.dto.InterviewDto;
import ru.ns.t_jobs.app.staff.dto.StaffInfoDto;
import ru.ns.t_jobs.app.track.dto.TrackInfoDto;
import ru.ns.t_jobs.app.vacancy.dto.VacancyDto;
import ru.ns.t_jobs.auth.user.Role;

import java.util.List;

@Repository
public interface StaffService {
    StaffInfoDto getUserInfo();
    List<Role> getUserRoles();
    List<VacancyDto> getUserVacancies();
    List<InterviewDto> getUserInterviews(boolean onlyActual);
    List<TrackInfoDto> getHrTracks(boolean onlyActual);
}
