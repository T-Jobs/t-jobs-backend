package ru.ns.t_jobs.app;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;

@Entity
@Table(name = "tag_category")
@Getter
public class TagCategory {

    @Id
    private long id;

    @Column(name = "name", length = 50)
    private String name;

}
