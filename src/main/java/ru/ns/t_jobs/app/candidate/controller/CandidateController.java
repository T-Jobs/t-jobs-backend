package ru.ns.t_jobs.app.candidate.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.ns.t_jobs.app.candidate.dto.CandidateDto;

import java.util.List;

@RequestMapping("/candidate")
public interface CandidateController {
    @GetMapping("/search")
    List<CandidateDto> searchCandidates(
            @RequestParam("page") int page,
            @RequestParam("page_size") int page_size,
            @RequestParam(name = "salaryUpperBound", defaultValue = "99999999", required = false) int salaryUpperBound,
            @RequestParam(name = "tagIds", required = false) List<Long> tagIds);
}
