package ru.ns.t_jobs.app.staff.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.ns.t_jobs.app.interview.dto.InterviewConvertor;
import ru.ns.t_jobs.app.interview.dto.InterviewDto;
import ru.ns.t_jobs.app.interview.entity.Interview;
import ru.ns.t_jobs.app.staff.dto.StaffConvertor;
import ru.ns.t_jobs.app.staff.dto.StaffInfoDto;
import ru.ns.t_jobs.app.staff.entity.Staff;
import ru.ns.t_jobs.app.staff.entity.StaffRepository;
import ru.ns.t_jobs.app.track.dto.TrackConvertor;
import ru.ns.t_jobs.app.track.dto.TrackInfoDto;
import ru.ns.t_jobs.app.vacancy.dto.VacancyConvertor;
import ru.ns.t_jobs.app.vacancy.dto.VacancyDto;
import ru.ns.t_jobs.auth.user.Credentials;
import ru.ns.t_jobs.auth.user.Role;
import ru.ns.t_jobs.auth.util.AuthUtils;

import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Stream;

import static ru.ns.t_jobs.app.interview.entity.InterviewStatus.FAILED;
import static ru.ns.t_jobs.app.interview.entity.InterviewStatus.SUCCESS;

@Service
@RequiredArgsConstructor
public class StaffServiceImpl implements StaffService {

    private final StaffRepository staffRepository;

    @Override
    public StaffInfoDto getUserInfo() {
        Credentials principal = (Credentials) AuthUtils.getCurrentUserDetails();
        Staff s = staffRepository.getReferenceById(principal.getStaffId());
        return StaffConvertor.from(s);
    }

    @Override
    public List<Role> getUserRoles() {
        Credentials principal = (Credentials) AuthUtils.getCurrentUserDetails();
        return (List<Role>) principal.getAuthorities();
    }

    @Override
    public List<VacancyDto> getUserVacancies() {
        Credentials principal = (Credentials) AuthUtils.getCurrentUserDetails();
        return VacancyConvertor.from(
                staffRepository.getReferenceById(principal.getStaffId()).getVacancies()
        );
    }

    @Override
    public List<InterviewDto> getUserInterviews(boolean onlyActual) {
        Credentials principal = (Credentials) AuthUtils.getCurrentUserDetails();
        long staffId = principal.getStaffId();
        var filterInterviewTypes = Set.of(SUCCESS, FAILED);

        Stream<Interview> result = staffRepository.getReferenceById(staffId).getInterviews().stream();
        if (onlyActual) {
            result = result.filter(i -> !filterInterviewTypes.contains(i.getStatus()));
        }

        result = result.sorted((o1, o2) -> {
            if (Objects.equals(o1.getDatePicked(), o2.getDatePicked())) return 0;
            if (o1.getDatePicked() == null) return 1;
            if (o2.getDatePicked() == null) return -1;
            return o1.getDatePicked().isBefore(o2.getDatePicked()) ? -1 : 1;
        });
        return result.map(InterviewConvertor::from).toList();
    }

    @Override
    public List<TrackInfoDto> getHrTracks(boolean onlyActual) {
        Credentials principal = (Credentials) AuthUtils.getCurrentUserDetails();
        return staffRepository.getReferenceById(principal.getStaffId()).getTracks()
                .stream().filter(t -> !onlyActual || t.isFinished()).map(TrackConvertor::from).toList();
    }
}
