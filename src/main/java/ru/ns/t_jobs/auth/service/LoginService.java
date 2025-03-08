package ru.ns.t_jobs.auth.service;

import ru.ns.t_jobs.auth.dto.AuthenticationRequest;

public interface LoginService {
    String login(AuthenticationRequest request);
}
