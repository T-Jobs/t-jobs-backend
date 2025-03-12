package ru.ns.t_jobs.app.interview.dto;

import ru.ns.t_jobs.app.interview.entity.Interview;
import ru.ns.t_jobs.app.interview.entity.InterviewBase;
import ru.ns.t_jobs.app.interview.entity.InterviewStatus;
import ru.ns.t_jobs.app.track.entity.Track;

import java.util.List;

public class InterviewConvertor {
    public static InterviewDto interviewDto(Interview i) {
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

    public static InterviewBaseDto interviewBaseDto(InterviewBase i) {
        return new InterviewBaseDto(
                i.getId(),
                i.getInterviewType(),
                i.getVacancy().getId(),
                i.getInterviewOrder()
        );
    }

    public static List<InterviewBaseDto> interviewBaseDtos(List<InterviewBase> is) {
        return is.stream().map(InterviewConvertor::interviewBaseDto).toList();
    }

    public static Interview interview(InterviewBase base, Track track) {
        return Interview.builder()
                .interviewOrder(base.getInterviewOrder())
                .interviewType(base.getInterviewType())
                .ableSetTime(base.getInterviewOrder() == 1)
                .track(track)
                .status(InterviewStatus.NONE)
                .dateApproved(false)
                .build();
    }

    public static List<Interview> interviews(List<InterviewBase> bases, Track track) {
        return bases.stream().map(b -> interview(b, track)).toList();
    }
}
