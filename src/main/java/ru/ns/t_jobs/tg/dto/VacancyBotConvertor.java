package ru.ns.t_jobs.tg.dto;

import ru.ns.t_jobs.app.vacancy.entity.Vacancy;

import java.util.Collection;
import java.util.List;

public class VacancyBotConvertor {
    public static VacancyBotDto vacancyBotDto(Vacancy v) {
        return new VacancyBotDto(v.getId(), v.getName());
    }

    public static List<VacancyBotDto> vacancyBotDtos(Collection<Vacancy> vs) {
        return vs.stream().map(VacancyBotConvertor::vacancyBotDto).toList();
    }
}
