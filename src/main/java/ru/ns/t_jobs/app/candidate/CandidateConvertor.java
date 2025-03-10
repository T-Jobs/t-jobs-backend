package ru.ns.t_jobs.app.candidate;

import ru.ns.t_jobs.app.track.entity.Track;

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
                candidate.getTracks().stream().map(Track::getId).toList()
        );
    }
}
