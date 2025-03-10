package ru.ns.t_jobs.app.interview.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;
import ru.ns.t_jobs.app.interview.dto.InterviewDto;
import ru.ns.t_jobs.app.interview.service.InterviewService;

@RestController
@RequiredArgsConstructor
public class InterviewControllerImpl implements InterviewController {

    private final InterviewService interviewService;

    @Override
    public InterviewDto getInterviewById(long id) {
        return interviewService.getInterviewById(id);
    }
}
