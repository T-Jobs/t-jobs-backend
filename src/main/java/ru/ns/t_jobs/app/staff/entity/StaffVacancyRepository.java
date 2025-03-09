package ru.ns.t_jobs.app.staff.entity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface StaffVacancyRepository extends JpaRepository<StaffVacancy, StaffVacancyKey> {
    @Query(
            nativeQuery = true,
            value = "SELECT vacancy_id FROM staff_vacancy WHERE staff_id = :id"
    )
    List<Long> findVacancyIdsByStaffId(Long id);
}
