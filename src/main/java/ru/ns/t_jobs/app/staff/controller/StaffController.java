package ru.ns.t_jobs.app.staff.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.ns.t_jobs.app.staff.dto.StaffInfoDto;
import ru.ns.t_jobs.auth.user.Roles;

import java.util.List;

@RequestMapping("/user")
public interface StaffController {
    @GetMapping("/info")
    StaffInfoDto getUserInfo();

    @GetMapping("/roles")
    List<Roles> getUserRoles();
}
