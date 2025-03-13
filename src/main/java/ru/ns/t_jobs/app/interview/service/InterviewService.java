package ru.ns.t_jobs.app.interview.service;

import ru.ns.t_jobs.app.interview.dto.BaseInterviewDto;
import ru.ns.t_jobs.app.interview.dto.CreateInterviewDto;
import ru.ns.t_jobs.app.interview.dto.InterviewDto;
import ru.ns.t_jobs.app.interview.entity.InterviewType;

import java.util.List;

public interface InterviewService {
    InterviewDto getInterview(long id);
    List<InterviewDto> getInterviews(List<Long> ids);
    List<InterviewType> searchInterviewTypes(String name);
    BaseInterviewDto getBaseInterview(long id);
    List<BaseInterviewDto> getBaseInterviews(List<Long> ids);
    void deleteInterview(long id);
    InterviewDto addInterview(CreateInterviewDto createInterviewDto);
    void setInterviewer(long interviewId, long interviewerId);
}
