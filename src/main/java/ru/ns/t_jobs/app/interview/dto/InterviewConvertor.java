package ru.ns.t_jobs.app.interview.dto;

import ru.ns.t_jobs.app.interview.entity.Interview;
import ru.ns.t_jobs.app.interview.entity.BaseInterview;
import ru.ns.t_jobs.app.interview.entity.InterviewStatus;
import ru.ns.t_jobs.app.track.entity.Track;

import java.util.Comparator;
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

    public static List<InterviewDto> interviewDtos(List<Interview> i) {
        return i.stream().map(InterviewConvertor::interviewDto).toList();
    }

    public static BaseInterviewDto baseInterviewsDto(BaseInterview i) {
        return new BaseInterviewDto(
                i.getId(),
                i.getInterviewType(),
                i.getVacancy().getId()
        );
    }

    public static List<BaseInterviewDto> baseInterviewDtos(List<BaseInterview> is) {
        return is.stream().sorted(Comparator.comparingInt(BaseInterview::getInterviewOrder))
                .map(InterviewConvertor::baseInterviewsDto).toList();
    }

    public static Interview interview(BaseInterview base, Track track) {
        return Interview.builder()
                .interviewOrder(base.getInterviewOrder())
                .interviewType(base.getInterviewType())
                .ableSetTime(base.getInterviewOrder() == 1)
                .track(track)
                .status(InterviewStatus.NONE)
                .dateApproved(false)
                .build();
    }

    public static List<Interview> interviews(List<BaseInterview> bases, Track track) {
        return bases.stream().map(b -> interview(b, track)).toList();
    }
}
