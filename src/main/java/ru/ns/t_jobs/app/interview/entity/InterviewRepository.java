package ru.ns.t_jobs.app.interview.entity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface InterviewRepository extends JpaRepository<Interview, Long> {
    @Query(
            nativeQuery = true,
            value = "SELECT id FROM interview WHERE interview_type_id = :id"
    )
    List<Long> findInterviewIdsByStaffId(Long id);
}
