package ru.ns.t_jobs.auth.user;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "Credentials")
public class User implements UserDetails {
    @Id
    @Column(name = "staff_id")
    private long staffId;

    @Column(name = "login", nullable = false, unique = true, length = 50)
    private String login;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "is_hr")
    private Boolean isHr;

    @Column(name = "is_tl")
    private Boolean isTl;

    @Column(name = "is_interv")
    private Boolean isInterv;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<Roles> roles = new ArrayList<>();
        if (isHr) roles.add(Roles.HR);
        if (isTl) roles.add(Roles.TL);
        if (isInterv) roles.add(Roles.INTERVIEWER);

        return roles;
    }

    @Override
    public String getUsername() {
        return login;
    }
}

