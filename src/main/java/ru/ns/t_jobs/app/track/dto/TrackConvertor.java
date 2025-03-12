package ru.ns.t_jobs.app.track.dto;

import ru.ns.t_jobs.app.candidate.dto.CandidateConvertor;
import ru.ns.t_jobs.app.interview.entity.Interview;
import ru.ns.t_jobs.app.staff.dto.StaffConvertor;
import ru.ns.t_jobs.app.track.entity.Track;
import ru.ns.t_jobs.app.vacancy.dto.VacancyConvertor;

import java.util.Comparator;

public class TrackConvertor {

    public static TrackInfoDto trackInfoDto(Track track) {
        return new TrackInfoDto(
                track.getId(),
                StaffConvertor.staffInfoDto(track.getHr()),
                CandidateConvertor.candidateDto(track.getCandidate()),
                VacancyConvertor.vacancyDto(track.getVacancy()),
                track.isFinished(),
                track.getInterviews().stream()
                        .sorted(Comparator.comparingInt(Interview::getInterviewOrder))
                        .map(Interview::getId).toList(),
                track.getLastStatus()
        );
    }
}
