package ru.ns.t_jobs.app.track.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.ns.t_jobs.app.candidate.entity.Candidate;
import ru.ns.t_jobs.app.candidate.entity.CandidateRepository;
import ru.ns.t_jobs.app.interview.dto.InterviewConvertor;
import ru.ns.t_jobs.app.interview.entity.Interview;
import ru.ns.t_jobs.app.interview.entity.InterviewRepository;
import ru.ns.t_jobs.app.interview.entity.InterviewStatus;
import ru.ns.t_jobs.app.staff.entity.Staff;
import ru.ns.t_jobs.app.staff.entity.StaffRepository;
import ru.ns.t_jobs.app.track.dto.TrackConvertor;
import ru.ns.t_jobs.app.track.dto.TrackInfoDto;
import ru.ns.t_jobs.app.track.entity.Track;
import ru.ns.t_jobs.app.track.entity.TrackRepository;
import ru.ns.t_jobs.app.vacancy.entity.Vacancy;
import ru.ns.t_jobs.app.vacancy.entity.VacancyRepository;
import ru.ns.t_jobs.auth.util.ContextUtils;

import java.util.List;
import java.util.Optional;

import static ru.ns.t_jobs.handler.exception.NotFoundExceptionFactory.noSuchApplicationException;
import static ru.ns.t_jobs.handler.exception.NotFoundExceptionFactory.noSuchCandidateException;
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

    @Override
    public TrackInfoDto getTrackById(long id) {
        Optional<Track> trackOp = trackRepository.findById(id);

        if (trackOp.isEmpty())
            throw noSuchTrackException(id);

        Track track = trackOp.orElseThrow();
        return TrackConvertor.trackInfoDto(track);
    }

    @Override
    public TrackInfoDto approveApplication(long candidateId, long vacancyId) {
        Candidate c = candidateRepository.findById(candidateId)
                .orElseThrow(() -> noSuchCandidateException(candidateId));

        if (c.getAppliedVacancies().stream().noneMatch(v -> v.getId() == vacancyId)) {
            throw noSuchApplicationException(candidateId, vacancyId);
        }

        Vacancy v = vacancyRepository.findById(candidateId)
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
        List<Interview> interviews = InterviewConvertor.interviews(v.getInterviewBases(), res);
        interviews = interviewRepository.saveAll(interviews);

        res.setInterviews(interviews);
        return TrackConvertor.trackInfoDto(res);
    }
}
