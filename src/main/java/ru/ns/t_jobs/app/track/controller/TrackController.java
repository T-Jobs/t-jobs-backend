package ru.ns.t_jobs.app.track.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.ns.t_jobs.app.track.dto.TrackInfoDto;

@RequestMapping("/track")
public interface TrackController {
    @GetMapping("/{id}")
    TrackInfoDto getTrack(@PathVariable("id") long id);
}
