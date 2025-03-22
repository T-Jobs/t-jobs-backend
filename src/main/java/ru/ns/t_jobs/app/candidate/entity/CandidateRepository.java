package ru.ns.t_jobs.app.candidate.entity;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CandidateRepository extends JpaRepository<Candidate, Long> {
    Optional<Candidate> findByChatId(long id);
}
