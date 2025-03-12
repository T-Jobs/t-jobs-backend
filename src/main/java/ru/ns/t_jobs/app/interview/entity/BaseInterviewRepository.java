package ru.ns.t_jobs.app.interview.entity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BaseInterviewRepository extends JpaRepository<BaseInterview, Long> {
}
