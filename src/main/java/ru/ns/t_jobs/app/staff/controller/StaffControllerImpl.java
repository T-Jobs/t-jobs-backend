package ru.ns.t_jobs.app.staff.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;
import ru.ns.t_jobs.app.interview.dto.InterviewDto;
import ru.ns.t_jobs.app.staff.dto.StaffInfoDto;
import ru.ns.t_jobs.app.staff.service.StaffService;
import ru.ns.t_jobs.app.track.dto.TrackInfoDto;
import ru.ns.t_jobs.app.vacancy.dto.VacancyDto;
import ru.ns.t_jobs.auth.user.Role;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class StaffControllerImpl implements StaffController {

    private final StaffService staffService;

    @Override
    public StaffInfoDto getUserInfo() {
        return staffService.getUserInfo();
    }

    @Override
    public List<Role> getUserRoles() {
        return staffService.getUserRoles();
    }

    @Override
    public List<InterviewDto> getUserInterviews(boolean onlyActual) {
        return staffService.getUserInterviews(onlyActual);
    }

    @Override
    public List<VacancyDto> getUserVacancies() {
        return staffService.getUserVacancies();
    }

    @Override
    public List<TrackInfoDto> getUserTracks(boolean onlyActual) {
        return staffService.getHrTracks(onlyActual);
    }

    @Override
    public List<StaffInfoDto> searchStaffByText(String text) {
        return staffService.searchStaffByText(text);
    }

    @Override
    public StaffInfoDto getStaffById(long id) {
        return staffService.getStaffById(id);
    }

    @Override
    public List<StaffInfoDto> getStaffByIds(List<Long> ids) {
        return staffService.getStaffByIds(ids);
    }

    @Override
    public void setInterviewerMode(boolean interviewerMode) {
        staffService.setInterviewerMode(interviewerMode);
    }

    @Override
    public void addInterviewTypeToInterviewer(long interviewTypeId) {
        staffService.addInterviewTypeToInterviewer(interviewTypeId);
    }

    @Override
    public void removeInterviewTypeToInterviewer(long interviewTypeId) {
        staffService.removeInterviewTypeFromInterviewer(interviewTypeId);
    }

    @Override
    public void followVacancy(long id) {
        staffService.followVacancy(id);
    }
}
