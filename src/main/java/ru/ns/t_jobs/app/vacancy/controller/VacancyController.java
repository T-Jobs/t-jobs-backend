package ru.ns.t_jobs.app.vacancy.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.ns.t_jobs.app.vacancy.dto.VacancyDto;

import java.util.List;

@RequestMapping("/vacancy")
public interface VacancyController {
    @GetMapping("/search")
    List<VacancyDto> searchVacancies(
            @RequestParam("page") int page,
            @RequestParam("page_size") int page_size,
            @RequestParam(name = "salaryLowerBound", defaultValue = "0", required = false) int salaryLowerBound,
            @RequestParam(name = "tagIds", required = false) List<Long> tagIds);
}
