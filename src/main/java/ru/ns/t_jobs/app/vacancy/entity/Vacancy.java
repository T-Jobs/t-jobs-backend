package ru.ns.t_jobs.app.vacancy.entity;

import jakarta.persistence.*;
import lombok.Getter;
import ru.ns.t_jobs.app.candidate.entity.Candidate;
import ru.ns.t_jobs.app.tag.Tag;
import ru.ns.t_jobs.app.interview.entity.InterviewBase;
import ru.ns.t_jobs.app.staff.entity.Staff;
import ru.ns.t_jobs.app.track.entity.Track;

import java.util.List;

@Getter
@Entity
@Table(name = "vacancy")
public class Vacancy {
    @Id
    private Long id;

    @Column(name = "name", nullable = false, length = 100)
    private String name;

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    @Column(name = "salary_min")
    private int salaryMin;

    @Column(name = "salary_max")
    private int salaryMax;

    @Column(name = "town", length = 50)
    private String town;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "vacancy")
    private List<InterviewBase> interviewBases;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "vacancy_tag",
            joinColumns = @JoinColumn(name = "vacancy_id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id")
    )
    private List<Tag> tags;

    @ManyToMany(mappedBy = "vacancies")
    private List<Staff> staff;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "vacancy")
    private List<Track> tracks;

    @ManyToMany(mappedBy = "appliedVacancies")
    private List<Candidate> appliedCandidates;
}
