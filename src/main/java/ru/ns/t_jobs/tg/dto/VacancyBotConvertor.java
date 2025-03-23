package ru.ns.t_jobs.tg.dto;

import ru.ns.t_jobs.app.tag.entity.Tag;
import ru.ns.t_jobs.app.vacancy.entity.Vacancy;

import java.util.Collection;
import java.util.List;

public class VacancyBotConvertor {
    public static VacancyBotDto vacancyBotDto(Vacancy v) {
        return new VacancyBotDto(
                v.getId(),
                v.getName(),
                v.getSalaryMin(),
                v.getSalaryMax(),
                v.getDescription(),
                v.getTown(),
                v.getTags().stream().map(Tag::getName).toList()
        );
    }

    public static List<VacancyBotDto> vacancyBotDtos(Collection<Vacancy> v) {
        return v.stream().map(VacancyBotConvertor::vacancyBotDto).toList();
    }
}
