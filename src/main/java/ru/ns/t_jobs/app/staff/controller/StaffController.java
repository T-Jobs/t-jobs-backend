package ru.ns.t_jobs.app.staff.controller;

import org.springframework.web.bind.annotation.*;
import ru.ns.t_jobs.app.interview.dto.InterviewDto;
import ru.ns.t_jobs.app.staff.dto.StaffInfoDto;
import ru.ns.t_jobs.app.track.dto.TrackInfoDto;
import ru.ns.t_jobs.app.vacancy.dto.VacancyDto;
import ru.ns.t_jobs.auth.user.Role;

import java.util.List;

@RequestMapping("/user")
public interface StaffController {

    @GetMapping("/info")
    StaffInfoDto getUserInfo();

    @GetMapping("/roles")
    List<Role> getUserRoles();

    @GetMapping("/interviews")
    List<InterviewDto> getUserInterviews(@RequestParam boolean onlyActual);

    @GetMapping("/vacancies")
    List<VacancyDto> getUserVacancies();

    @GetMapping("/tracks")
    List<TrackInfoDto> getUserTracks(@RequestParam boolean onlyActual);

    @GetMapping("/search")
    List<StaffInfoDto> searchStaffByText(@RequestParam String text);

    @GetMapping("/{id}")
    StaffInfoDto getStaffById(@PathVariable long id);

    @GetMapping
    List<StaffInfoDto> getStaffByIds(@RequestParam("ids") List<Long> ids);

    @PostMapping("/set-interviewer-mode")
    void setInterviewerMode(@RequestParam("value") boolean interviewerMode);
}
