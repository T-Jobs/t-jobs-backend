package ru.ns.t_jobs.app.track.service;

import org.springframework.stereotype.Repository;
import ru.ns.t_jobs.app.track.dto.TrackInfoDto;

import java.util.List;

@Repository
public interface TrackService {
    TrackInfoDto getTrack(long id);
    List<TrackInfoDto> getTracks(List<Long> ids);
    TrackInfoDto approveApplication(long candidateId, long vacancyId);
}
