package ru.ns.t_jobs.app.interview.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;
import ru.ns.t_jobs.app.interview.dto.BaseInterviewDto;
import ru.ns.t_jobs.app.interview.dto.CreateInterviewDto;
import ru.ns.t_jobs.app.interview.dto.InterviewDto;
import ru.ns.t_jobs.app.interview.entity.InterviewType;
import ru.ns.t_jobs.app.interview.service.InterviewService;

import java.time.LocalDateTime;
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

    @Override
    public void setInterviewer(long interviewId, long interviewerId) {
        interviewService.setInterviewer(interviewId, interviewerId);
    }

    @Override
    public void setDate(long interviewId, LocalDateTime date) {
        interviewService.setDate(interviewId, date);
    }

    @Override
    public void setAutoDate(long interviewId) {
        interviewService.setAutoDate(interviewId);
    }

    @Override
    public void setAutoInterviewer(long interviewId) {
        interviewService.setAutoInterviewer(interviewId);
    }

    @Override
    public void deleteInterview(long id) {
        interviewService.deleteInterview(id);
    }

    @Override
    public InterviewDto addInterview(CreateInterviewDto createInterviewDto) {
        return interviewService.addInterview(createInterviewDto);
    }

    @Override
    public void setLink(long interviewId, String link) {
        interviewService.setLink(interviewId, link);
    }

    @Override
    public void setFeedback(long interviewId, boolean success, String feedback) {
        interviewService.setFeedback(interviewId, success, feedback);
    }
}

