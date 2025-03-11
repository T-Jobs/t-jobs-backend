package ru.ns.t_jobs.auth.user;

import jakarta.persistence.*;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;

@Entity
@Table(name = "role")
@Getter
public class Role implements GrantedAuthority {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", length = 50)
    private String name;

    @Override
    public String getAuthority() {
        return "ROLE_" + getName();
    }
}
