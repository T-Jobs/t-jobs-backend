package ru.ns.t_jobs.app.tag;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "tag")
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Tag {
    @Id
    private long id;

    @ManyToOne
    @JoinColumn(name = "category_id", referencedColumnName = "id", nullable = false)
    private TagCategory category;

    @Column(name = "name", length = 50)
    private String name;
}
