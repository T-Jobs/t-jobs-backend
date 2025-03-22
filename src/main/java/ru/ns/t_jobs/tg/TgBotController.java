package ru.ns.t_jobs.tg;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.ns.t_jobs.app.candidate.entity.Candidate;
import ru.ns.t_jobs.app.candidate.entity.CandidateRepository;
import ru.ns.t_jobs.app.track.entity.Track;
import ru.ns.t_jobs.handler.exception.BadRequestException;
import ru.ns.t_jobs.tg.dto.VacancyBotConvertor;
import ru.ns.t_jobs.tg.dto.VacancyBotDto;
import ru.ns.t_jobs.tg.entity.NewCandidate;
import ru.ns.t_jobs.tg.entity.NewCandidateRepository;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/bot/api")
public class TgBotController {

    private final NewCandidateRepository newCandidateRepository;
    private final CandidateRepository candidateRepository;

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

    @GetMapping("/get-users-vacancies")
    List<VacancyBotDto> getUsersCurrentVacancies(@RequestParam("chat-id") long chatId) {
        Candidate candidate = candidateRepository.findByChatId(chatId)
                .orElseThrow(() -> new BadRequestException("No registered users with %d chat id".formatted(chatId)));

        return VacancyBotConvertor.vacancyBotDtos(
                candidate.getTracks().stream()
                        .filter(t -> !t.isFinished())
                        .map(Track::getVacancy)
                        .toList()
        );
    }


}
