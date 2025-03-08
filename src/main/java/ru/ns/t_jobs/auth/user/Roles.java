package ru.ns.t_jobs.auth.user;

import org.springframework.security.core.GrantedAuthority;

enum Roles implements GrantedAuthority {
    HR, TL, INTERVIEWER;

    @Override
    public String getAuthority() {
        return name();
    }
}
