package ru.ns.t_jobs.auth.config;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import ru.ns.t_jobs.auth.util.ContextUtils;

import java.io.IOException;

@Component
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request,
                         HttpServletResponse response,
                         AuthenticationException authException) throws IOException {
        if (!ContextUtils.isAuthenticated()) response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized");
        else response.sendError(HttpServletResponse.SC_FORBIDDEN, "Forbidden");
    }
}
