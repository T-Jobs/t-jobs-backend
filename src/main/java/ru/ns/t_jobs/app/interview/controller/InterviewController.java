package ru.ns.t_jobs.app.interview.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.ns.t_jobs.app.interview.dto.InterviewDto;

@RequestMapping("/interview")
public interface InterviewController {
    @GetMapping("/{id}")
    public InterviewDto getInterviewById(@PathVariable("id") long id);
}
