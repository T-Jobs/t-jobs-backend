package ru.ns.t_jobs.app.interview.entity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface InterviewRepository extends JpaRepository<Interview, Long> {
    @Query("SELECT i FROM Interview i WHERE i.interviewer.id = :id")
    List<Interview> findByStaffId(@Param("id") long id);
}
