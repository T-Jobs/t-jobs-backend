package ru.ns.t_jobs.app.track.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;
import ru.ns.t_jobs.app.track.dto.TrackInfoDto;
import ru.ns.t_jobs.app.track.service.TrackService;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class TrackControllerImpl implements TrackController {

    private final TrackService trackService;

    @Override
    public TrackInfoDto getTrack(long id) {
        return trackService.getTrack(id);
    }

    @Override
    public List<TrackInfoDto> getTracks(List<Long> ids) {
        return trackService.getTracks(ids);
    }

    @Override
    public TrackInfoDto approveApplication(long candidateId, long vacancyId) {
        return trackService.approveApplication(candidateId, vacancyId);
    }

    @Override
    public void setHr(long trackId, long hrId) {
        trackService.setHr(trackId, hrId);
    }

    @Override
    public void finishTrack(long trackId) {
        trackService.finishTrack(trackId);
    }
}
