package ru.ns.t_jobs.app.staff.entity;

import jakarta.persistence.*;
import lombok.Getter;
import ru.ns.t_jobs.app.interview.entity.InterviewType;
import ru.ns.t_jobs.app.track.Track;

import java.util.List;

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
}