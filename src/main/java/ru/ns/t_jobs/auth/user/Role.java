package ru.ns.t_jobs.auth.user;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;

@Entity
@Table(name = "role")
@Getter
public class Role implements GrantedAuthority {

    @Id
    private long id;

    @Column(name = "name", length = 50)
    private String name;

    @Override
    public String getAuthority() {
        return getName();
    }
}
