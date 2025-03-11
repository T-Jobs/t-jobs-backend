package ru.ns.t_jobs.app.candidate.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;
import ru.ns.t_jobs.app.candidate.dto.CandidateDto;
import ru.ns.t_jobs.app.candidate.dto.ResumeDto;
import ru.ns.t_jobs.app.candidate.service.CandidateService;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class CandidateControllerImpl implements CandidateController {

    private final CandidateService candidateService;

    @Override
    public List<CandidateDto> searchCandidates(
            String text,
            int page,
            int page_size,
            int salaryUpperBound,
            List<Long> tagIds
    ) {
        return candidateService.searchCandidates(text, page, page_size, salaryUpperBound, tagIds);
    }

    @Override
    public List<ResumeDto> getResumes(List<Long> resumeIds) {
        return candidateService.getResumes(resumeIds);
    }

    @Override
    public ResumeDto getResume(long id) {
        return candidateService.getResume(id);
    }
}
