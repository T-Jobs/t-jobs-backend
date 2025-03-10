package ru.ns.t_jobs.app.staff.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import ru.ns.t_jobs.app.staff.dto.StaffConvertor;
import ru.ns.t_jobs.app.staff.dto.StaffInfoDto;
import ru.ns.t_jobs.app.staff.entity.Staff;
import ru.ns.t_jobs.app.staff.entity.StaffRepository;
import ru.ns.t_jobs.auth.user.Credentials;
import ru.ns.t_jobs.auth.user.Role;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StaffServiceImpl implements StaffService {

    private final StaffRepository staffRepository;

    @Override
    public StaffInfoDto getUserInfo() {
        Credentials principal = (Credentials) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Staff s = staffRepository.getReferenceById(principal.getStaffId());
        return StaffConvertor.from(s);
    }

    @Override
    public List<Role> getUserRoles() {
        Credentials principal = (Credentials) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return (List<Role>) principal.getAuthorities();
    }
}
