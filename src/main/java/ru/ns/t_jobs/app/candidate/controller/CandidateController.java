package ru.ns.t_jobs.app.candidate.controller;

import org.springframework.web.bind.annotation.*;
import ru.ns.t_jobs.app.candidate.dto.CandidateDto;
import ru.ns.t_jobs.app.candidate.dto.ResumeDto;
import ru.ns.t_jobs.app.track.dto.TrackInfoDto;

import java.util.List;

@RequestMapping("/candidate")
public interface CandidateController {
    @GetMapping("/search")
    List<CandidateDto> searchCandidates(
            @RequestParam(name = "text", required = false, defaultValue = "") String text,
            @RequestParam("page") int page,
            @RequestParam("page_size") int page_size,
            @RequestParam(name = "salaryUpperBound", defaultValue = "99999999", required = false) int salaryUpperBound,
            @RequestParam(name = "tagIds", required = false) List<Long> tagIds);

    @GetMapping("/resumes")
    List<ResumeDto> getResumes(@RequestParam(name = "ids") List<Long> resumeIds);

    @GetMapping("/resume/{id}")
    ResumeDto getResume(@PathVariable("id") long id);
}
