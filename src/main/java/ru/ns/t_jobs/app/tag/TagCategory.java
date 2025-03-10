package ru.ns.t_jobs.app.tag;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "tag_category")
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class TagCategory {

    @Id
    private long id;

    @Column(name = "name", length = 50)
    private String name;

}
