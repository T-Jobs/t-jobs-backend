package ru.ns.t_jobs.app.vacancy.service;

import ru.ns.t_jobs.app.vacancy.dto.NewVacancyDto;
import ru.ns.t_jobs.app.vacancy.dto.VacancyDto;

import java.util.List;

public interface VacancyService {
    List<VacancyDto> searchVacancies(String text, int page, int pageSize, int salaryLowerBound, List<Long> tagIds);
    void createVacancy(NewVacancyDto vacancyDto);
    void editVacancy(NewVacancyDto vacancyDto, long id);
}
