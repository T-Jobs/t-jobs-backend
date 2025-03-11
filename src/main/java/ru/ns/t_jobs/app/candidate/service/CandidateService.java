package ru.ns.t_jobs.app.candidate.service;

import ru.ns.t_jobs.app.candidate.dto.CandidateDto;
import ru.ns.t_jobs.app.candidate.dto.ResumeDto;

import java.util.List;

public interface CandidateService {
    List<CandidateDto> searchCandidates(String text, int page, int pageSize, int salaryUpperBound, List<Long> tagIds);
    List<ResumeDto> getResumes(List<Long> ids);
    ResumeDto getResume(Long id);
}
