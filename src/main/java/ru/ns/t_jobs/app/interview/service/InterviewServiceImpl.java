package ru.ns.t_jobs.app.interview.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.ns.t_jobs.app.interview.dto.InterviewBaseDto;
import ru.ns.t_jobs.app.interview.dto.InterviewConvertor;
import ru.ns.t_jobs.app.interview.dto.InterviewDto;
import ru.ns.t_jobs.app.interview.entity.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static ru.ns.t_jobs.handler.exception.NotFoundExceptionFactory.*;

@Service
@RequiredArgsConstructor
public class InterviewServiceImpl implements InterviewService {

    private final InterviewRepository interviewRepository;
    private final InterviewTypeRepository interviewTypeRepository;
    private final InterviewBaseRepository interviewBaseRepository;

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
        if (res.size() != ids.size()) {
            var notFound = new ArrayList<>(ids);
            notFound.removeAll(res.stream().map(Interview::getId).toList());
            throw noSuchInterviewsException(notFound);
        }
        return InterviewConvertor.interviewDtos(res);
    }

    @Override
    public List<InterviewType> searchInterviewTypes(String name) {
        return interviewTypeRepository.findByNameIgnoreCaseContains(name);
    }

    @Override
    public InterviewBaseDto getInterviewBase(long id) {
        return InterviewConvertor.interviewBaseDto(
                interviewBaseRepository.findById(id)
                        .orElseThrow(() -> noSuchBaseInterviewException(id))
        );
    }

    @Override
    public List<InterviewBaseDto> getInterviewBases(List<Long> ids) {
        var res = interviewBaseRepository.findAllById(ids);
        if (res.size() != ids.size()) {
            var notFound = new ArrayList<>(ids);
            notFound.removeAll(res.stream().map(InterviewBase::getId).toList());
            throw noSuchBaseInterviewsException(notFound);
        }
        return InterviewConvertor.interviewBaseDtos(
                interviewBaseRepository.findAllById(ids)
        );
    }

}
