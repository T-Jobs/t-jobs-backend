package ru.ns.t_jobs.app.staff.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.io.Serializable;

@Embeddable
public class StaffVacancyKey implements Serializable {

    @Column(name = "staff_id")
    Long staffId;

    @Column(name = "vacancy_id")
    Long vacancyId;

}
