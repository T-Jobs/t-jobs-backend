package ru.ns.t_jobs.auth.user;

import org.springframework.security.core.GrantedAuthority;

public enum Roles implements GrantedAuthority {
    HR, TL, INTERVIEWER;

    @Override
    public String getAuthority() {
        return name();
    }
}
