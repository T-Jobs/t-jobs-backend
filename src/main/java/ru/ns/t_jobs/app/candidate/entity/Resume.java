package ru.ns.t_jobs.app.candidate.entity;

import jakarta.persistence.*;
import lombok.Getter;
import ru.ns.t_jobs.app.tag.Tag;

import java.util.List;

@Entity
@Table(name = "Resume")
@Getter
public class Resume {
    @Id
    private long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "candidate_id", referencedColumnName = "id",
            nullable = false, updatable = false, insertable = false)
    private Candidate candidate;

    @Column(name = "salary_min")
    private int salaryMin;

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    @ManyToMany
    @JoinTable(
            name = "resume_tag",
            joinColumns = @JoinColumn(name = "resume_id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id")
    )
    private List<Tag> tags;
}
