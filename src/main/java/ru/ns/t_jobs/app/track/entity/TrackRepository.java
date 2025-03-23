package ru.ns.t_jobs.app.track.entity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TrackRepository extends JpaRepository<Track, Long> {
    @Query("SELECT t FROM Track t JOIN t.candidate c WHERE t.finished = FALSE AND c.id = :id")
    List<Track> findActiveTracksByCandidateId(@Param("id") long candidateId);
}
