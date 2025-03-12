package ru.ns.t_jobs.app.candidate.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.ns.t_jobs.app.candidate.dto.CandidateConvertor;
import ru.ns.t_jobs.app.candidate.dto.CandidateDto;
import ru.ns.t_jobs.app.candidate.dto.ResumeConvertor;
import ru.ns.t_jobs.app.candidate.dto.ResumeDto;
import ru.ns.t_jobs.app.candidate.entity.Candidate;
import ru.ns.t_jobs.app.candidate.entity.CandidateRepository;
import ru.ns.t_jobs.app.candidate.entity.Resume;
import ru.ns.t_jobs.app.candidate.entity.ResumeRepository;
import ru.ns.t_jobs.handler.exception.NotFoundExceptionFactory;

import java.util.List;

import static ru.ns.t_jobs.handler.exception.NotFoundExceptionFactory.noSuchCandidateException;
import static ru.ns.t_jobs.handler.exception.NotFoundExceptionFactory.noSuchResumeException;

@Service
@RequiredArgsConstructor
public class CandidateServiceImpl implements CandidateService {

    private final ResumeRepository resumeRepository;
    private final CandidateRepository candidateRepository;

    @Override
    public List<CandidateDto> searchCandidates(
            String text,
            int page,
            int pageSize,
            int salaryUpperBound,
            List<Long> tagIds
    ) {
        Pageable paging = PageRequest.of(page, pageSize);
        List<Candidate> candidates;
        if (tagIds != null) {
            candidates = resumeRepository.findAllByTags(text.toLowerCase(), tagIds, tagIds.size(), salaryUpperBound, paging);
        } else {
            candidates = resumeRepository.findAllByTags(text, salaryUpperBound, paging);
        }

        return CandidateConvertor.candidateDtos(candidates);
    }

    @Override
    public CandidateDto getCandidate(long id) {
        return CandidateConvertor.candidateDto(
                candidateRepository.findById(id).orElseThrow(() -> noSuchCandidateException(id))
        );
    }

    @Override
    public List<CandidateDto> getCandidates(List<Long> ids) {
        var res = candidateRepository.findAllById(ids);
        NotFoundExceptionFactory.containsAllOrThrow(
                res.stream().map(Candidate::getId).toList(), ids,
                NotFoundExceptionFactory::noSuchCandidatesException
        );
        return CandidateConvertor.candidateDtos(res);
    }

    @Override
    public List<ResumeDto> getResumes(List<Long> ids) {
        var res = resumeRepository.findAllById(ids);
        NotFoundExceptionFactory.containsAllOrThrow(
                res.stream().map(Resume::getId).toList(), ids,
                NotFoundExceptionFactory::noSuchResumesException
        );
        return ResumeConvertor.resumeDtos(res);
    }

    @Override
    public ResumeDto getResume(Long id) {
        return ResumeConvertor.resumeDto(
                resumeRepository.findById(id).orElseThrow(() -> noSuchResumeException(id))
        );
    }
}
