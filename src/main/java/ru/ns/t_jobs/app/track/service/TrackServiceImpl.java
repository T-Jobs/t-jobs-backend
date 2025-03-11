package ru.ns.t_jobs.app.track.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.ns.t_jobs.app.candidate.entity.Candidate;
import ru.ns.t_jobs.app.candidate.entity.CandidateRepository;
import ru.ns.t_jobs.app.interview.entity.Interview;
import ru.ns.t_jobs.app.interview.entity.InterviewBaseConvertor;
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
import ru.ns.t_jobs.auth.user.Credentials;
import ru.ns.t_jobs.auth.util.AuthUtils;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

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
            throw new NoSuchElementException("No tracks with %d id".formatted(id));

        Track track = trackOp.orElseThrow();
        return TrackConvertor.from(track);
    }

    @Override
    public TrackInfoDto approveApplication(long candidateId, long vacancyId) {
        Candidate c = candidateRepository.findById(candidateId).orElseThrow(
                () -> new NoSuchElementException("No such candidate with %d id.".formatted(candidateId))
        );

        if (c.getAppliedVacancies().stream().noneMatch(v -> v.getId() == vacancyId)) {
            throw new NoSuchElementException("No such application from candidate with %d id to vacancy with %d id"
                    .formatted(candidateId, vacancyId));
        }

        Vacancy v = vacancyRepository.findById(candidateId).orElseThrow(
                () -> new NoSuchElementException("No such vacancy with %d id.".formatted(candidateId))
        );
        Staff s = ((Credentials) AuthUtils.getCurrentUserDetails()).getStaff();
        s = staffRepository.getReferenceById(s.getId());
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
        List<Interview> interviews = InterviewBaseConvertor.from(v.getInterviewBases(), res);
        interviews = interviewRepository.saveAll(interviews);

        res.setInterviews(interviews);
        return TrackConvertor.from(res);
    }
}
