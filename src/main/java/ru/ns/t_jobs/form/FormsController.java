package ru.ns.t_jobs.form;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.ns.t_jobs.app.candidate.entity.Candidate;
import ru.ns.t_jobs.app.candidate.entity.CandidateRepository;
import ru.ns.t_jobs.app.candidate.entity.Resume;
import ru.ns.t_jobs.app.candidate.entity.ResumeRepository;
import ru.ns.t_jobs.app.tag.entity.TagRepository;
import ru.ns.t_jobs.form.dto.AboutMeForm;
import ru.ns.t_jobs.form.dto.ResumeForm;
import ru.ns.t_jobs.handler.exception.BadRequestException;
import ru.ns.t_jobs.tg.BotNotifier;
import ru.ns.t_jobs.tg.entity.NewCandidate;
import ru.ns.t_jobs.tg.entity.NewCandidateRepository;

@RestController
@RequiredArgsConstructor
@RequestMapping("/forms/api")
public class FormsController {

    private final NewCandidateRepository newCandidateRepository;
    private final CandidateRepository candidateRepository;
    private final TagRepository tagRepository;
    private final ResumeRepository resumeRepository;

    @PostMapping("/about-me")
    void processAboutMeForm(@RequestBody AboutMeForm form) {
        NewCandidate newCandidate = newCandidateRepository.findByChatId(form.getChatId())
                .orElseThrow(() -> new BadRequestException("No users with %d chat id.".formatted(form.getChatId())));

        newCandidateRepository.delete(newCandidate);
        candidateRepository.save(Candidate.builder()
                .chatId(newCandidate.getChatId())
                .town(form.getTown())
                .name(form.getName())
                .surname(form.getSurname())
                .build()
        );

        BotNotifier.notifyAboutMeFormAccepted(form.getChatId());
    }

    @PostMapping("/resume")
    void processResumeForm(@RequestBody ResumeForm form) {
        Candidate candidate = candidateRepository.findByChatId(form.getChatId())
                .orElseThrow(() -> new BadRequestException("No users with %d chat id.".formatted(form.getChatId())));

        Resume resume = Resume.builder()
                .date(form.getDate().toLocalDate())
                .candidate(candidate)
                .name(form.getName())
                .description(form.getDescription())
                .salaryMin(form.getSalaryMin())
                .tags(tagRepository.findByNameIn(form.getTagNames()))
                .build();

        resumeRepository.save(resume);
        BotNotifier.notifyResumeAccepted(resume);
    }

}
