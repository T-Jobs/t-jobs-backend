package ru.ns.t_jobs.app.track.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.ns.t_jobs.app.track.dto.TrackInfoDto;

import java.util.List;

@RequestMapping("/track")
public interface TrackController {
    @GetMapping("/{id}")
    TrackInfoDto getTrack(@PathVariable("id") long id);

    @GetMapping
    List<TrackInfoDto> getTracks(@RequestParam("ids") List<Long> ids);

    @PostMapping("/approve-application")
    TrackInfoDto approveApplication(@RequestParam("candidate_id") long candidateId, @RequestParam("vacancy_id") long vacancyId);

    @PostMapping("/set-hr")
    void setHr(@RequestParam("track_id") long trackId, @RequestParam("hr_id") long hrId);

    @PostMapping("/finish")
    void finishTrack(@RequestParam("id") long trackId);

    @PostMapping("/create")
    TrackInfoDto createTrack(@RequestParam("candidate_id") long candidateId, @RequestParam("vacancy_id") long vacancyId);
}
