package ru.ns.t_jobs.app.track.entity;

import jakarta.persistence.*;
import lombok.Getter;
import ru.ns.t_jobs.app.candidate.Candidate;
import ru.ns.t_jobs.app.interview.entity.Interview;
import ru.ns.t_jobs.app.interview.entity.InterviewStatus;
import ru.ns.t_jobs.app.staff.entity.Staff;
import ru.ns.t_jobs.app.vacancy.Vacancy;

import java.util.List;

@Getter
@Entity
@Table(name = "track")
public class Track {
    @Id
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "last_status")
    private InterviewStatus lastStatus;

    @Column(name = "hr_id")
    private Long hrId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "hr_id", referencedColumnName = "id", insertable = false, updatable = false)
    private Staff hr;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "candidate_id", referencedColumnName = "id", insertable = false, updatable = false)
    private Candidate candidate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "vacancy_id", referencedColumnName = "id", insertable = false, updatable = false)
    private Vacancy vacancy;

    @OneToMany(mappedBy = "track")
    private List<Interview> interviews;
}
