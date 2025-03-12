package ru.ns.t_jobs.handler;

import jakarta.servlet.http.HttpServletRequest;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import ru.ns.t_jobs.auth.credentials.Credentials;
import ru.ns.t_jobs.handler.dto.ExceptionResponse;

import java.util.NoSuchElementException;
import java.util.concurrent.atomic.AtomicLong;

@RestControllerAdvice
public class ExceptionsHandler extends ResponseEntityExceptionHandler {
    private final AtomicLong atomicLong = new AtomicLong(0);
    private final Logger logger = LogManager.getLogger(ExceptionsHandler.class);
    private final Level REQUEST_LEVEL = Level.forName("request", 1);

    @ExceptionHandler({AuthenticationException.class})
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ExceptionResponse authenticationException(AuthenticationException e) {
        long id = atomicLong.getAndIncrement();
        log(e.getMessage(), id, HttpStatus.UNAUTHORIZED);
        return new ExceptionResponse("Wrong login or password", id);
    }

    @ExceptionHandler({NoSuchElementException.class})
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ExceptionResponse notFoundException(NoSuchElementException e) {
        long id = atomicLong.getAndIncrement();
        log(e.getMessage(), id, HttpStatus.NOT_FOUND);
        return new ExceptionResponse(e.getMessage(), id);
    }

    private void log(String msg, long id, HttpStatus status) {
        HttpServletRequest request =
                ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        logger.log(
                REQUEST_LEVEL, String.format("%30s %8s %20s %20s %d %d %s FAIL",
                principal instanceof Credentials ? ((Credentials) principal).getLogin() : principal,
                request.getMethod(),
                request.getServletPath(),
                status.name(),
                status.value(),
                id,
                msg)
        );
    }
}
