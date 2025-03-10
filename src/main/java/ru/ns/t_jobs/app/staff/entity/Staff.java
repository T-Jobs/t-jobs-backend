package ru.ns.t_jobs.app.staff.entity;

import jakarta.persistence.*;
import lombok.Getter;
import ru.ns.t_jobs.app.interview.entity.Interview;
import ru.ns.t_jobs.app.interview.entity.InterviewType;
import ru.ns.t_jobs.app.track.entity.Track;
import ru.ns.t_jobs.app.vacancy.entity.Vacancy;
import ru.ns.t_jobs.auth.user.Role;

import java.util.List;
import java.util.Set;

@Getter
@Entity
@Table(name = "staff")
public class Staff {

    @Id
    @Column(name = "id", nullable = false, unique = true)
    private Long id;

    @Column(name = "name", nullable = false, length = 100)
    private String name;

    @Column(name = "surname", length = 100)
    private String surname;

    @Column(name = "photo_url", columnDefinition = "TEXT")
    private String photoUrl;

    @OneToMany(mappedBy = "hrId", fetch = FetchType.LAZY)
    private List<Track> tracks;

    @ManyToMany
    @JoinTable(
            name = "interview_type_staff",
            joinColumns = @JoinColumn(name = "staff_id"),
            inverseJoinColumns = @JoinColumn(name = "interview_type_id")
    )
    private List<InterviewType> interviewTypes;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "interviewer")
    private List<Interview> interviews;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "staff_vacancy",
            joinColumns = @JoinColumn(name = "staff_id"),
            inverseJoinColumns = @JoinColumn(name = "vacancy_id")
    )
    private List<Vacancy> vacancies;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "staff_role",
            joinColumns = @JoinColumn(name = "staff_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private Set<Role> roles;
}