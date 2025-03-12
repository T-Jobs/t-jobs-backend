package ru.ns.t_jobs.app.interview.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.ns.t_jobs.app.interview.dto.InterviewBaseDto;
import ru.ns.t_jobs.app.interview.dto.InterviewDto;
import ru.ns.t_jobs.app.interview.entity.InterviewType;

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
    InterviewBaseDto getInterviewBase(@PathVariable("id") long id);

    @GetMapping("/base")
    List<InterviewBaseDto> getInterviewBases(@RequestParam("ids") List<Long> ids);
}
