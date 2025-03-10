package ru.ns.t_jobs.app;

import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Table(name = "tag")
@Getter
public class Tag {
    @Id
    private long id;

    @ManyToOne
    @JoinColumn(name = "category_id", referencedColumnName = "id", nullable = false)
    private TagCategory category;

    @Column(name = "name", length = 50)
    private String name;
}
