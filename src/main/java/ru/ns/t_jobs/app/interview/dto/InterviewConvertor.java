package ru.ns.t_jobs.app.interview.dto;

import ru.ns.t_jobs.app.interview.entity.Interview;

public class InterviewConvertor {
    public static InterviewDto from(Interview i) {
        return new InterviewDto(
                i.getId(),
                i.getInterviewer().getId(),
                i.getInterviewType(),
                i.getTrackId(),
                i.getDatePicked(),
                i.isDateApproved(),
                i.getFeedback(),
                i.getStatus(),
                i.isAbleSetTime()
        );
    }
}
