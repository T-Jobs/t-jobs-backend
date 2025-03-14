package ru.ns.t_jobs.app.interview.controller;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.ns.t_jobs.app.interview.dto.BaseInterviewDto;
import ru.ns.t_jobs.app.interview.dto.CreateInterviewDto;
import ru.ns.t_jobs.app.interview.dto.InterviewDto;
import ru.ns.t_jobs.app.interview.entity.InterviewType;

import java.time.LocalDateTime;
import java.util.List;

@RequestMapping("/interview")
public interface InterviewController {
    @GetMapping("/{id}")
    InterviewDto getInterview(@PathVariable("id") long id);

    @GetMapping
    List<InterviewDto> getInterviews(@RequestParam("ids") List<Long> ids);

    @GetMapping("/type/search")
    List<InterviewType> searchInterviewTypes(
            @RequestParam(value = "name", required = false, defaultValue = "")
            String name
    );

    @GetMapping("/base/{id}")
    BaseInterviewDto getBaseInterview(@PathVariable("id") long id);

    @GetMapping("/base")
    List<BaseInterviewDto> getBaseInterviews(@RequestParam("ids") List<Long> ids);

    @DeleteMapping("/{id}")
    void deleteInterview(@PathVariable("id") long id);

    @PostMapping("/add-to-track")
    InterviewDto addInterview(@RequestBody CreateInterviewDto createInterviewDto);

    @PostMapping("/set-interviewer")
    void setInterviewer(@RequestParam("interview_id") long interviewId, @RequestParam("interviewer_id") long interviewerId);

    @PostMapping("/set-auto-interviewer")
    void setAutoInterviewer(@RequestParam("interview_id") long interviewId);

    @PostMapping("/set-date")
    void setDate(@RequestParam("interview_id") long interviewId, @RequestParam("date") LocalDateTime date);


    @PostMapping("/set-auto-date")
    void setAutoDate(@RequestParam("interview_id") long interviewId);

    @PostMapping("/set-link")
    void setLink(@RequestParam("interview_id") long interviewId, @RequestParam("link") String link);

    @PostMapping("/set-feedback")
    void setFeedback(@RequestParam("interview_id") long interviewId, @RequestParam("success") boolean success, @RequestParam("feedback") String feedback);

    @PostMapping("/approve-time")
    void approveTime(@RequestParam("interview_id") long interviewId);

    @PostMapping("/decline-time")
    void declinePickedTime(@RequestParam("interview_id") long interviewId);
}
