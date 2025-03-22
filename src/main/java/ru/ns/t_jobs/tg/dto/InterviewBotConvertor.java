package ru.ns.t_jobs.tg.dto;

import ru.ns.t_jobs.app.interview.entity.Interview;

import java.util.List;

public class InterviewBotConvertor {
    public static InterviewBotDto interviewBotDto(Interview i) {
        return new InterviewBotDto(
                i.getId(),
                i.getStatus(),
                i.getInterviewType().getName(),
                i.getDatePicked()
        );
    }

    public static List<InterviewBotDto> interviewBotDtos(List<Interview> is) {
        return is.stream().map(InterviewBotConvertor::interviewBotDto).toList();
    }
}
