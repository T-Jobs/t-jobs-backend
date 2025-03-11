package ru.ns.t_jobs.app.vacancy.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;
import ru.ns.t_jobs.app.vacancy.dto.NewVacancyDto;
import ru.ns.t_jobs.app.vacancy.dto.VacancyDto;
import ru.ns.t_jobs.app.vacancy.service.VacancyService;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class VacancyControllerImpl implements VacancyController {

    private final VacancyService vacancyService;

    @Override
    public List<VacancyDto> searchVacancies(String text, int page, int page_size, int salaryLowerBound, List<Long> tagIds) {
        return vacancyService.searchVacancies(text, page, page_size, salaryLowerBound, tagIds);
    }

    @Override
    public void createVacancy(NewVacancyDto vacancyDto) {
        vacancyService.createVacancy(vacancyDto);
    }

    @Override
    public void editVacancy(NewVacancyDto vacancyDto, long id) {
        vacancyService.editVacancy(vacancyDto, id);
    }
}
