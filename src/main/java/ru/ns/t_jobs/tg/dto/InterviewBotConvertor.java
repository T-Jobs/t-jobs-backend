package ru.ns.t_jobs.tg.dto;

import ru.ns.t_jobs.app.interview.entity.Interview;

import java.util.List;
import java.util.Objects;

public class InterviewBotConvertor {
    public static InterviewBotDto interviewBotDto(Interview i) {
        return new InterviewBotDto(
                i.getId(),
                i.getStatus(),
                i.getInterviewType().getName(),
                i.getInterviewer() == null ? null : i.getInterviewer().getName() + " " + Objects.requireNonNullElse(i.getInterviewer().getSurname(), ""),
                i.getDatePicked()
        );
    }

    public static List<InterviewBotDto> interviewBotDtos(List<Interview> is) {
        return is.stream().map(InterviewBotConvertor::interviewBotDto).toList();
    }
}
