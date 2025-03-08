package ru.ns.t_jobs.auth.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;
import ru.ns.t_jobs.auth.dto.AuthenticationRequest;
import ru.ns.t_jobs.auth.token.JwtTokenUtils;
import ru.ns.t_jobs.auth.user.User;
import ru.ns.t_jobs.auth.user.UserRepository;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LoginServiceImpl implements LoginService {
    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;

    @Override
    public String login(AuthenticationRequest request) {
        Optional<User> userOp = userRepository.findByLogin(request.login());
        if (userOp.isEmpty()) throw new BadCredentialsException("");

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.login(),
                        request.password()
                )
        );

        User user = userOp.orElseThrow();
        return JwtTokenUtils.generateToken(user);
    }
}
