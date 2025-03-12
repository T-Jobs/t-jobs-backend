package ru.ns.t_jobs.app.vacancy.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.ns.t_jobs.app.candidate.entity.Candidate;
import ru.ns.t_jobs.app.interview.entity.InterviewBase;
import ru.ns.t_jobs.app.staff.entity.Staff;
import ru.ns.t_jobs.app.tag.entity.Tag;
import ru.ns.t_jobs.app.track.entity.Track;

import java.util.List;

@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "vacancy")
public class Vacancy {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false, length = 100)
    private String name;

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    @Column(name = "salary_min")
    private Integer salaryMin;

    @Column(name = "salary_max")
    private Integer salaryMax;

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
