package ru.ns.t_jobs.app.interview.entity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InterviewTypeRepository extends JpaRepository<InterviewType, Long> {
    List<InterviewType> findByNameIgnoreCaseContains(String name);
}
