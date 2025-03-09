package ru.ns.t_jobs.app.staff.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;
import ru.ns.t_jobs.app.staff.dto.StaffInfoDto;
import ru.ns.t_jobs.app.staff.service.StaffService;
import ru.ns.t_jobs.auth.user.Roles;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class StaffControllerImpl implements StaffController {

    private final StaffService staffService;

    @Override
    public StaffInfoDto getUserInfo() {
        return staffService.getUserInfo();
    }

    @Override
    public List<Roles> getUserRoles() {
        return staffService.getUserRoles();
    }
}
