package ru.ns.t_jobs.app.vacancy;

import ru.ns.t_jobs.app.Tag;

import java.util.List;

public record VacancyDto(
        Long id,
        String name,
        String description,
        int salaryMin,
        int salaryMax,
        String town,
        List<Long> interviews,
        List<Tag> tags,
        List<Long> staff,
        List<Long> tracks
) {
}
