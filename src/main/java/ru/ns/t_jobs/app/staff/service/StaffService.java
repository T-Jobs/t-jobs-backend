package ru.ns.t_jobs.app.staff.service;

import org.springframework.stereotype.Repository;
import ru.ns.t_jobs.app.staff.dto.StaffInfoDto;
import ru.ns.t_jobs.auth.user.Roles;

import java.util.List;

@Repository
public interface StaffService {
    StaffInfoDto getUserInfo();
    List<Roles> getUserRoles();
}
