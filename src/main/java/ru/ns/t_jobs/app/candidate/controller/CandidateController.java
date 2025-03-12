package ru.ns.t_jobs.app.candidate.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.ns.t_jobs.app.candidate.dto.CandidateDto;
import ru.ns.t_jobs.app.candidate.dto.ResumeDto;

import java.util.List;

@RequestMapping("/candidate")
public interface CandidateController {
    @GetMapping("/search")
    List<CandidateDto> searchCandidates(
            @RequestParam(name = "text", required = false, defaultValue = "") String text,
            @RequestParam("page") int page,
            @RequestParam("page_size") int page_size,
            @RequestParam(name = "salary_upper_bound", defaultValue = "99999999", required = false) int salaryUpperBound,
            @RequestParam(name = "tag_ids", required = false) List<Long> tagIds);

    @GetMapping("/resumes")
    List<ResumeDto> getResumes(@RequestParam(name = "ids") List<Long> resumeIds);

    @GetMapping("/resume/{id}")
    ResumeDto getResume(@PathVariable("id") long id);
}
