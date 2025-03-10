package ru.ns.t_jobs.app.interview.entity;

import jakarta.persistence.*;
import lombok.Getter;
import ru.ns.t_jobs.app.vacancy.entity.Vacancy;

@Entity
@Table(name = "interview_base")
@Getter
public class InterviewBase {

    @Id
    private Long id;

    @ManyToOne
    @JoinColumn(name = "interview_type_id", nullable = false)
    private InterviewType interviewType;

    @ManyToOne
    @JoinColumn(name = "vacancy_id", nullable = false)
    private Vacancy vacancy;

    @Column(name = "interview_order")
    private int interviewOrder;
}
