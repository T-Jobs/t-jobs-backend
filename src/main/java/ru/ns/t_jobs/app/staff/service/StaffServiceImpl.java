package ru.ns.t_jobs.app.staff.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.ns.t_jobs.app.interview.dto.InterviewConvertor;
import ru.ns.t_jobs.app.interview.dto.InterviewDto;
import ru.ns.t_jobs.app.interview.entity.Interview;
import ru.ns.t_jobs.app.interview.entity.InterviewTypeRepository;
import ru.ns.t_jobs.app.staff.dto.StaffConvertor;
import ru.ns.t_jobs.app.staff.dto.StaffInfoDto;
import ru.ns.t_jobs.app.staff.entity.Staff;
import ru.ns.t_jobs.app.staff.entity.StaffRepository;
import ru.ns.t_jobs.app.track.dto.TrackConvertor;
import ru.ns.t_jobs.app.track.dto.TrackInfoDto;
import ru.ns.t_jobs.app.vacancy.dto.VacancyConvertor;
import ru.ns.t_jobs.app.vacancy.dto.VacancyDto;
import ru.ns.t_jobs.app.vacancy.entity.VacancyRepository;
import ru.ns.t_jobs.auth.user.Credentials;
import ru.ns.t_jobs.auth.user.Role;
import ru.ns.t_jobs.auth.util.AuthUtils;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Stream;

import static ru.ns.t_jobs.app.interview.entity.InterviewStatus.FAILED;
import static ru.ns.t_jobs.app.interview.entity.InterviewStatus.SUCCESS;

@Service
@RequiredArgsConstructor
public class StaffServiceImpl implements StaffService {

    private final StaffRepository staffRepository;
    private final InterviewTypeRepository interviewTypeRepository;
    private final VacancyRepository vacancyRepository;

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

    @Override
    public List<StaffInfoDto> searchStaffByText(String text) {
        return StaffConvertor.from(staffRepository.findByText(text));
    }

    @Override
    public List<StaffInfoDto> getStaffByIds(List<Long> ids) {
        return StaffConvertor.from(staffRepository.findAllById(ids));
    }

    @Override
    public StaffInfoDto getStaffById(Long id) {
        return StaffConvertor.from(
                staffRepository.findById(id)
                        .orElseThrow(() -> new NoSuchElementException("No staff with %d id".formatted(id)))
        );
    }

    @Override
    public void setInterviewerMode(boolean interviewerMode) {
        Credentials c = (Credentials) AuthUtils.getCurrentUserDetails();
        c.getStaff().setInterviewerMode(interviewerMode);
        staffRepository.save(c.getStaff());
    }

    @Override
    public void addInterviewTypeToInterviewer(long interviewTypeId) {
        var interviewType = interviewTypeRepository.findById(interviewTypeId);

        Credentials c = (Credentials) AuthUtils.getCurrentUserDetails();
        Staff s = staffRepository.getReferenceById(c.getStaffId());
        s.getInterviewTypes().add(
                interviewType.orElseThrow(
                        () -> new NoSuchElementException("No interview type with %d id.".formatted(interviewTypeId))
                )
        );
        staffRepository.save(s);
    }

    @Override
    public void removeInterviewTypeFromInterviewer(long interviewTypeId) {
        var interviewType = interviewTypeRepository.findById(interviewTypeId);

        Credentials c = (Credentials) AuthUtils.getCurrentUserDetails();
        Staff s = staffRepository.getReferenceById(c.getStaffId());
        s.getInterviewTypes().remove(
                interviewType.orElseThrow(
                        () -> new NoSuchElementException("No interview type with %d id.".formatted(interviewTypeId))
                )
        );
        staffRepository.save(s);
    }

    @Override
    public void followVacancy(long id) {
        Credentials c = (Credentials) AuthUtils.getCurrentUserDetails();
        c.getStaff().getVacancies().add(
                vacancyRepository.findById(id)
                        .orElseThrow(() -> new NoSuchElementException("No vacancy type with %d id.".formatted(id)))
        );
        staffRepository.save(c.getStaff());
    }
}
