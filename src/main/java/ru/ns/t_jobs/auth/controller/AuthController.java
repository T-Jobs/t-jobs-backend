package ru.ns.t_jobs.auth.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import ru.ns.t_jobs.auth.dto.AuthenticationRequest;
import ru.ns.t_jobs.auth.dto.AuthenticationResponse;

public interface AuthController {
    @PostMapping("/login")
    AuthenticationResponse authenticate(@RequestBody AuthenticationRequest request);
}
