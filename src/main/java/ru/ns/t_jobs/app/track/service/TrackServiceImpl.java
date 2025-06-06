package ru.ns.t_jobs.app.track.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.ns.t_jobs.app.candidate.entity.Candidate;
import ru.ns.t_jobs.app.candidate.entity.CandidateRepository;
import ru.ns.t_jobs.app.interview.dto.InterviewConvertor;
import ru.ns.t_jobs.app.interview.entity.Interview;
import ru.ns.t_jobs.app.interview.entity.InterviewRepository;
import ru.ns.t_jobs.app.interview.entity.InterviewStatus;
import ru.ns.t_jobs.app.interview.service.InterviewService;
import ru.ns.t_jobs.app.staff.entity.Staff;
import ru.ns.t_jobs.app.staff.entity.StaffRepository;
import ru.ns.t_jobs.app.track.dto.TrackConvertor;
import ru.ns.t_jobs.app.track.dto.TrackInfoDto;
import ru.ns.t_jobs.app.track.entity.Track;
import ru.ns.t_jobs.app.track.entity.TrackRepository;
import ru.ns.t_jobs.app.vacancy.entity.Vacancy;
import ru.ns.t_jobs.app.vacancy.entity.VacancyRepository;
import ru.ns.t_jobs.auth.util.ContextUtils;
import ru.ns.t_jobs.handler.exception.BadRequestException;
import ru.ns.t_jobs.handler.exception.NotFoundExceptionFactory;
import ru.ns.t_jobs.tg.BotNotifier;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static ru.ns.t_jobs.handler.exception.NotFoundExceptionFactory.noSuchApplicationException;
import static ru.ns.t_jobs.handler.exception.NotFoundExceptionFactory.noSuchCandidateException;
import static ru.ns.t_jobs.handler.exception.NotFoundExceptionFactory.noSuchHrException;
import static ru.ns.t_jobs.handler.exception.NotFoundExceptionFactory.noSuchStaffException;
import static ru.ns.t_jobs.handler.exception.NotFoundExceptionFactory.noSuchTrackException;
import static ru.ns.t_jobs.handler.exception.NotFoundExceptionFactory.noSuchVacancyException;

@Service
@RequiredArgsConstructor
public class TrackServiceImpl implements TrackService {

    private final TrackRepository trackRepository;
    private final CandidateRepository candidateRepository;
    private final VacancyRepository vacancyRepository;
    private final StaffRepository staffRepository;
    private final InterviewRepository interviewRepository;
    private final InterviewService interviewService;

    @Override
    public TrackInfoDto getTrack(long id) {
        Track track = trackRepository.findById(id)
                .orElseThrow(() -> noSuchTrackException(id));
        return TrackConvertor.trackInfoDto(track);
    }

    @Override
    public List<TrackInfoDto> getTracks(List<Long> ids) {
        var res = trackRepository.findAllById(ids);
        NotFoundExceptionFactory.containsAllOrThrow(
                res.stream().map(Track::getId).toList(), ids,
                NotFoundExceptionFactory::noSuchTracksException
        );
        return TrackConvertor.trackInfoDtos(res);
    }

    @Override
    public TrackInfoDto approveApplication(long candidateId, long vacancyId) {
        Candidate c = candidateRepository.findById(candidateId)
                .orElseThrow(() -> noSuchCandidateException(candidateId));

        if (c.getAppliedVacancies().stream().noneMatch(v -> v.getId() == vacancyId)) {
            throw noSuchApplicationException(candidateId, vacancyId);
        }

        Vacancy v = vacancyRepository.findById(vacancyId)
                .orElseThrow(() -> noSuchVacancyException(vacancyId));
        Staff s = staffRepository.getReferenceById(ContextUtils.getCurrentUserStaffId());
        c.getAppliedVacancies().remove(v);
        candidateRepository.save(c);

        Track newTrack = Track.builder()
                .hr(s)
                .finished(false)
                .lastStatus(InterviewStatus.NONE)
                .hr(s)
                .candidate(c)
                .vacancy(v)
                .build();

        var res = trackRepository.save(newTrack);
        List<Interview> interviews = InterviewConvertor.interviews(v.getBaseInterviews(), res);
        interviews = interviewRepository.saveAll(interviews);

        Staff interviewer = staffRepository.findRandomStaffByInterviewType(interviews.getFirst().getInterviewType())
                .orElseThrow();
        interviews.getFirst().setInterviewer(interviewer);
        interviewRepository.save(interviews.getFirst());

        res.setInterviews(interviews);

        BotNotifier.notifyApprovedApplication(res);
        return TrackConvertor.trackInfoDto(res);
    }

    @Override
    public void declineApplication(long candidateId, long vacancyId) {
        Candidate c = candidateRepository.findById(candidateId)
                .orElseThrow(() -> noSuchCandidateException(candidateId));

        if (c.getAppliedVacancies().stream().noneMatch(v -> v.getId() == vacancyId)) {
            throw noSuchApplicationException(candidateId, vacancyId);
        }

        Vacancy v = vacancyRepository.findById(vacancyId)
                .orElseThrow(() -> noSuchVacancyException(vacancyId));
        Staff s = staffRepository.getReferenceById(ContextUtils.getCurrentUserStaffId());
        c.getAppliedVacancies().remove(v);
        candidateRepository.save(c);

        BotNotifier.notifyDeclinedApplication(c, v);
    }

    @Override
    public void setHr(long trackId, long hrId) {
        Track t = trackRepository.findById(trackId).orElseThrow(() -> noSuchTrackException(trackId));
        Staff hr = staffRepository.findById(hrId).orElseThrow(() -> noSuchStaffException(hrId));

        if (hr.getRoles().stream().noneMatch(r -> Objects.equals(r.getName(), "HR"))) {
            throw noSuchHrException(hrId);
        }

        t.setHr(hr);
        trackRepository.save(t);
    }

    @Override
    public void finishTrack(long id) {
        Track t = trackRepository.findById(id).orElseThrow(() -> noSuchTrackException(id));

        InterviewStatus lastStatus = InterviewStatus.NONE;
        List<Interview> toDelete = new ArrayList<>();
        for (Interview i : t.getInterviews()) {
            if (i.getStatus() == InterviewStatus.FAILED || i.getStatus() == InterviewStatus.SUCCESS) {
                lastStatus = i.getStatus();
            } else {
                toDelete.add(i);
            }
        }

        t.setFinished(true);
        t.setLastStatus(lastStatus);
        trackRepository.save(t);

        interviewRepository.deleteAll(toDelete);
        BotNotifier.notifyFinishedTrack(t);
    }

    @Override
    public TrackInfoDto createTrack(long candidateId, long vacancyId) {
        Candidate c = candidateRepository.findById(candidateId)
                .orElseThrow(() -> noSuchCandidateException(candidateId));

        Vacancy v = vacancyRepository.findById(vacancyId)
                .orElseThrow(() -> noSuchVacancyException(vacancyId));
        Staff s = staffRepository.getReferenceById(ContextUtils.getCurrentUserStaffId());

        Track newTrack = Track.builder()
                .hr(s)
                .finished(false)
                .lastStatus(InterviewStatus.NONE)
                .hr(s)
                .candidate(c)
                .vacancy(v)
                .build();

        var res = trackRepository.save(newTrack);
        List<Interview> interviews = InterviewConvertor.interviews(v.getBaseInterviews(), res);
        interviews = interviewRepository.saveAll(interviews);

        Staff interviewer = staffRepository.findRandomStaffByInterviewType(interviews.getFirst().getInterviewType())
                .orElseThrow();
        interviews.getFirst().setInterviewer(interviewer);
        interviewRepository.save(interviews.getFirst());

        res.setInterviews(interviews);
        BotNotifier.notifyStartedTrack(res);
        return TrackConvertor.trackInfoDto(res);
    }

    @Override
    public void continueTrack(long trackId) {
        Track t = trackRepository.findById(trackId).orElseThrow(() -> noSuchTrackException(trackId));

        if (t.isFinished()) {
            throw new BadRequestException("Track %d is finished.".formatted(trackId));
        }



        Interview i = t.getInterviews().stream().filter(e -> e.getStatus() == InterviewStatus.FAILED).findFirst()
                .orElseThrow(() -> new BadRequestException("Track has not any failed interview."));

        interviewService.setFeedback(i, true, i.getFeedback());
    }
}
