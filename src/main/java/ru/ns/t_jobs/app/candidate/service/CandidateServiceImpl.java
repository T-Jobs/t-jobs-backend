package ru.ns.t_jobs.app.candidate.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.ns.t_jobs.app.candidate.dto.CandidateConvertor;
import ru.ns.t_jobs.app.candidate.dto.CandidateDto;
import ru.ns.t_jobs.app.candidate.dto.ResumeDto;
import ru.ns.t_jobs.app.candidate.entity.ResumeConvertor;
import ru.ns.t_jobs.app.candidate.entity.ResumeRepository;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class CandidateServiceImpl implements CandidateService {

    private final ResumeRepository resumeRepository;

    @Override
    public List<CandidateDto> searchCandidates(int page, int pageSize, int salaryUpperBound, List<Long> tagIds) {
        if (tagIds == null || tagIds.isEmpty()) return searchCandidates(page, pageSize, salaryUpperBound);

        Pageable paging = PageRequest.of(page, pageSize);
        return CandidateConvertor.from(
                resumeRepository.findAllByTags(tagIds, tagIds.size(), salaryUpperBound, paging)
        );
    }

    @Override
    public List<ResumeDto> getResumes(List<Long> ids) {
        return ResumeConvertor.from(resumeRepository.findAllById(ids));
    }

    @Override
    public ResumeDto getResume(Long id) {
        return ResumeConvertor.from(
                resumeRepository.findById(id)
                        .orElseThrow(() -> new NoSuchElementException("No resume with %d id".formatted(id)))
        );
    }

    List<CandidateDto> searchCandidates(int page, int pageSize, int salaryUpperBound) {
        Pageable paging = PageRequest.of(page, pageSize);
        return CandidateConvertor.from(resumeRepository.findAllByTags(salaryUpperBound, paging));
    }
}
