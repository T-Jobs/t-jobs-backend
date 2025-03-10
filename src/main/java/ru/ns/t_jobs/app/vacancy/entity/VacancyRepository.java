package ru.ns.t_jobs.app.vacancy.entity;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VacancyRepository extends PagingAndSortingRepository<Vacancy, Long> {
    @Query("SELECT distinct v FROM Vacancy v JOIN v.tags t WHERE t.id IN :tagIds AND v.salaryMax >= :salary GROUP BY v.id HAVING COUNT(t.id) >= :tagCount")
    List<Vacancy> findAllByTags(@Param("tagIds") List<Long> tagIds, @Param("tagCount") long tagCount, @Param("salary") int lowerBound, Pageable pageable);

    @Query("SELECT v FROM Vacancy v WHERE v.salaryMax >= :salary")
    List<Vacancy> findAllByTags(@Param("salary") int lowerBound, Pageable pageable);
}