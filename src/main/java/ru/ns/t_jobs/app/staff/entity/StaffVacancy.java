package ru.ns.t_jobs.app.staff.entity;

import jakarta.persistence.*;
import lombok.Getter;
import ru.ns.t_jobs.app.vacancy.Vacancy;

@Getter
@Entity
@Table(name = "staff_vacancy")
public class StaffVacancy {

    @EmbeddedId
    private StaffVacancyKey id;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "staff_id", referencedColumnName = "id", insertable = false, updatable = false)
    private Staff staff;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "vacancy_id", referencedColumnName = "id", insertable = false, updatable = false)
    private Vacancy vacancy;
}
