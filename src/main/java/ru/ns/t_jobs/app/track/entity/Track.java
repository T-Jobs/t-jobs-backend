package ru.ns.t_jobs.app.track.entity;

import jakarta.persistence.*;
import lombok.*;
import ru.ns.t_jobs.app.candidate.entity.Candidate;
import ru.ns.t_jobs.app.interview.entity.Interview;
import ru.ns.t_jobs.app.interview.entity.InterviewStatus;
import ru.ns.t_jobs.app.staff.entity.Staff;
import ru.ns.t_jobs.app.vacancy.entity.Vacancy;

import java.util.List;

@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "track")
public class Track {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "finished")
    private boolean finished;

    @Enumerated(EnumType.STRING)
    @Column(name = "last_status")
    private InterviewStatus lastStatus;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "hr_id", referencedColumnName = "id")
    private Staff hr;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "candidate_id", referencedColumnName = "id")
    private Candidate candidate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "vacancy_id", referencedColumnName = "id")
    private Vacancy vacancy;

    @OneToMany(mappedBy = "track")
    private List<Interview> interviews;
}
