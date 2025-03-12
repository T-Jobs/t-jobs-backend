package ru.ns.t_jobs.auth.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;
import ru.ns.t_jobs.auth.credentials.Credentials;
import ru.ns.t_jobs.auth.credentials.CredentialsRepository;
import ru.ns.t_jobs.auth.dto.AuthenticationRequest;
import ru.ns.t_jobs.auth.token.JwtTokenUtils;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LoginServiceImpl implements LoginService {
    private final CredentialsRepository credentialsRepository;
    private final AuthenticationManager authenticationManager;

    @Override
    public String login(AuthenticationRequest request) {
        Optional<Credentials> userOp = credentialsRepository.findByLogin(request.login());
        if (userOp.isEmpty()) throw new BadCredentialsException("");

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.login(),
                        request.password()
                )
        );

        Credentials credentials = userOp.orElseThrow();
        return JwtTokenUtils.generateToken(credentials);
    }
}
