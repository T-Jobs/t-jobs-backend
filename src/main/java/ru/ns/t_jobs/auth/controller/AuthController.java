package ru.ns.t_jobs.auth.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import ru.ns.t_jobs.auth.dto.AuthenticationRequest;

public interface AuthController {
    @PostMapping("/login")
    ResponseEntity<?> authenticate(@RequestBody AuthenticationRequest request);
}
