package ru.ns.t_jobs.app.track.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.ns.t_jobs.app.track.dto.TrackConvertor;
import ru.ns.t_jobs.app.track.dto.TrackInfoDto;
import ru.ns.t_jobs.app.track.entity.Track;
import ru.ns.t_jobs.app.track.entity.TrackRepository;

import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TrackServiceImpl implements TrackService {

    private final TrackRepository trackRepository;

    @Override
    public TrackInfoDto getTrackById(long id) {
        Optional<Track> trackOp = trackRepository.findById(id);

        if (trackOp.isEmpty())
            throw new NoSuchElementException("No tracks with %d id".formatted(id));

        Track track = trackOp.orElseThrow();
        return TrackConvertor.from(track);
    }
}
