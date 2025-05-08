package ru.ns.t_jobs.app.interview.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.ns.t_jobs.app.interview.dto.BaseInterviewDto;
import ru.ns.t_jobs.app.interview.dto.CreateInterviewDto;
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
import ru.ns.t_jobs.auth.util.ContextUtils;
import ru.ns.t_jobs.handler.exception.BadRequestException;
import ru.ns.t_jobs.handler.exception.NotFoundExceptionFactory;
import ru.ns.t_jobs.tg.BotNotifier;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static ru.ns.t_jobs.handler.exception.NotFoundExceptionFactory.noSuchBaseInterviewException;
import static ru.ns.t_jobs.handler.exception.NotFoundExceptionFactory.noSuchInterviewException;
import static ru.ns.t_jobs.handler.exception.NotFoundExceptionFactory.noSuchInterviewTypeException;
import static ru.ns.t_jobs.handler.exception.NotFoundExceptionFactory.noSuchStaffException;
import static ru.ns.t_jobs.handler.exception.NotFoundExceptionFactory.noSuchTrackException;

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

        if (interview.getStatus() == InterviewStatus.FAILED || interview.getStatus() == InterviewStatus.SUCCESS) {
            throw new BadRequestException("Interview %d is finished.".formatted(id));
        }

        if (Objects.equals(track.getCurrentInterview().getId(), interview.getId())) {
            BotNotifier.notifyCanceledInterview(interview);
        }

        interviewRepository.delete(interview);

        track = trackRepository.getReferenceById(track.getId());
        updateLastStatus(track);
        findInterviewerIfNeeded(track);
        validateInterviewOrder(track);
    }

    @Override
    public InterviewDto addInterview(CreateInterviewDto createInterviewDto) {
        Track t = trackRepository.findById(createInterviewDto.trackId())
                .orElseThrow(() -> noSuchTrackException(createInterviewDto.trackId()));

        if (t.isFinished()) throw new BadRequestException("%d Track is finished.".formatted(t.getId()));

        InterviewType type = interviewTypeRepository.findById(createInterviewDto.interviewTypeId())
                .orElseThrow(() -> noSuchInterviewTypeException(createInterviewDto.interviewTypeId()));

        Interview interview = Interview.builder()
                .interviewType(type)
                .track(t)
                .dateApproved(createInterviewDto.date() != null)
                .datePicked(createInterviewDto.date())
                .interviewer(null)
                .status(InterviewStatus.NONE)
                .feedback(null)
                .link(createInterviewDto.link())
                .interviewOrder(Objects.requireNonNullElse(t.getInterviews(), List.of()).size())
                .build();

        if (createInterviewDto.interviewerId() != null) {
            Staff interviewer = staffRepository.findById(createInterviewDto.interviewerId())
                    .orElseThrow(() -> noSuchStaffException(createInterviewDto.interviewerId()));

            interview.setInterviewer(interviewer);
        }

        var res = interviewRepository.save(interview);

        t = trackRepository.getReferenceById(t.getId());
        findInterviewerIfNeeded(t);
        updateLastStatus(t);

        return InterviewConvertor.interviewDto(interviewRepository.getReferenceById(res.getId()));
    }

    @Override
    public void setInterviewer(long interviewId, long interviewerId) {
        Interview interview = interviewRepository.findById(interviewId)
                .orElseThrow(() -> noSuchInterviewException(interviewId));
        Staff interviewer = staffRepository.findById(interviewerId)
                .orElseThrow(() -> noSuchStaffException(interviewerId));

        if (interview.getStatus() == InterviewStatus.FAILED || interview.getStatus() == InterviewStatus.SUCCESS) {
            throw new BadRequestException("Interview %d is finished.".formatted(interviewId));
        }

        interview.setInterviewer(interviewer);
        interviewRepository.save(interview);
    }

    @Override
    public void setAutoInterviewer(long interviewId) {
        Interview interview = interviewRepository.findById(interviewId)
                .orElseThrow(() -> noSuchInterviewException(interviewId));

        if (interview.getStatus() == InterviewStatus.FAILED || interview.getStatus() == InterviewStatus.SUCCESS) {
            throw new BadRequestException("Interview %d is finished.".formatted(interviewId));
        }

        Staff interviewer = staffRepository.findRandomStaffByInterviewType(interview.getInterviewType())
                .orElseThrow();

        interview.setInterviewer(interviewer);
        interviewRepository.save(interview);
    }

    @Override
    public void setDate(long interviewId, LocalDateTime date) {
        Interview interview = interviewRepository.findById(interviewId)
                .orElseThrow(() -> noSuchInterviewException(interviewId));

        if (interview.getStatus() == InterviewStatus.FAILED || interview.getStatus() == InterviewStatus.SUCCESS) {
            throw new BadRequestException("Interview %d is finished.".formatted(interviewId));
        }

        int pos = interview.getInterviewOrder();
        Track t = interview.getTrack();
        if (pos == 0 || t.getInterviews().get(pos - 1).getStatus() == InterviewStatus.SUCCESS) {
            interview.setDatePicked(date);
            interview.setDateApproved(true);
            interview.setStatus(InterviewStatus.WAITING_FEEDBACK);
            interviewRepository.save(interview);

            t.setLastStatus(InterviewStatus.WAITING_FEEDBACK);
            trackRepository.save(t);

            BotNotifier.notifyDateInterview(interview);
        } else {
            throw new BadRequestException("Too early. Interview %d is not relevant.".formatted(interviewId));
        }
    }

    @Override
    public void setAutoDate(long interviewId) {
        Interview interview = interviewRepository.findById(interviewId)
                .orElseThrow(() -> noSuchInterviewException(interviewId));

        if (interview.getStatus() == InterviewStatus.FAILED || interview.getStatus() == InterviewStatus.SUCCESS) {
            throw new BadRequestException("Interview %d is finished.".formatted(interviewId));
        }

        int pos = interview.getInterviewOrder();
        Track t = interview.getTrack();
        if (pos == 0 || t.getInterviews().get(pos - 1).getStatus() == InterviewStatus.SUCCESS) {
            interview.setDatePicked(null);
            interview.setDateApproved(false);
            interviewRepository.save(interview);
        } else {
            throw new BadRequestException("Too early. Interview %d is not relevant.".formatted(interviewId));
        }
    }

    @Override
    public void setLink(long interviewId, String link) {
        Interview interview = interviewRepository.findById(interviewId)
                .orElseThrow(() -> noSuchInterviewException(interviewId));

        if (interview.getStatus() == InterviewStatus.FAILED || interview.getStatus() == InterviewStatus.SUCCESS) {
            throw new BadRequestException("Interview %d is finished.".formatted(interviewId));
        }

        interview.setLink(link);
        interviewRepository.save(interview);
    }

    @Override
    public void setFeedback(long interviewId, boolean success, String feedback) {
        Interview interview = interviewRepository.findById(interviewId)
                .orElseThrow(() -> noSuchInterviewException(interviewId));

        if (interview.getStatus() == InterviewStatus.FAILED || interview.getStatus() == InterviewStatus.SUCCESS) {
            throw new BadRequestException("Interview %d is finished.".formatted(interviewId));
        }

        Staff interviewer = interview.getInterviewer();
        if (!Objects.equals(interviewer.getId(), ContextUtils.getCurrentUserStaffId())
                || interview.getStatus() != InterviewStatus.WAITING_FEEDBACK) {
            throw new BadRequestException("Unable to set feedback.");
        }

        setFeedback(interview, success, feedback);
    }

    @Override
    public void setFeedback(Interview interview, boolean success, String feedback) {
        int pos = interview.getInterviewOrder();
        Track t = interview.getTrack();
        if (pos == 0 || t.getInterviews().get(pos - 1).getStatus() == InterviewStatus.SUCCESS) {
            interview.setFeedback(feedback);
            if (success) {
                interview.setStatus(InterviewStatus.SUCCESS);
                BotNotifier.notifySucceedInterview(interview);
            } else {
                interview.setStatus(InterviewStatus.FAILED);
                BotNotifier.notifyFailedInterview(interview);
            }
            interviewRepository.save(interview);

            t = trackRepository.getReferenceById(t.getId());
            updateLastStatus(t);
            findInterviewerIfNeeded(t);
        } else {
            throw new BadRequestException("Too early. Interview %d is not relevant.".formatted(interview.getId()));
        }
    }

    @Override
    public void approveTime(long interviewId) {
        Interview interview = interviewRepository.findById(interviewId)
                .orElseThrow(() -> noSuchInterviewException(interviewId));

        if (interview.getStatus() == InterviewStatus.FAILED || interview.getStatus() == InterviewStatus.SUCCESS
                || interview.getDatePicked() == null || interview.isDateApproved()
                || !Objects.equals(interview.getInterviewer().getId(), ContextUtils.getCurrentUserStaffId())) {
            throw new BadRequestException("");
        }

        int pos = interview.getInterviewOrder();
        Track t = interview.getTrack();
        if (pos == 0 || t.getInterviews().get(pos - 1).getStatus() == InterviewStatus.SUCCESS) {
            interview.setDateApproved(true);
            interview.setStatus(InterviewStatus.WAITING_FEEDBACK);
            interviewRepository.save(interview);

            t.setLastStatus(InterviewStatus.WAITING_FEEDBACK);
            trackRepository.save(t);

            BotNotifier.notifyDateInterview(interview);
        } else {
            throw new BadRequestException("Too early. Interview %d is not relevant.".formatted(interviewId));
        }
    }

    @Override
    public void declinePickedTime(long interviewId) {
        Interview interview = interviewRepository.findById(interviewId)
                .orElseThrow(() -> noSuchInterviewException(interviewId));

        if (interview.getStatus() == InterviewStatus.FAILED || interview.getStatus() == InterviewStatus.SUCCESS
                || interview.getDatePicked() == null
                || !Objects.equals(interview.getInterviewer().getId(), ContextUtils.getCurrentUserStaffId())) {
            throw new BadRequestException("Either interview is finished or you not the who conducting this interview.");
        }

        int pos = interview.getInterviewOrder();
        Track t = interview.getTrack();
        if (pos == 0 || t.getInterviews().get(pos - 1).getStatus() == InterviewStatus.SUCCESS) {
            interview.setDatePicked(null);
            interview.setDateApproved(false);
            interviewRepository.save(interview);
            BotNotifier.notifyDateDeclined(interview);
        } else {
            throw new BadRequestException("Too early. Interview %d is not relevant.".formatted(interviewId));
        }
    }

    private void validateInterviewOrder(Track track) {
        if (track.getInterviews() == null) return;

        for (int i = 0; i < track.getInterviews().size(); i++) {
            Interview interview = track.getInterviews().get(i);
            if (interview.getInterviewOrder() != i) {
                interview.setInterviewOrder(i);
                interviewRepository.save(interview);
            }
        }
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
