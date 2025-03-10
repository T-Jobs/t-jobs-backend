package ru.ns.t_jobs.app.track.dto;

import ru.ns.t_jobs.app.candidate.dto.CandidateConvertor;
import ru.ns.t_jobs.app.interview.entity.Interview;
import ru.ns.t_jobs.app.staff.dto.StaffConvertor;
import ru.ns.t_jobs.app.track.entity.Track;
import ru.ns.t_jobs.app.vacancy.VacancyConvertor;

import java.util.Comparator;

public class TrackConvertor {

    public static TrackInfoDto from(Track track) {
        return new TrackInfoDto(
                track.getId(),
                StaffConvertor.from(track.getHr()),
                CandidateConvertor.from(track.getCandidate()),
                VacancyConvertor.from(track.getVacancy()),
                track.isFinished(),
                track.getInterviews().stream()
                        .sorted(Comparator.comparingInt(Interview::getInterviewOrder))
                        .map(Interview::getId).toList(),
                track.getLastStatus()
        );
    }
}
