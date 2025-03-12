package ru.ns.t_jobs.app.candidate.dto;

import ru.ns.t_jobs.app.candidate.entity.Candidate;
import ru.ns.t_jobs.app.candidate.entity.Resume;
import ru.ns.t_jobs.app.track.entity.Track;
import ru.ns.t_jobs.app.vacancy.entity.Vacancy;

import java.util.List;

public class CandidateConvertor {
    public static CandidateDto from(Candidate candidate) {
        return new CandidateDto(
                candidate.getId(),
                candidate.getName(),
                candidate.getSurname(),
                candidate.getPhotoUrl(),
                candidate.getTgId(),
                candidate.getTown(),
                candidate.getResumes().stream().map(Resume::getId).toList(),
                candidate.getTracks().stream().map(Track::getId).toList(),
                candidate.getAppliedVacancies().stream().map(Vacancy::getId).toList()
        );
    }

    public static List<CandidateDto> from(List<Candidate> c) {
        return c.stream().map(CandidateConvertor::from).toList();
    }
}
