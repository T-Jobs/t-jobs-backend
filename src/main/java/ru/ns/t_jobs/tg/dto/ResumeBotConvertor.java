package ru.ns.t_jobs.tg.dto;

import ru.ns.t_jobs.app.candidate.entity.Resume;
import ru.ns.t_jobs.app.tag.entity.Tag;

import java.util.Collection;
import java.util.List;

public class ResumeBotConvertor {
    public static ResumeShortDto resumeShortDto(Resume r) {
        return new ResumeShortDto(
                r.getId(),
                r.getName(),
                r.getSalaryMin(),
                r.getTags().stream().map(Tag::getName).toList()
        );
    }

    public static ResumeFullDto resumeFullDto(Resume r) {
        return new ResumeFullDto(
                r.getId(),
                r.getName(),
                r.getSalaryMin(),
                r.getTags().stream().map(Tag::getName).toList(),
                r.getDescription()
        );
    }

    public static List<ResumeShortDto> resumeShortDtos(Collection<Resume> rs) {
        return rs.stream().map(ResumeBotConvertor::resumeShortDto).toList();
    }

    public static List<ResumeFullDto> resumeFullDtos(Collection<Resume> rs) {
        return rs.stream().map(ResumeBotConvertor::resumeFullDto).toList();
    }
}
