package ru.ns.t_jobs.auth.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.ns.t_jobs.auth.dto.AuthenticationRequest;
import ru.ns.t_jobs.auth.dto.AuthenticationResponse;
import ru.ns.t_jobs.auth.service.LoginService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthControllerImpl implements AuthController {

    private final LoginService loginService;

    @Override
    public AuthenticationResponse authenticate(@RequestBody AuthenticationRequest request) {
        return new AuthenticationResponse(loginService.login(request));
    }
}
