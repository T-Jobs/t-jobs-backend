package ru.ns.t_jobs.auth.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.ns.t_jobs.auth.dto.AuthenticationRequest;
import ru.ns.t_jobs.auth.dto.AuthenticationResponse;
import ru.ns.t_jobs.auth.service.LoginService;
import ru.ns.t_jobs.auth.user.UserRepository;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthControllerImpl implements AuthController {

    private final UserRepository userRepository;
    private final LoginService loginService;

    @Override
    public ResponseEntity<?> authenticate(@RequestBody AuthenticationRequest request) {
        return ResponseEntity.ok(new AuthenticationResponse(loginService.login(request)));
    }
}
