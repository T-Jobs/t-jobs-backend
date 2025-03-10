package ru.ns.t_jobs.app.candidate.entity;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ResumeRepository extends PagingAndSortingRepository<Resume, Long> {
    @Query("SELECT distinct r.candidate FROM Resume r JOIN r.tags t WHERE t.id IN :tagIds AND r.salaryMin <= :salary GROUP BY r.id HAVING COUNT(t.id) >= :tagCount")
    List<Candidate> findAllByTags(@Param("tagIds") List<Long> tagIds, @Param("tagCount") long tagCount, @Param("salary") int upperBound, Pageable pageable);
    @Query("SELECT distinct r.candidate FROM Resume r WHERE r.salaryMin <= :salary")
    List<Candidate> findAllByTags(@Param("salary") int upperBound, Pageable pageable);
}
