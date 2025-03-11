package ru.ns.t_jobs.auth.user;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import ru.ns.t_jobs.app.staff.entity.Staff;

import java.util.Collection;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "Credentials")
public class Credentials implements UserDetails {
    @Id
    @Column(name = "staff_id")
    private Long staffId;

    @Column(name = "login", nullable = false, unique = true, length = 50)
    private String login;

    @Column(name = "password", nullable = false)
    private String password;

    @OneToOne
    @JoinColumn(name = "staff_id", referencedColumnName = "id", nullable = false)
    private Staff staff;

    @Override
    public Collection<Role> getAuthorities() {
        return staff.getRoles();
    }

    @Override
    public String getUsername() {
        return login;
    }
}

