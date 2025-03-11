package ru.ns.t_jobs.app.staff.entity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StaffRepository extends JpaRepository<Staff, Long> {
    @Query("SELECT s FROM Staff s WHERE CONCAT(lower(s.name), lower(s.surname)) LIKE %:text%")
    List<Staff> findByText(@Param("text") String text);
}
