package ru.ns.t_jobs.auth.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import ru.ns.t_jobs.auth.dto.AuthenticationRequest;

public interface AuthController {
    @GetMapping("/login")
    ResponseEntity<?> authenticate(@RequestBody AuthenticationRequest request);
}
