package ru.ns.t_jobs.app.interview.entity;

import ru.ns.t_jobs.app.track.entity.Track;

import java.util.List;

public class InterviewBaseConvertor {
    public static Interview from(InterviewBase base, Track track) {
        return Interview.builder()
                .interviewOrder(base.getInterviewOrder())
                .interviewType(base.getInterviewType())
                .ableSetTime(base.getInterviewOrder() == 1)
                .track(track)
                .status(InterviewStatus.NONE)
                .dateApproved(false)
                .build();
    }

    public static List<Interview> from(List<InterviewBase> bases, Track track) {
        return bases.stream().map(b -> InterviewBaseConvertor.from(b, track)).toList();
    }
}
