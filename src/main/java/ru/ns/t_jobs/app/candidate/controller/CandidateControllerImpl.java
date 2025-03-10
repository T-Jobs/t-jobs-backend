package ru.ns.t_jobs.app.candidate.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;
import ru.ns.t_jobs.app.candidate.dto.CandidateDto;
import ru.ns.t_jobs.app.candidate.service.CandidateService;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class CandidateControllerImpl implements CandidateController{

    private final CandidateService candidateService;

    @Override
    public List<CandidateDto> searchCandidates(int page, int page_size, int salaryUpperBound, List<Long> tagIds) {
        return candidateService.searchCandidates(page, page_size, salaryUpperBound, tagIds);
    }
}
