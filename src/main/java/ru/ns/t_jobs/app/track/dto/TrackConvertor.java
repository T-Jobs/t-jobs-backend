package ru.ns.t_jobs.app.track.dto;

import ru.ns.t_jobs.app.candidate.CandidateConvertor;
import ru.ns.t_jobs.app.interview.entity.Interview;
import ru.ns.t_jobs.app.staff.dto.StaffConvertor;
import ru.ns.t_jobs.app.track.entity.Track;
import ru.ns.t_jobs.app.vacancy.VacancyConvertor;

public class TrackConvertor {

    public static TrackInfoDto from(Track track) {
        return new TrackInfoDto(
                track.getId(),
                StaffConvertor.from(track.getHr()),
                CandidateConvertor.from(track.getCandidate()),
                VacancyConvertor.from(track.getVacancy()),
                track.getInterviews().stream().map(Interview::getId).toList(),
                track.getLastStatus()
        );
    }
}
