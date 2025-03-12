package ru.ns.t_jobs.app.interview.dto;

import ru.ns.t_jobs.app.interview.entity.Interview;
import ru.ns.t_jobs.app.interview.entity.InterviewBase;

import java.util.List;

public class InterviewConvertor {
    public static InterviewDto from(Interview i) {
        return new InterviewDto(
                i.getId(),
                i.getInterviewer().getId(),
                i.getInterviewType(),
                i.getTrack().getId(),
                i.getDatePicked(),
                i.isDateApproved(),
                i.getFeedback(),
                i.getStatus(),
                i.isAbleSetTime(),
                i.getLink()
        );
    }

    public static InterviewBaseDto from(InterviewBase i) {
        return new InterviewBaseDto(
                i.getId(),
                i.getInterviewType(),
                i.getVacancy().getId(),
                i.getInterviewOrder()
        );
    }

    public static List<InterviewBaseDto> from(List<InterviewBase> is) {
        return is.stream().map(InterviewConvertor::from).toList();
    }
}
