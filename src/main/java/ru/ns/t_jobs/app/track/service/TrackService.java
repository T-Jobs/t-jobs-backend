package ru.ns.t_jobs.app.track.service;

import org.springframework.stereotype.Repository;
import ru.ns.t_jobs.app.track.dto.TrackInfoDto;

@Repository
public interface TrackService {
    TrackInfoDto getTrackById(long id);
    TrackInfoDto approveApplication(long candidateId, long vacancyId);
}
