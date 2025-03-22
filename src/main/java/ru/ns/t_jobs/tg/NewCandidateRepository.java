package ru.ns.t_jobs.tg;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface NewCandidateRepository extends JpaRepository<NewCandidate, Long> {
    Optional<NewCandidate> findByChatId(long chatId);
}
