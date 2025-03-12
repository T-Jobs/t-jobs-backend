package ru.ns.t_jobs.app.vacancy.service;

import ru.ns.t_jobs.app.vacancy.dto.EditOrCreateVacancyDto;
import ru.ns.t_jobs.app.vacancy.dto.VacancyDto;

import java.util.List;

public interface VacancyService {
    List<VacancyDto> searchVacancies(String text, int page, int pageSize, int salaryLowerBound, List<Long> tagIds);
    VacancyDto createVacancy(EditOrCreateVacancyDto vacancyDto);
    VacancyDto editVacancy(EditOrCreateVacancyDto vacancyDto, long id);
    VacancyDto getVacancy(long id);
    List<VacancyDto> getVacancies(List<Long> ids);
}
