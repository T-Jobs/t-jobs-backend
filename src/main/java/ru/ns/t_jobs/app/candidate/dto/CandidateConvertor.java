package ru.ns.t_jobs.app.candidate.dto;

import ru.ns.t_jobs.app.candidate.entity.Candidate;
import ru.ns.t_jobs.app.candidate.entity.Resume;
import ru.ns.t_jobs.app.track.entity.Track;
import ru.ns.t_jobs.app.vacancy.entity.Vacancy;

import java.util.List;

public class CandidateConvertor {
    public static CandidateDto candidateDto(Candidate candidate) {
        return new CandidateDto(
                candidate.getId(),
                candidate.getName(),
                candidate.getSurname(),
                candidate.getPhotoUrl(),
                candidate.getTgId(),
                candidate.getTown(),
                candidate.getResumes() == null ? List.of() : candidate.getResumes().stream().map(Resume::getId).toList(),
                candidate.getTracks() == null ? List.of() : candidate.getTracks().stream().map(Track::getId).toList(),
                candidate.getAppliedVacancies() == null ? List.of() : candidate.getAppliedVacancies().stream().map(Vacancy::getId).toList()
        );
    }

    public static List<CandidateDto> candidateDtos(List<Candidate> c) {
        return c.stream().map(CandidateConvertor::candidateDto).toList();
    }
}
