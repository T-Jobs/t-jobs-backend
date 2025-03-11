package ru.ns.t_jobs.app.candidate.entity;

import jakarta.persistence.*;
import lombok.Getter;
import ru.ns.t_jobs.app.track.entity.Track;
import ru.ns.t_jobs.app.vacancy.entity.Vacancy;

import java.util.List;

@Getter
@Entity
@Table(name = "candidate")
public class Candidate {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false, length = 50)
    private String name;

    @Column(name = "surname", length = 50)
    private String surname;

    @Column(name = "photo_url", columnDefinition = "TEXT")
    private String photoUrl;

    @Column(name = "tg_id", length = 50)
    private String tgId;

    @Column(name = "town", length = 50)
    private String town;

    @OneToMany(mappedBy = "candidate")
    private List<Resume> resumes;

    @OneToMany(mappedBy = "candidate")
    private List<Track> tracks;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "candidate_applications",
            joinColumns = @JoinColumn(name = "candidate_id"),
            inverseJoinColumns = @JoinColumn(name = "vacancy_id")
    )
    private List<Vacancy> appliedVacancies;
}
