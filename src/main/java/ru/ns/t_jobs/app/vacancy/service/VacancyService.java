package ru.ns.t_jobs.app.vacancy.service;

import ru.ns.t_jobs.app.vacancy.dto.VacancyDto;

import java.util.List;

public interface VacancyService {
    List<VacancyDto> searchVacancies(int page, int pageSize, int salaryUpperBound, List<Long> tagIds);
}
