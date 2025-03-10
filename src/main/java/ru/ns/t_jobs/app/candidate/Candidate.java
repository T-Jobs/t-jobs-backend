package ru.ns.t_jobs.app.candidate;

import jakarta.persistence.*;
import lombok.Getter;
import ru.ns.t_jobs.app.track.entity.Track;

import java.util.List;

@Getter
@Entity
@Table(name = "candidate")
public class Candidate {
    @Id
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
}
