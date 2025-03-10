package ru.ns.t_jobs.app.vacancy.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.ns.t_jobs.app.candidate.dto.CandidateConvertor;
import ru.ns.t_jobs.app.candidate.dto.CandidateDto;
import ru.ns.t_jobs.app.candidate.entity.ResumeRepository;
import ru.ns.t_jobs.app.vacancy.dto.VacancyConvertor;
import ru.ns.t_jobs.app.vacancy.dto.VacancyDto;
import ru.ns.t_jobs.app.vacancy.entity.VacancyRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class VacancyServiceImpl implements VacancyService {

    private final VacancyRepository vacancyRepository;

    @Override
    public List<VacancyDto> searchVacancies(int page, int pageSize, int salaryUpperBound, List<Long> tagIds) {
        if (tagIds == null || tagIds.isEmpty()) return searchCandidates(page, pageSize, salaryUpperBound);

        Pageable paging = PageRequest.of(page, pageSize);
        return VacancyConvertor.from(
                vacancyRepository.findAllByTags(tagIds, tagIds.size(), salaryUpperBound, paging)
        );
    }

    List<VacancyDto> searchCandidates(int page, int pageSize, int salaryUpperBound) {
        Pageable paging = PageRequest.of(page, pageSize);
        return VacancyConvertor.from(vacancyRepository.findAllByTags(salaryUpperBound, paging));
    }
}
