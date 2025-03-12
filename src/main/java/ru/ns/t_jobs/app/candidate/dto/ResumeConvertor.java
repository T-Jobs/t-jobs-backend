package ru.ns.t_jobs.app.candidate.dto;

import ru.ns.t_jobs.app.candidate.entity.Resume;
import ru.ns.t_jobs.app.tag.dto.TagConvertor;

import java.util.List;

public class ResumeConvertor {
    public static ResumeDto resumeDto(Resume r) {
        return new ResumeDto(
                r.getId(),
                r.getCandidate().getId(),
                r.getSalaryMin(),
                r.getDescription(),
                TagConvertor.tagDtos(r.getTags()),
                r.getDate()
        );
    }

    public static List<ResumeDto> resumeDtos(List<Resume> r) {
        return r.stream().map(ResumeConvertor::resumeDto).toList();
    }
}
