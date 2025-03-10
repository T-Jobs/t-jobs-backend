package ru.ns.t_jobs.app.candidate.service;

import ru.ns.t_jobs.app.candidate.dto.CandidateDto;

import java.util.List;

public interface CandidateService {
    List<CandidateDto> searchCandidates(int page, int pageSize, int salaryUpperBound, List<Long> tagIds);
}
