package ru.ns.t_jobs.app.vacancy.controller;

import org.springframework.web.bind.annotation.*;
import ru.ns.t_jobs.app.vacancy.dto.NewVacancyDto;
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
    void createVacancy(@RequestBody NewVacancyDto vacancyDto);

    @PostMapping("/edit/{id}")
    void editVacancy(@RequestBody NewVacancyDto vacancyDto, @PathVariable("id") long id);
}
