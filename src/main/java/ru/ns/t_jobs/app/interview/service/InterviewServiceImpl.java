package ru.ns.t_jobs.app.interview.service;

import lombok.RequiredArgsConstructor;
import org.apache.coyote.BadRequestException;
import org.springframework.stereotype.Service;
import ru.ns.t_jobs.app.interview.dto.BaseInterviewDto;
import ru.ns.t_jobs.app.interview.dto.InterviewConvertor;
import ru.ns.t_jobs.app.interview.dto.InterviewDto;
import ru.ns.t_jobs.app.interview.entity.BaseInterview;
import ru.ns.t_jobs.app.interview.entity.BaseInterviewRepository;
import ru.ns.t_jobs.app.interview.entity.Interview;
import ru.ns.t_jobs.app.interview.entity.InterviewRepository;
import ru.ns.t_jobs.app.interview.entity.InterviewStatus;
import ru.ns.t_jobs.app.interview.entity.InterviewType;
import ru.ns.t_jobs.app.interview.entity.InterviewTypeRepository;
import ru.ns.t_jobs.app.staff.entity.Staff;
import ru.ns.t_jobs.app.staff.entity.StaffRepository;
import ru.ns.t_jobs.app.track.entity.Track;
import ru.ns.t_jobs.app.track.entity.TrackRepository;
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
    private final StaffRepository staffRepository;
    private final TrackRepository trackRepository;

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

    @Override
    public void deleteInterview(long id) {
        Interview interview = interviewRepository.findById(id)
                .orElseThrow(() -> noSuchInterviewException(id));
        Track track = interview.getTrack();

        if (interview.getStatus() == InterviewStatus.FAILED || interview.getStatus() == InterviewStatus.SUCCESS
                || track.isFinished()) {
            throw new RuntimeException();
        }

        track = trackRepository.getReferenceById(track.getId());
        interviewRepository.delete(interview);
        updateLastStatus(track);
        findInterviewerIfNeeded(track);
    }

    private void findInterviewerIfNeeded(Track track) {
        if (track.getInterviews() == null) return;

        for (Interview i : track.getInterviews()) {
            if (i.getStatus() != InterviewStatus.SUCCESS) {
                if (i.getInterviewer() == null) {
                    Staff interviewer = staffRepository.findRandomStaffByInterviewType(i.getInterviewType())
                            .orElseThrow();
                    i.setInterviewer(interviewer);
                    interviewRepository.save(i);
                }

                return;
            }
        }
    }

    private void updateLastStatus(Track track) {
        if (track.getInterviews() == null) return;

        InterviewStatus lastStatus = InterviewStatus.NONE;
        for (Interview i : track.getInterviews()) {
            if (i.getStatus() != InterviewStatus.SUCCESS || i == track.getInterviews().getLast()) {
                lastStatus = i.getStatus();
                break;
            }
        }

        track.setLastStatus(lastStatus);
        trackRepository.save(track);
    }

}
