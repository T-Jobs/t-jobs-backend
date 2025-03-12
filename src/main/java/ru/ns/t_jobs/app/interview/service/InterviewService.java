package ru.ns.t_jobs.app.interview.service;

import ru.ns.t_jobs.app.interview.dto.InterviewBaseDto;
import ru.ns.t_jobs.app.interview.dto.InterviewDto;
import ru.ns.t_jobs.app.interview.entity.InterviewType;

import java.util.List;

public interface InterviewService {
    InterviewDto getInterview(long id);
    List<InterviewDto> getInterviews(List<Long> ids);
    List<InterviewType> searchInterviewTypes(String name);
    InterviewBaseDto getInterviewBase(long id);
    List<InterviewBaseDto> getInterviewBases(List<Long> ids);
}
