package ru.ns.t_jobs.tg;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.ns.t_jobs.app.candidate.entity.Candidate;
import ru.ns.t_jobs.app.candidate.entity.CandidateRepository;
import ru.ns.t_jobs.app.interview.entity.Interview;
import ru.ns.t_jobs.app.interview.entity.InterviewRepository;
import ru.ns.t_jobs.app.track.entity.Track;
import ru.ns.t_jobs.app.track.entity.TrackRepository;
import ru.ns.t_jobs.app.track.service.TrackService;
import ru.ns.t_jobs.handler.exception.BadRequestException;
import ru.ns.t_jobs.tg.dto.TrackBotConvertor;
import ru.ns.t_jobs.tg.dto.TrackBotDto;
import ru.ns.t_jobs.tg.entity.NewCandidate;
import ru.ns.t_jobs.tg.entity.NewCandidateRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.time.temporal.Temporal;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Stream;

import static ru.ns.t_jobs.handler.exception.NotFoundExceptionFactory.noSuchTrackException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/bot/api")
public class TgBotController {

    private final NewCandidateRepository newCandidateRepository;
    private final CandidateRepository candidateRepository;
    private final TrackRepository trackRepository;
    private final TrackService trackService;
    private final InterviewRepository interviewRepository;

    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    private static final Stream<LocalTime> BASE_TIMES = Stream.of(
            LocalTime.of(12, 30),
            LocalTime.of(14, 0),
            LocalTime.of(16, 45),
            LocalTime.of(17, 0),
            LocalTime.of(18, 30)
    );

    @PostMapping("/register-chat-id")
    void registerChatId(@RequestParam("chat-id") long chatId) {
        Optional<NewCandidate> newCandidate = newCandidateRepository.findByChatId(chatId);
        Optional<Candidate> candidate = candidateRepository.findByChatId(chatId);
        if (newCandidate.isPresent() || candidate.isPresent()) return;

        newCandidateRepository.save(new NewCandidate(null, chatId));
    }

    @GetMapping("/filled-forms")
    boolean filledForms(@RequestParam("chat-id") long chatId) {
        Optional<Candidate> candidate = candidateRepository.findByChatId(chatId);
        return candidate.isPresent();
    }

    @GetMapping("/get-users-tracks")
    List<TrackBotDto> getUsersCurrentTracks(@RequestParam("chat-id") long chatId) {
        Candidate candidate = getCurrentUser(chatId);

        return TrackBotConvertor.trackBotDtos(
                trackRepository.findActiveTracksByCandidateId(candidate.getId())
        );
    }

    @GetMapping("/track/info")
    TrackBotDto getUsersTrackInfo(@RequestParam("chat-id") long chatId, @RequestParam("track-id") long trackId) {
        Candidate candidate = getCurrentUser(chatId);

        Track track = trackRepository.findById(trackId)
                .orElseThrow(() -> noSuchTrackException(trackId));

        if (!Objects.equals(track.getCandidate().getId(), candidate.getId()))
            throw new BadRequestException("");

        return TrackBotConvertor.trackBotDto(track);
    }

    @DeleteMapping("/track/finish")
    void finishUsersTrack(@RequestParam("chat-id") long chatId, @RequestParam("track-id") long trackId) {
        Candidate candidate = getCurrentUser(chatId);
        Track track = getUsersTrack(chatId, trackId);
        trackService.finishTrack(trackId);
    }

    @GetMapping("/track/available-time")
    List<String> getAvailableTimeSlots(@RequestParam("chat-id") long chatId, @RequestParam("track-id") long trackId) {
        LocalDate base = LocalDate.now();
        return BASE_TIMES.map(t -> t.atDate(base)).map(DATE_TIME_FORMATTER::format).toList();
    }

    @DeleteMapping("/track/pick-date")
    void pickDate(@RequestParam("chat-id") long chatId, @RequestParam("track-id") long trackId, @RequestParam("date") String date) {
        Interview curInterview = getTracksActiveInterview(chatId, trackId);

        curInterview.setDatePicked(LocalDateTime.parse(date, DATE_TIME_FORMATTER));
        curInterview.setDateApproved(false);

        interviewRepository.save(curInterview);
    }

    private Candidate getCurrentUser(long chatId) {
        return candidateRepository.findByChatId(chatId)
                .orElseThrow(() -> new BadRequestException("No registered users with %d chat id".formatted(chatId)));
    }

    private Track getUsersTrack(long chatId, long trackId) {
        Track track = trackRepository.findById(trackId)
                .orElseThrow(() -> noSuchTrackException(trackId));

        if (!Objects.equals(track.getCandidate().getChatId(), chatId) || track.isFinished())
            throw new BadRequestException("");

        return track;
    }

    private Interview getTracksActiveInterview(long chatId, long trackId) {
        Track track = getUsersTrack(chatId, trackId);

        Interview curInterview = track.getCurrentInterview();
        if (curInterview == null) {
            throw new BadRequestException("");
        }

        return curInterview;
    }
}
