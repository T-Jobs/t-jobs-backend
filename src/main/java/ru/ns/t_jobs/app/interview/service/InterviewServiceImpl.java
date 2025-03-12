package ru.ns.t_jobs.app.interview.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.ns.t_jobs.app.interview.dto.InterviewBaseDto;
import ru.ns.t_jobs.app.interview.dto.InterviewConvertor;
import ru.ns.t_jobs.app.interview.dto.InterviewDto;
import ru.ns.t_jobs.app.interview.entity.*;

import java.util.List;
import java.util.Optional;

import static ru.ns.t_jobs.handler.exception.NotFoundExceptionFactory.noSuchBaseInterviewException;
import static ru.ns.t_jobs.handler.exception.NotFoundExceptionFactory.noSuchInterviewException;

@Service
@RequiredArgsConstructor
public class InterviewServiceImpl implements InterviewService {

    private final InterviewRepository interviewRepository;
    private final InterviewTypeRepository interviewTypeRepository;
    private final InterviewBaseRepository interviewBaseRepository;

    @Override
    public InterviewDto getInterviewById(long id) {
        Optional<Interview> interviewOp = interviewRepository.findById(id);

        if (interviewOp.isEmpty())
            throw noSuchInterviewException(id);

        return InterviewConvertor.from(interviewOp.orElseThrow());
    }

    @Override
    public List<InterviewType> searchInterviewTypes(String name) {
        return interviewTypeRepository.findByNameIgnoreCaseContains(name);
    }

    @Override
    public InterviewBaseDto getInterviewBase(long id) {
        return InterviewConvertor.from(
                interviewBaseRepository.findById(id)
                        .orElseThrow(() -> noSuchBaseInterviewException(id))
        );
    }

    @Override
    public List<InterviewBaseDto> getInterviewBases(List<Long> ids) {
        return InterviewConvertor.from(
                interviewBaseRepository.findAllById(ids)
        );
    }

}
