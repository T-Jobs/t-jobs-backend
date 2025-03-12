package ru.ns.t_jobs.app.interview.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.ns.t_jobs.app.interview.dto.BaseInterviewDto;
import ru.ns.t_jobs.app.interview.dto.InterviewConvertor;
import ru.ns.t_jobs.app.interview.dto.InterviewDto;
import ru.ns.t_jobs.app.interview.entity.*;
import ru.ns.t_jobs.handler.exception.NotFoundExceptionFactory;

import java.util.List;
import java.util.Optional;

import static ru.ns.t_jobs.handler.exception.NotFoundExceptionFactory.noSuchBaseInterviewException;
import static ru.ns.t_jobs.handler.exception.NotFoundExceptionFactory.noSuchInterviewException;

@Service
@RequiredArgsConstructor
public class InterviewServiceImpl implements InterviewService {

    private final InterviewRepository interviewRepository;
    private final InterviewTypeRepository interviewTypeRepository;
    private final BaseInterviewRepository baseInterviewRepository;

    @Override
    public InterviewDto getInterview(long id) {
        Optional<Interview> interviewOp = interviewRepository.findById(id);

        if (interviewOp.isEmpty())
            throw noSuchInterviewException(id);

        return InterviewConvertor.interviewDto(interviewOp.orElseThrow());
    }

    @Override
    public List<InterviewDto> getInterviews(List<Long> ids) {
        var res = interviewRepository.findAllById(ids);
        NotFoundExceptionFactory.containsAllOrThrow(
                res.stream().map(Interview::getId).toList(), ids,
                NotFoundExceptionFactory::noSuchInterviewsException
        );
        return InterviewConvertor.interviewDtos(res);
    }

    @Override
    public List<InterviewType> searchInterviewTypes(String name) {
        return interviewTypeRepository.findByNameIgnoreCaseContains(name);
    }

    @Override
    public BaseInterviewDto getBaseInterview(long id) {
        return InterviewConvertor.baseInterviewsDto(
                baseInterviewRepository.findById(id)
                        .orElseThrow(() -> noSuchBaseInterviewException(id))
        );
    }

    @Override
    public List<BaseInterviewDto> getBaseInterviews(List<Long> ids) {
        var res = baseInterviewRepository.findAllById(ids);
        NotFoundExceptionFactory.containsAllOrThrow(
                res.stream().map(BaseInterview::getId).toList(), ids,
                NotFoundExceptionFactory::noSuchBaseInterviewsException
        );
        return InterviewConvertor.baseInterviewDtos(
                baseInterviewRepository.findAllById(ids)
        );
    }

}
