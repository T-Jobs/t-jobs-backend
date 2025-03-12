package ru.ns.t_jobs.app.candidate.entity;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ResumeRepository extends PagingAndSortingRepository<Resume, Long>, JpaRepository<Resume, Long> {
    @Query("SELECT c FROM Candidate c JOIN c.resumes r JOIN r.tags t " +
            "WHERE t.id IN :tagIds AND r.salaryMin <= :salary " +
            "AND CONCAT(lower(c.name), ' ', lower(c.surname)) LIKE %:text% " +
            "GROUP BY c.id HAVING COUNT(t.id) >= :tagCount")
    List<Candidate> findAllByTags(@Param("text") String text,
                                  @Param("tagIds") List<Long> tagIds,
                                  @Param("tagCount") long tagCount,
                                  @Param("salary") int salary,
                                  Pageable pageable);

    @Query("SELECT c FROM Candidate c JOIN c.resumes r " +
            "WHERE r.salaryMin <= :salary " +
            "AND CONCAT(lower(c.name), ' ', lower(c.surname)) LIKE %:text% ")
    List<Candidate> findAllByTags(@Param("text") String text,
                                  @Param("salary") int salary,
                                  Pageable pageable);
}
