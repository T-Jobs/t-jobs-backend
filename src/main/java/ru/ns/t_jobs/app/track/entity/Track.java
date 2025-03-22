package ru.ns.t_jobs.app.track.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
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

    public Interview getCurrentInterview() {
        Interview res = null;

        for (Interview i : interviews) {
            if (i.getStatus() != InterviewStatus.FAILED && i.getStatus() != InterviewStatus.SUCCESS) {
                res = i;
                break;
            }
        }

        return res;
    }
}
