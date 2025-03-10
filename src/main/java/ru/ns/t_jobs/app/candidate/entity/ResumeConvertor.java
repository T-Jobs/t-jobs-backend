package ru.ns.t_jobs.app.candidate.entity;

import ru.ns.t_jobs.app.candidate.dto.ResumeDto;

import java.util.List;

public class ResumeConvertor {
    public static ResumeDto from(Resume r) {
        return new ResumeDto(
                r.getId(),
                r.getCandidate().getId(),
                r.getSalaryMin(),
                r.getDescription(),
                r.getTags()
        );
    }

    public static List<ResumeDto> from(List<Resume> r) {
        return r.stream().map(ResumeConvertor::from).toList();
    }
}
