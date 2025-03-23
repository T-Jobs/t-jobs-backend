package ru.ns.t_jobs.auth.util;

import org.springframework.security.core.context.SecurityContextHolder;
import ru.ns.t_jobs.auth.credentials.Credentials;

public class ContextUtils {
    public static final ThreadLocal<Boolean> authenticated = ThreadLocal.withInitial(() -> false);


    public static Long getCurrentUserStaffId() {
        return ((Credentials) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getStaffId();
    }

    public static boolean isAuthenticated() {
        return authenticated.get();
    }
}
