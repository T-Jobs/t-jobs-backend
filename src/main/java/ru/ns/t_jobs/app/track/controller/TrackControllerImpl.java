package ru.ns.t_jobs.app.track.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;
import ru.ns.t_jobs.app.track.dto.TrackInfoDto;
import ru.ns.t_jobs.app.track.entity.Track;
import ru.ns.t_jobs.app.track.service.TrackService;

@RestController
@RequiredArgsConstructor
public class TrackControllerImpl implements TrackController {

    private final TrackService trackService;

    @Override
    public TrackInfoDto getTrack(long id) {
        return trackService.getTrackById(id);
    }
}
