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
}
