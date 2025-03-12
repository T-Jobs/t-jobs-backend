package ru.ns.t_jobs.app.interview.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;
import ru.ns.t_jobs.app.interview.dto.BaseInterviewDto;
import ru.ns.t_jobs.app.interview.dto.InterviewDto;
import ru.ns.t_jobs.app.interview.entity.InterviewType;
import ru.ns.t_jobs.app.interview.service.InterviewService;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class InterviewControllerImpl implements InterviewController {

    private final InterviewService interviewService;

    @Override
    public InterviewDto getInterview(long id) {
        return interviewService.getInterview(id);
    }

    @Override
    public List<InterviewDto> getInterviews(List<Long> ids) {
        return interviewService.getInterviews(ids);
    }

    @Override
    public List<InterviewType> searchInterviewTypes(String name) {
        return interviewService.searchInterviewTypes(name);
    }

    @Override
    public BaseInterviewDto getBaseInterview(long id) {
        return interviewService.getBaseInterview(id);
    }

    @Override
    public List<BaseInterviewDto> getBaseInterviews(List<Long> ids) {
        return interviewService.getBaseInterviews(ids);
    }
}
