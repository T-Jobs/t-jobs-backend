package ru.ns.t_jobs.app.track.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.ns.t_jobs.app.track.dto.TrackInfoDto;

@RequestMapping("/track")
public interface TrackController {
    @GetMapping("/{id}")
    TrackInfoDto getTrack(@PathVariable("id") long id);

    @PostMapping("/approve-application")
    TrackInfoDto approveApplication(@RequestParam("candidate_id") long candidateId, @RequestParam("vacancy_id") long vacancyId);
}
