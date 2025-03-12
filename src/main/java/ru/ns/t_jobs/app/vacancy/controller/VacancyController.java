package ru.ns.t_jobs.app.vacancy.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.ns.t_jobs.app.vacancy.dto.EditOrCreateVacancyDto;
import ru.ns.t_jobs.app.vacancy.dto.VacancyDto;

import java.util.List;

@RequestMapping("/vacancy")
public interface VacancyController {
    @GetMapping("/search")
    List<VacancyDto> searchVacancies(
            @RequestParam(name = "text", required = false, defaultValue = "") String text,
            @RequestParam("page") int page,
            @RequestParam("page_size") int page_size,
            @RequestParam(name = "salaryLowerBound", defaultValue = "0", required = false) int salaryLowerBound,
            @RequestParam(name = "tagIds", required = false) List<Long> tagIds);

    @PostMapping("/create")
    VacancyDto createVacancy(@RequestBody EditOrCreateVacancyDto vacancyDto);

    @PostMapping("/edit/{id}")
    VacancyDto editVacancy(@RequestBody EditOrCreateVacancyDto vacancyDto, @PathVariable("id") long id);
}
