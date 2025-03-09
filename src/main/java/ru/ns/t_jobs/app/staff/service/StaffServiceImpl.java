package ru.ns.t_jobs.app.staff.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import ru.ns.t_jobs.app.interview.entity.InterviewRepository;
import ru.ns.t_jobs.app.track.Track;
import ru.ns.t_jobs.app.staff.dto.StaffInfoDto;
import ru.ns.t_jobs.app.staff.entity.Staff;
import ru.ns.t_jobs.app.staff.entity.StaffRepository;
import ru.ns.t_jobs.app.staff.entity.StaffVacancyRepository;
import ru.ns.t_jobs.auth.user.Roles;
import ru.ns.t_jobs.auth.user.User;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StaffServiceImpl implements StaffService {

    private final StaffRepository staffRepository;
    private final StaffVacancyRepository staffVacancyRepository;
    private final InterviewRepository interviewRepository;

    @Override
    public StaffInfoDto getUserInfo() {
        User principal = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Staff user = staffRepository.getReferenceById(principal.getStaffId());

        return new StaffInfoDto(
                user.getId(),
                user.getName(),
                user.getSurname(),
                user.getPhotoUrl(),
                user.getTracks().stream().map(Track::getId).toList(),
                user.getInterviewTypes(),
                staffVacancyRepository.findVacancyIdsByStaffId(user.getId()),
                (List<Roles>) principal.getAuthorities(),
                interviewRepository.findInterviewIdsByStaffId(user.getId())
        );
    }

    @Override
    public List<Roles> getUserRoles() {
        User principal = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return (List<Roles>) principal.getAuthorities();
    }
}
