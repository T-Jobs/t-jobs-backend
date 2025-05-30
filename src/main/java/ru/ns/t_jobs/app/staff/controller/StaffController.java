package ru.ns.t_jobs.app.staff.controller;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.ns.t_jobs.app.interview.dto.InterviewDto;
import ru.ns.t_jobs.app.staff.dto.StaffInfoDto;
import ru.ns.t_jobs.app.track.dto.TrackInfoDto;
import ru.ns.t_jobs.app.vacancy.dto.VacancyDto;

import java.util.Collection;
import java.util.List;

@RequestMapping("/user")
public interface StaffController {

    @GetMapping("/info")
    StaffInfoDto getUserInfo();

    @GetMapping("/roles")
    Collection<String> getUserRoles();

    @GetMapping("/interviews")
    List<InterviewDto> getUserInterviews(@RequestParam boolean onlyRelevant);

    @GetMapping("/vacancies")
    List<VacancyDto> getUserVacancies();

    @GetMapping("/tracks")
    List<TrackInfoDto> getUserTracks(@RequestParam boolean onlyRelevant);

    @GetMapping("/search")
    List<StaffInfoDto> searchStaffByText(@RequestParam String text);

    @GetMapping("/{id}")
    StaffInfoDto getStaffById(@PathVariable long id);

    @GetMapping
    List<StaffInfoDto> getStaffByIds(@RequestParam("ids") List<Long> ids);

    @PostMapping("/set-interviewer-mode")
    void setInterviewerMode(@RequestParam("value") boolean interviewerMode);

    @PostMapping("/competencies")
    void addInterviewTypeToInterviewer(@RequestParam("interview_type_id") long interviewTypeId);

    @DeleteMapping("/competencies")
    void removeInterviewTypeToInterviewer(@RequestParam("interview_type_id") long interviewTypeId);

    @PostMapping("/follow-vacancy/{id}")
    void followVacancy(@PathVariable("id") long id);

    @PostMapping("/unfollow-vacancy/{id}")
    void unfollowVacancy(@PathVariable("id") long id);
}
