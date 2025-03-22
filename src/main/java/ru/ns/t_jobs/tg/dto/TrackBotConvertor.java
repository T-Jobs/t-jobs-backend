package ru.ns.t_jobs.tg.dto;

import ru.ns.t_jobs.app.track.entity.Track;

import java.util.Collection;
import java.util.List;

public class TrackBotConvertor {

    public static List<TrackBotDto> trackBotDtos(Collection<Track> vs) {
        return vs.stream().map(TrackBotConvertor::trackBotDto).toList();
    }

    public static TrackBotDto trackBotDto(Track t) {
        return new TrackBotDto(
                t.getId(),
                t.getVacancy().getName(),
                InterviewBotConvertor.interviewBotDto(t.getCurrentInterview())
        );
    }
}
