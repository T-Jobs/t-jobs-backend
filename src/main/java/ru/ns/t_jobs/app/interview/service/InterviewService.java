package ru.ns.t_jobs.app.interview.service;

import ru.ns.t_jobs.app.interview.dto.InterviewDto;

public interface InterviewService {
    InterviewDto getInterviewById(long id);
}
