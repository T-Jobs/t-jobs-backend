package ru.ns.t_jobs.app.candidate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;

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

    @Column(name = "tg_id", length = 50)
    private String tgId;

    @Column(name = "town", length = 50)
    private String town;
}
