package ru.ns.t_jobs.app.staff.service;

import ru.ns.t_jobs.app.interview.dto.InterviewDto;
import ru.ns.t_jobs.app.staff.dto.StaffInfoDto;
import ru.ns.t_jobs.app.track.dto.TrackInfoDto;
import ru.ns.t_jobs.app.vacancy.dto.VacancyDto;
import ru.ns.t_jobs.auth.credentials.Role;

import java.util.Collection;
import java.util.List;

public interface StaffService {
    StaffInfoDto getUserInfo();
    Collection<String> getUserRoles();
    List<VacancyDto> getUserVacancies();
    List<InterviewDto> getUserInterviews(boolean onlyActual);
    List<TrackInfoDto> getHrTracks(boolean onlyActual);
    List<StaffInfoDto> searchStaffByText(String text);
    List<StaffInfoDto> getStaffByIds(List<Long> ids);
    StaffInfoDto getStaffById(Long id);
    void setInterviewerMode(boolean interviewerMode);
    void addInterviewTypeToInterviewer(long interviewTypeId);
    void removeInterviewTypeFromInterviewer(long interviewTypeId);
    void followVacancy(long id);
}
